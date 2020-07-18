/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.controller;


import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.dm.config.ConfigConstants;
import com.baseboot.dm.sdk.client.constants.BaseConstants;
import com.baseboot.dm.sdk.client.constants.DocMgtConstants;
import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.idm.sdk.model.ServiceCheck;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
@RequestMapping(DocMgtConstants.SERVICE_CHECK)
public class ServiceCheckRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCheckRestController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	MongoTemplate mongoTemplate;

	private static final String SERVICE_NAME = "DM Service";

	private static final String SUCCESS = "SUCCESS";

	private static final String FAILED = "FAILED";


	@RequestMapping(value = "test", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return SERVICE_NAME;
	}


	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)), SUCCESS);

		// MONGO Connection
		String mongodb = messageSource.getMessage(ConfigConstants.MONGO_CONF_HOST, null, Locale.getDefault()) + ":"
				+ messageSource.getMessage(ConfigConstants.MONGO_CONF_PORT, null, Locale.getDefault());

		try {
			LOGGER.debug(mongoTemplate.getDb().getMongo().toString());
			svcTest.setMongodb(new ServiceCheck("MONGO DB Connection", mongodb, SUCCESS));
		} catch (Exception e) {
			svcTest.setMongodb(
					new ServiceCheck("MONGO DB Connection", mongodb, FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(FAILED);
		}

		// IDM Service
		String idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			IdmServiceClient idmService = new IdmServiceClient(idmUrl);
			idmService.setToken(messageSource.getMessage("idm.service.skey", null, Locale.getDefault()));
			idmService.setClientId(messageSource.getMessage("idm.service.client", null, Locale.getDefault()));
			String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
			if (messageId == null)
				messageId = String.valueOf(UUID.randomUUID());
			idmService.setMessageId(messageId);
			String auth = request.getHeader(BaseConstants.HEADER_AUTHORIZATION);
			idmService.setAuthToken(auth);
			String str = idmService.checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(new ServiceCheck("IDM Service", idmUrl, FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(FAILED);
		}

		return svcTest;
	}

}