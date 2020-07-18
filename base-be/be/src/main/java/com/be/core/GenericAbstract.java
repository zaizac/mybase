/**
 * Copyright 2019
 */
package com.be.core;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.be.sdk.util.ProjectEnum;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.util.constants.BaseConstants;
import com.wfw.sdk.client.WfwServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public abstract class GenericAbstract {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	private NotServiceClient notifyService;

	@Autowired
	private WfwServiceClient wfwService;

	@Autowired
	private DmServiceClient dmService;


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return idmService;
	}


	public NotServiceClient getNotifyService(HttpServletRequest request) {
		notifyService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		notifyService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return notifyService;
	}


	protected WfwServiceClient getWfwService(HttpServletRequest request) {
		wfwService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		wfwService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return wfwService;
	}


	public DmServiceClient getDmService(HttpServletRequest request) {
		dmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		dmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return dmService;
	}


	public DmServiceClient getDmService(HttpServletRequest request, ProjectEnum project) {
		if (project != null) {
			dmService.setProjId(project.getName());
		}
		dmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		dmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return dmService;
	}


	public DmServiceClient getDmService(HttpServletRequest request, String projId) {
		if (projId != null) {
			return getDmService(request, ProjectEnum.findByName(projId));
		} else {
			return getDmService(request);
		}
	}


	protected String getCurrUserId(HttpServletRequest request) {
		return String.valueOf(request.getAttribute("currUserId"));
	}

}