/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.rmq.constants.ProjectEnum;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class GenericAbstract {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	private DmServiceClient dmService;


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return idmService;
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

}