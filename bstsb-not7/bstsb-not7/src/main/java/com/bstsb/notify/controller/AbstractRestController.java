/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.controller;


import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstsb.notify.service.EmailService;
import com.bstsb.notify.service.FcmService;
import com.bstsb.notify.service.SmsManagerService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConstants;



/**
 * The Class AbstractRestController.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractRestController extends GenericAbstract {

	/** The sms manager service. */
	@Autowired
	protected SmsManagerService smsManagerService;

	/** The email service. */
	@Autowired
	protected EmailService emailService;

	/** The fcm noti service. */
	@Autowired
	protected FcmService fcmNotiService;

	/** The dozer mapper. */
	@Autowired
	protected Mapper dozerMapper;


	/**
	 * Gets the real path.
	 *
	 * @return the real path
	 */
	protected String getRealPath() {
		return this.getClass().getResource(BaseConstants.SLASH).getPath();
	}


	/**
	 * Gets the curr user id.
	 *
	 * @param request the request
	 * @return the curr user id
	 */
	protected String getCurrUserId(HttpServletRequest request) {
		String currUser = BaseUtil.getStr(request.getAttribute("currUserId"));
		return !BaseUtil.isObjNull(currUser) ? currUser : "sysprtal";
	}


	/**
	 * Gets the SQL timestamp.
	 *
	 * @return the SQL timestamp
	 */
	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}

}