/**
 * Copyright 2019. 
 */
package com.notify.controller;


import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.notify.service.EmailService;
import com.notify.service.FcmService;
import com.notify.service.SmsManagerService;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;



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

	
	protected String getSystemHeader(HttpServletRequest request) {
		return request.getHeader(BaseConstants.HEADER_SYSTEM_TYPE);
	}
	
}