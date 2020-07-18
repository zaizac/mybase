/**
 * Copyright 2019
 */
package com.be.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.wfw.sdk.client.WfwServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	@Value("${" + BaseConfigConstants.SVC_IDM_URL + "}")
	private String idmUrl;

	@Value("${" + BaseConfigConstants.SVC_IDM_TIMEOUT + "}")
	private int idmTimeout;

	@Value("${" + BaseConfigConstants.SVC_NOT_URL + "}")
	private String notUrl;

	@Value("${" + BaseConfigConstants.SVC_NOT_TIMEOUT + "}")
	private int notTimeout;

	@Value("${" + BaseConfigConstants.SVC_WFW_URL + "}")
	private String wfwUrl;

	@Value("${" + BaseConfigConstants.SVC_WFW_TIMEOUT + "}")
	private int wfwTimeout;

	@Value("${" + BaseConfigConstants.SVC_DM_URL + "}")
	private String dmUrl;

	@Value("${" + BaseConfigConstants.SVC_DM_TIMEOUT + "}")
	private int dmTimeout;


	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	@Bean
	public NotServiceClient notifyService() {
		return new NotServiceClient(notUrl, notTimeout);
	}


	@Bean
	public WfwServiceClient wfwService() {
		return new WfwServiceClient(wfwUrl, wfwTimeout);
	}


	@Bean
	public DmServiceClient dmService() {
		return new DmServiceClient(dmUrl, dmTimeout);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		final String TIMEOUT = "\t- Timeout (seconds): ";
		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + TIMEOUT + idmTimeout + BaseConstants.NEW_LINE);
		sb.append("NOT Service URL: " + notUrl + TIMEOUT + notTimeout + BaseConstants.NEW_LINE);
		sb.append("WFW Service URL: " + wfwUrl + TIMEOUT + wfwTimeout + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}

}