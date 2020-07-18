/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.controller;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.be.sdk.client.BeServiceClient;
import com.dm.sdk.client.DmServiceClient;
import com.idm.sdk.client.IdmServiceClient;
import com.notify.sdk.client.NotServiceClient;
import com.sgw.constants.ProjectEnum;
import com.sgw.constants.SgwTxnCodeConstants;
import com.sgw.sdk.client.constants.BaseConstants;
import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.sgw.sdk.model.MessageResponse;
import com.util.BaseUtil;


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


	public DmServiceClient getDmService(HttpServletRequest request, String dmBucket) {
		dmService.setProjId(dmBucket);
		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(SgwTxnCodeConstants.HEADER_AUTHORIZATION);
		dmService.setAuthToken(auth);
		dmService.setMessageId(messageId);
		return dmService;
	}


	public String convertAppNoSlashToDash(String appNo) {
		if (appNo == null) {
			return null;
		}
		return appNo.replace(BaseConstants.SLASH, BaseConstants.DASH);
	}


	public String convertAppNoDashToSlash(String appNo) {
		if (appNo == null) {
			return null;
		}
		return appNo.replace(BaseConstants.DASH, BaseConstants.SLASH);
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}
	
	protected MessageResponse invalidResponse(String path) {
		MessageResponse mobileResponse = new MessageResponse();
		mobileResponse.setCode(SgwErrorCodeEnum.E400SGW003.name());
		mobileResponse.setMessage(SgwErrorCodeEnum.E400SGW003.getMessage());
		mobileResponse.setPath(path);
		mobileResponse.setTimestamp(getSQLTimestamp());
		return mobileResponse;
	}
	
	protected String genFileName(String fileNm, String contentType) {
		String filename = fileNm;
		if (BaseUtil.isEquals(contentType, "image/jpeg")) {
			filename += ".jpg";
		} else if (BaseUtil.isEquals(contentType, "image/gif")) {
			filename += ".gif";
		} else if (BaseUtil.isEquals(contentType, "image/png")) {
			filename += ".png";
		} else if (BaseUtil.isEquals(contentType, "application/pdf")) {
			filename += ".pdf";
		}
		return filename;
	}
	
}