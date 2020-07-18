/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.config;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.idm.sdk.constants.BaseConstants;
//FIXME: import com.baseboot.report.sdk.client.ReportServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	private String idmUrl;

	private int idmTimeout;

	private String rptUrl;

	private int rptTimeout;

	@Autowired
	MessageSource messageSource;


	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	/*
	 * FIXME: @Bean public ReportServiceClient rptService() { return new
	 * ReportServiceClient(rptUrl, rptTimeout); }
	 */

	@Override
	public void afterPropertiesSet() throws Exception {
		idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		idmTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_IDM_TIMEOUT, null, Locale.getDefault()));

		rptUrl = messageSource.getMessage(ConfigConstants.SVC_RPT_URL, null, Locale.getDefault());
		rptTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_RPT_TIMEOUT, null, Locale.getDefault()));

		StringBuffer sb = new StringBuffer();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + "\t- Timeout (seconds): " + idmTimeout + BaseConstants.NEW_LINE);
		sb.append("Report Service URL: " + rptUrl + "\t- Timeout (seconds): " + rptTimeout);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());
	}

}