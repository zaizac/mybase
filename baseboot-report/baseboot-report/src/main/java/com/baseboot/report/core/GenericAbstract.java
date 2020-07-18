/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.baseboot.be.sdk.client.BeServiceClient;
import com.baseboot.dm.sdk.client.DmServiceClient;
import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.report.mail.MailService;
import com.baseboot.report.util.ProjectEnum;


/**
 * @author Mary Jane Buenaventura
 * @since 06/06/2016
 */
public abstract class GenericAbstract {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected MailService mailService;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	private BeServiceClient apjatiService;

	@Autowired
	private DmServiceClient dmService;


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID) : String.valueOf(UUID.randomUUID()));
		return idmService;
	}


	public BeServiceClient getApjatiService(HttpServletRequest request) {
		apjatiService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		apjatiService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID) : String.valueOf(UUID.randomUUID()));
		return apjatiService;
	}


	protected DmServiceClient getDmService(ProjectEnum project, HttpServletRequest request) {
		dmService.setProjId(project.getName());
		return getDmService(request);
	}


	protected DmServiceClient getDmService(HttpServletRequest request) {
		dmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		dmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID) : String.valueOf(UUID.randomUUID()));
		return dmService;
	}
}
