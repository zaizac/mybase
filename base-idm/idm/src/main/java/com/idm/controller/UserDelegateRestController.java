package com.idm.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmMailTemplateConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmProfile;
import com.idm.sdk.constants.IdmUrlConstants;
import com.notify.sdk.constants.NotConfigConstants;
import com.notify.sdk.model.Notification;
import com.notify.sdk.util.MailUtil;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.DELEGATE_USER)
public class UserDelegateRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDelegateRestController.class);

	String skipValues = "[-+.^:,()*@/]";


	public boolean sendMail(IdmProfile tp, String pword) {
		Map<String, Object> map = new HashMap<>();
		map.put("genDate", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		map.put("name", tp.getFirstName().concat(" ").concat(tp.getLastName()));
		map.put("loginName", tp.getUserId());
		map.put("password", pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get("PORTAL_URL_" + tp.getUserType().getUserTypeCode());

			LOGGER.debug("contactPhone: {} - contactEmail: {} - fwcmsUrl: {}", contactPhone, contactEmail, fwcmsUrl);
			map.put("contactPhone", contactPhone);
			map.put("contactEmail", contactEmail);
			map.put("fwcmsUrl", fwcmsUrl);
		}

		Notification notification = new Notification();
		notification.setNotifyTo(tp.getEmail());
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, IdmMailTemplateConstants.IDM_ACTIVATE_SUCCESS);
		return true;
	}

}
