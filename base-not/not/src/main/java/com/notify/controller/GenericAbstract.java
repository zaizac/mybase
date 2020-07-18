/**
 * Copyright 2019. 
 */
package com.notify.controller;


import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.model.NotConfig;
import com.notify.service.NotConfigService;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;



/**
 * The Class GenericAbstract.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class GenericAbstract {

	/** The message source. */
	@Autowired
	protected MessageSource messageSource;

	/** The idm service. */
	@Autowired
	private IdmServiceClient idmService;

	/** The dm service. */
	@Autowired
	private DmServiceClient dmService;

	/** The not config svc. */
	@Autowired
	protected NotConfigService notConfigSvc;


	/**
	 * Gets the idm service.
	 *
	 * @param request the request
	 * @return the idm service
	 */
	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setSystemType(request.getHeader(BaseConstants.HEADER_SYSTEM_TYPE));
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return idmService;
	}
	
	protected IdmServiceClient getIdmService() {
		idmService.setToken(messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
		idmService.setClientId(
				messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		idmService.setMessageId(String.valueOf(UUID.randomUUID()));
		return idmService;
	}


	/**
	 * Gets the dm service.
	 *
	 * @return the dm service
	 */
	protected DmServiceClient getDmService() {
		dmService.setToken(messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
		dmService.setClientId(
				messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		dmService.setMessageId(String.valueOf(UUID.randomUUID()));
		return dmService;
	}


	/**
	 * Gets the config value.
	 *
	 * @param configCode the config code
	 * @return the config value
	 */
	protected String getConfigValue(String configCode) {
		NotConfig config = notConfigSvc.findByConfigCode(configCode);
		if (!BaseUtil.isObjNull(config)) {
			return config.getConfigVal();
		}
		return "";
	}

}