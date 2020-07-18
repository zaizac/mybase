/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.be.sdk.client.BeServiceClient;
import com.bff.cmn.form.StaticData;
import com.bff.util.constants.ConfigConstants;
import com.dm.sdk.client.DmServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.wfw.sdk.client.WfwServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
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

	@Value("${" + ConfigConstants.SVC_BE_URL + "}")
	private String beUrl;

	@Value("${" + ConfigConstants.SVC_BE_TIMEOUT + "}")
	private int beTimeout;

	@Value("${" + BaseConfigConstants.SVC_DM_URL + "}")
	private String dmUrl;

	@Value("${" + BaseConfigConstants.SVC_DM_TIMEOUT + "}")
	private int dmTimeout;

	@Value("${" + BaseConfigConstants.SVC_WFW_URL + "}")
	private String wfwUrl;

	@Value("${" + BaseConfigConstants.SVC_WFW_TIMEOUT + "}")
	private int wfwTimeout;

	@Value("${" + ConfigConstants.SVC_REPORT_URL + "}")
	private String rptUrl;

	@Value("${" + ConfigConstants.SVC_REPORT_TIMEOUT + "}")
	private int rptTimeout;


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}


	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


	@Bean
	public StaticData staticData() {
		return new StaticData();
	}


	@Bean
	public IdmServiceClient idmService() {
		return new IdmServiceClient(idmUrl, idmTimeout);
	}


	@Bean
	public NotServiceClient notifyService() {
		return new NotServiceClient(notUrl, notTimeout);
	}


	@Bean
	public BeServiceClient apjatiService() {
		return new BeServiceClient(beUrl, beTimeout);
	}


	@Bean
	public DmServiceClient dmService() {
		return new DmServiceClient(dmUrl, dmTimeout);
	}


	@Bean
	public WfwServiceClient wfwService() {
		return new WfwServiceClient(wfwUrl, wfwTimeout);
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
		sb.append("DM Service URL:  " + dmUrl + TIMEOUT + dmTimeout + BaseConstants.NEW_LINE);
		sb.append("NOT Service URL: " + notUrl + TIMEOUT + notTimeout + BaseConstants.NEW_LINE);
		sb.append("WFW Service URL: " + wfwUrl + TIMEOUT + wfwTimeout + BaseConstants.NEW_LINE);
		sb.append("BE Service URL:  " + beUrl + TIMEOUT + beTimeout + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}

}