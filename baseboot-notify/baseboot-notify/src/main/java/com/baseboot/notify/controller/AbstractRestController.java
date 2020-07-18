/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.controller;


import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baseboot.notify.sdk.constants.BaseConstants;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.baseboot.notify.service.EmailService;
import com.baseboot.notify.service.SmsManagerService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractRestController extends GenericAbstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	protected SmsManagerService smsManagerService;

	@Autowired
	protected EmailService emailService;

	@Autowired
	protected Mapper dozerMapper;


	protected String getRealPath() {
		return this.getClass().getResource(BaseConstants.SLASH).getPath();
	}


	protected String getCurrUserId(HttpServletRequest request) {
		String currUser = BaseUtil.getStr(request.getAttribute("currUserId"));
		return !BaseUtil.isObjNull(currUser) ? currUser : "sysprtal";
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		return currentTimestamp;
	}

}