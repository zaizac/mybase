/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.IdmMailTemplateConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.notify.sdk.constants.NotConfigConstants;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.notify.sdk.util.MailUtil;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConstants;


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
		map.put("name", tp.getFullName());
		map.put("loginName", tp.getUserId());
		map.put("password", pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get("PORTAL_URL_" + tp.getUserType());

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
