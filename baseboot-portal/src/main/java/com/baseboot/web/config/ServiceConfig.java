/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


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

import com.baseboot.web.util.PaymentSwitch;
import com.dm.sdk.client.DmServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.report.sdk.client.ReportServiceClient;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
public class ServiceConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

	private String idmUrl;

	private int idmTimeout;

	private String notUrl;

	private int notTimeout;

	private String rptUrl;

	private int rptTimeout;

	private String beUrl;

	private int beTimeout;

	private String dmUrl;

	private int dmTimeout;

	private String wfwUrl;

	private int wfwTimeout;

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
	public NotServiceClient notifyService() {
		return new NotServiceClient(notUrl, notTimeout);
	}


	@Bean
	public ReportServiceClient reportService() {
		return new ReportServiceClient(rptUrl, rptTimeout);
	}


	@Bean
	public DmServiceClient dmService() {
		return new DmServiceClient(dmUrl, dmTimeout);
	}


	@Bean
	public PaymentSwitch paymentSwitch() {
		return new PaymentSwitch();
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		idmTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_IDM_TIMEOUT, null, Locale.getDefault()));

		notUrl = messageSource.getMessage(ConfigConstants.SVC_NOT_URL, null, Locale.getDefault());
		notTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_NOT_TIMEOUT, null, Locale.getDefault()));

		rptUrl = messageSource.getMessage(ConfigConstants.SVC_RPT_URL, null, Locale.getDefault());
		rptTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_RPT_TIMEOUT, null, Locale.getDefault()));

		beUrl = messageSource.getMessage(ConfigConstants.SVC_APJ_URL, null, Locale.getDefault());
		beTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_APJ_TIMEOUT, null, Locale.getDefault()));

		dmUrl = messageSource.getMessage(ConfigConstants.SVC_DM_URL, null, Locale.getDefault());
		dmTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_DM_TIMEOUT, null, Locale.getDefault()));

		wfwUrl = messageSource.getMessage(ConfigConstants.SVC_WFW_URL, null, Locale.getDefault());
		wfwTimeout = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.SVC_WFW_TIMEOUT, null, Locale.getDefault()));

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Integration Service");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("IDM Service URL: " + idmUrl + "\t- Timeout (seconds): " + idmTimeout + BaseConstants.NEW_LINE);
		sb.append("DM Service URL: " + dmUrl + "\t- Timeout (seconds): " + dmTimeout + BaseConstants.NEW_LINE);
		sb.append("Notification Service URL: " + notUrl + "\t- Timeout (seconds): " + notTimeout
				+ BaseConstants.NEW_LINE);
		sb.append("Report Service URL: " + rptUrl + "\t- Timeout (seconds): " + rptTimeout + BaseConstants.NEW_LINE);
		sb.append("Workflow Service URL: " + wfwUrl + "\t- Timeout (seconds): " + wfwTimeout
				+ BaseConstants.NEW_LINE);
		sb.append("BE Service URL: " + beUrl + "\t- Timeout (seconds): " + beTimeout);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.debug(sb.toString());
	}

}