/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.config;


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

import com.baseboot.be.sdk.client.BeServiceClient;
import com.baseboot.dm.sdk.client.DmServiceClient;
import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.report.util.ConfigConstants;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	private String idmUrl;

	private int idmTimeout;

	private String beUrl;

	private int beTimeout;

	private String dmUrl;

	private int dmTimeout;

	@Autowired
	MessageSource messageSource;


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}


	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	@Bean
	public BeServiceClient apjatiService() {
		return new BeServiceClient(beUrl, beTimeout);
	}


	@Bean
	public DmServiceClient dmService() {
		return new DmServiceClient(dmUrl, dmTimeout);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		idmTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_IDM_TIMEOUT, null, Locale.getDefault()));

		beUrl = messageSource.getMessage(ConfigConstants.SVC_BE_URL, null, Locale.getDefault());
		beTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_BE_TIMEOUT, null, Locale.getDefault()));

		dmUrl = messageSource.getMessage(ConfigConstants.SVC_DM_URL, null, Locale.getDefault());
		dmTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_DM_TIMEOUT, null, Locale.getDefault()));

		StringBuffer sb = new StringBuffer();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + "\t- Timeout (seconds): " + idmTimeout + BaseConstants.NEW_LINE);
		sb.append("Backend Service URL: " + beUrl + "\t- Timeout (seconds): " + beTimeout + BaseConstants.NEW_LINE);
		sb.append("Report Service URL: " + dmUrl + "\t- Timeout (seconds): " + dmTimeout);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());
	}

}