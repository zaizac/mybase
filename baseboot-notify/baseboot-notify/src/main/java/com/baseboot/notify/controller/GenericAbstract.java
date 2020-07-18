/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.notify.model.NotConfig;
import com.baseboot.notify.sdk.constants.BaseConstants;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.baseboot.notify.service.NotConfigService;
// FIXME: import com.baseboot.report.sdk.client.ReportServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class GenericAbstract implements BaseConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericAbstract.class);

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	private IdmServiceClient idmService;

	/*
	 * FIXME: @Autowired private ReportServiceClient rptService;
	 */

	@Autowired
	NotConfigService notConfigSvc;


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(HEADER_AUTHORIZATION));
		idmService.setMessageId(
				StringUtils.hasText(request.getHeader(HEADER_MESSAGE_ID)) ? request.getHeader(HEADER_MESSAGE_ID)
						: String.valueOf(UUID.randomUUID()));
		return idmService;
	}


	/*
	 * FIXME: protected ReportServiceClient getRptService(HttpServletRequest
	 * request) {
	 * rptService.setAuthToken(request.getHeader(HEADER_AUTHORIZATION));
	 * rptService.setMessageId(StringUtils.hasText(request.getHeader(
	 * HEADER_MESSAGE_ID)) ? request.getHeader(HEADER_MESSAGE_ID) :
	 * String.valueOf(UUID.randomUUID())); return rptService; }
	 */

	protected String getConfigValue(String configCode) {
		NotConfig config = notConfigSvc.findByConfigCode(configCode);
		if (!BaseUtil.isObjNull(config)) {
			return config.getConfigVal();
		}
		return "";
	}

}