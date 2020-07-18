/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.idm.sdk.client.IdmServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	@Autowired
	MessageSource messageSource;

	@Value("${" + BaseConfigConstants.SVC_IDM_URL + "}")
	private String idmUrl;

	@Value("${" + BaseConfigConstants.SVC_IDM_TIMEOUT + "}")
	private int idmTimeout;


	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + "\t- Timeout (seconds): " + idmTimeout);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}
}
