/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.web.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baseboot.web.config.ConfigConstants;
import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.constants.AppConstants;
import com.baseboot.web.constants.ProjectEnum;
import com.baseboot.web.util.MessageService;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.notify.sdk.client.NotServiceClient;
import com.report.sdk.client.ReportServiceClient;
import com.util.UidGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public abstract class GenericAbstract implements AppConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericAbstract.class);

	@Autowired
	protected MessageService messageService;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	private NotServiceClient notifyService;

	@Autowired
	private ReportServiceClient reportService;

	@Autowired
	private DmServiceClient dmService;


	public IdmServiceClient getIdmService(String userId, String token) {
		idmService.setClientId(userId);
		idmService.setToken(token);
		return idmService;
	}


	public IdmServiceClient getIdmService() {
		String authToken = getAuthToken();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("authToken: " + authToken);
		}
		if (authToken != null) {
			idmService.setToken(authToken);
		} else {
			idmService.setToken(messageService.getMessage(ConfigConstants.SVC_IDM_SKEY));
		}
		idmService.setClientId(messageService.getMessage(ConfigConstants.SVC_IDM_CLIENT));
		idmService.setMessageId(UidGenerator.getMessageId());
		return idmService;
	}


	public NotServiceClient getNotifyService() {
		String authToken = getAuthToken();
		if (authToken != null) {
			notifyService.setToken(authToken);
		} else {
			notifyService.setToken(messageService.getMessage(ConfigConstants.SVC_IDM_SKEY));
		}
		notifyService.setClientId(messageService.getMessage(ConfigConstants.SVC_IDM_CLIENT));
		notifyService.setMessageId(UidGenerator.getMessageId());
		return notifyService;
	}


	public ReportServiceClient getReportService() {
		String authToken = getAuthToken();
		if (authToken != null) {
			reportService.setToken(authToken);
		} else {
			reportService.setToken(messageService.getMessage(ConfigConstants.SVC_IDM_SKEY));
		}
		reportService.setClientId(messageService.getMessage(ConfigConstants.SVC_IDM_CLIENT));
		reportService.setMessageId(UidGenerator.getMessageId());
		return reportService;
	}


	public DmServiceClient getDmService(ProjectEnum project) {
		if (project != null) {
			dmService.setProjId(project.getName());
		}
		String authToken = getAuthToken();
		if (authToken != null) {
			dmService.setToken(authToken);
		} else {
			dmService.setToken(messageService.getMessage(ConfigConstants.SVC_IDM_SKEY));
		}
		dmService.setClientId(messageService.getMessage(ConfigConstants.SVC_IDM_CLIENT));
		dmService.setMessageId(UidGenerator.getMessageId());
		return dmService;
	}


	private String getAuthToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			Token token = cud.getAuthToken();
			if (token != null) {
				return token.getAccessToken();
			}
		}
		return null;
	}

}