/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.controller;


import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.be.sdk.client.BeServiceClient;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.rmq.constants.ProjectEnum;
import com.rmq.constants.SgwTxnCodeConstants;
import com.util.UidGenerator;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public abstract class AbstractRestController {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	protected IdmServiceClient idmService;

	@Autowired
	protected BeServiceClient beService;

	@Autowired
	private DmServiceClient dmService;

	@Autowired
	private NotServiceClient notService;


	public IdmServiceClient getIdmService() {
		idmService.setToken(messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
		idmService.setClientId(messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		idmService.setMessageId(UidGenerator.getMessageId());
		return idmService;
	}
	
	public IdmServiceClient getIdmService(HttpServletRequest request) {
		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(SgwTxnCodeConstants.HEADER_AUTHORIZATION);
		idmService.setAuthToken(auth);
		idmService.setMessageId(messageId);
		return idmService;
	}


	public BeServiceClient getBeService(HttpServletRequest request) {
		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(SgwTxnCodeConstants.HEADER_AUTHORIZATION);
		beService.setAuthToken(auth);
		beService.setMessageId(messageId);
		return beService;
	}


	public NotServiceClient getNotifyService(HttpServletRequest request) {
		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(SgwTxnCodeConstants.HEADER_AUTHORIZATION);
		notService.setAuthToken(auth);
		notService.setMessageId(messageId);
		return notService;
	}


	public DmServiceClient getDmService(HttpServletRequest request, ProjectEnum project) {
		if (project != null) {
			dmService.setProjId(project.getName());
		}
		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(SgwTxnCodeConstants.HEADER_AUTHORIZATION);
		dmService.setAuthToken(auth);
		dmService.setMessageId(messageId);
		return dmService;
	}
}