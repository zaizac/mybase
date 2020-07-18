/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.core;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.be.sdk.client.BeServiceClient;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.wfw.sdk.client.WfwServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public abstract class GenericAbstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericAbstract.class);

	@Autowired
	protected MessageService messageService;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	private NotServiceClient notifyService;

	@Autowired
	private BeServiceClient beService;

	@Autowired
	private DmServiceClient dmService;

	@Autowired
	private WfwServiceClient wfwService;

	@Autowired
	private HttpServletRequest request;


	public IdmServiceClient getIdmService(String userId, String token) {
		idmService.setClientId(userId);
		idmService.setToken(token);
		idmService.setPortalType(getPortalType());
		return idmService;
	}


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(getMessageId());
		idmService.setPortalType(getPortalType());
		return idmService;
	}


	public IdmServiceClient getIdmService() {
		String authToken = getAuthToken();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("authToken: {}", authToken);
		}
		if (authToken != null) {
			idmService.setAuthToken(authToken);
		} else {
			idmService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
			idmService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
		}
		idmService.setMessageId(getMessageId());
		idmService.setPortalType(getPortalType());
		return idmService;
	}


	public NotServiceClient getNotifyService() {
		String authToken = getAuthToken();
		if (authToken != null) {
			notifyService.setAuthToken(authToken);
		} else {
			notifyService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
			notifyService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
		}
		notifyService.setMessageId(getMessageId());
		notifyService.setPortalType(getPortalType());
		return notifyService;
	}


	public BeServiceClient getBeService() {
		String authToken = getAuthToken();
		if (authToken != null) {
			beService.setAuthToken(authToken);
		} else {
			beService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
			beService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
		}
		beService.setMessageId(getMessageId());
		beService.setPortalType(getPortalType());
		return beService;
	}


	protected BeServiceClient getBeService(HttpServletRequest request) {
		beService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		beService.setMessageId(getMessageId());
		beService.setPortalType(getPortalType());
		return beService;
	}


	public DmServiceClient getDmService(String dmBucket) {
		if (dmBucket != null) {
			dmService.setProjId(dmBucket);
		}
		String authToken = getAuthToken();
		if (authToken != null) {
			dmService.setToken(authToken);
		} else {
			dmService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
		}
		dmService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
		dmService.setMessageId(getMessageId());
		dmService.setPortalType(getPortalType());
		return dmService;
	}


	protected DmServiceClient getDmService(HttpServletRequest request, String dmBucket) {
		if (dmBucket != null) {
			dmService.setProjId(dmBucket);
		}
		dmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		dmService.setMessageId(getMessageId());
		dmService.setPortalType(getPortalType());
		return dmService;
	}


	public WfwServiceClient getWfwService() {
		String authToken = getAuthToken();
		if (authToken != null) {
			wfwService.setAuthToken(authToken);
		} else {
			wfwService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
		}
		wfwService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
		wfwService.setMessageId(getMessageId());
		wfwService.setPortalType(getPortalType());
		return wfwService;
	}


	protected WfwServiceClient getWfwService(HttpServletRequest request) {
		wfwService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		wfwService.setMessageId(getMessageId());
		wfwService.setPortalType(getPortalType());
		return wfwService;
	}


	private String getAuthToken() {
		String authToken = request.getHeader(BaseConstants.HEADER_AUTHORIZATION);
		LOGGER.info("AUTH TOKEN: {}", authToken);
		return authToken;
	}

	private String getPortalType() {
		String portalType = request.getHeader(BaseConstants.HEADER_PORTAL_TYPE);
		LOGGER.info("PORTAL TYPE: {}", portalType);
		return portalType;
	}

	private String getMessageId() {
		return StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID());
	}

}