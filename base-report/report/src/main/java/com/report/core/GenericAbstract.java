/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.core;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.be.sdk.client.BeServiceClient;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.report.mail.MailService;
import com.util.constants.BaseConstants;


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
	private BeServiceClient beService;

	@Autowired
	private DmServiceClient dmService;


	protected IdmServiceClient getIdmService(HttpServletRequest request) {
		idmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		idmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return idmService;
	}


	public BeServiceClient getBeService(HttpServletRequest request) {
		beService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		beService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return beService;
	}


	protected DmServiceClient getDmService(String projectName, HttpServletRequest request) {
		dmService.setProjId(projectName);
		return getDmService(request);
	}


	protected DmServiceClient getDmService(HttpServletRequest request) {
		dmService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
		dmService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
				? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
				: String.valueOf(UUID.randomUUID()));
		return dmService;
	}
}
