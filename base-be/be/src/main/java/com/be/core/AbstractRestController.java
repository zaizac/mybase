/**
 * Copyright 2019
 */
package com.be.core;


import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.be.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public abstract class AbstractRestController extends GenericAbstract {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected CacheManager cacheManager;

	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	@Autowired
	protected MessageService messageService;


	@Override
	protected String getCurrUserId(HttpServletRequest request) {
		return String.valueOf(request.getAttribute("currUserId"));
	}


	protected String getCurrUserFullname(HttpServletRequest request) {
		return String.valueOf(request.getAttribute("currUserFname"));
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}


	public String getPwordEkey() {
		return messageService.getMessage(BaseConfigConstants.SVC_IDM_EKEY);
	}

}