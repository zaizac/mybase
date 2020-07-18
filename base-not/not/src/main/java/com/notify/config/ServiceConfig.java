/**
 * Copyright 2019. 
 */
package com.notify.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;



/**
 * The Class ServiceConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	/** The idm url. */
	@Value("${" + BaseConfigConstants.SVC_IDM_URL + "}")
	private String idmUrl;

	/** The idm timeout. */
	@Value("${" + BaseConfigConstants.SVC_IDM_TIMEOUT + "}")
	private int idmTimeout;

	/** The dm url. */
	@Value("${" + BaseConfigConstants.SVC_DM_URL + "}")
	private String dmUrl;

	/** The dm timeout. */
	@Value("${" + BaseConfigConstants.SVC_DM_TIMEOUT + "}")
	private int dmTimeout;

	/** The message source. */
	@Autowired
	MessageSource messageSource;


	/**
	 * Idm service.
	 *
	 * @return the idm service client
	 */
	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	/**
	 * Dm service.
	 *
	 * @return the dm service client
	 */
	@Bean
	public DmServiceClient dmService() {
		return new DmServiceClient(dmUrl, dmTimeout);
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + "\t- Timeout (seconds): " + idmTimeout + BaseConstants.NEW_LINE);
		sb.append("DM Service URL: " + dmUrl + "\t- Timeout (seconds): " + dmTimeout + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}

}