/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.config;


import java.util.Locale;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.notify.sdk.client.NotServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	private String notUrl;

	private int notTimeout;

	@Autowired
	MessageSource messageSource;


	@Bean
	public NotServiceClient notifyService() {
		return new NotServiceClient(notUrl, notTimeout);
	}


	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		notUrl = messageSource.getMessage(ConfigConstants.SVC_NOT_URL, null, Locale.getDefault());
		notTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_NOT_TIMEOUT, null, Locale.getDefault()));

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Notification Service URL: " + notUrl + "\t- Timeout (seconds): " + notTimeout);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.debug(sb.toString());
	}

}