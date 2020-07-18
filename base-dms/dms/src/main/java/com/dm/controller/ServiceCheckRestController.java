/**
 * Copyright 2019. 
 */
package com.dm.controller;


import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dm.sdk.client.constants.DocMgtConstants;
import com.idm.sdk.client.IdmServiceClient;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.ServiceCheck;


/**
 * The Class ServiceCheckRestController.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
@RequestMapping(DocMgtConstants.SERVICE_CHECK)
public class ServiceCheckRestController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCheckRestController.class);

	/** The message source. */
	@Autowired
	MessageSource messageSource;

	/** The Constant SERVICE_NAME. */
	private static final String SERVICE_NAME = "DM Service";


	/**
	 * Service check.
	 *
	 * @return the string
	 */
	@GetMapping(value = "test", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return SERVICE_NAME;
	}


	/**
	 * Service check.
	 *
	 * @param request the request
	 * @return the service check
	 */
	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// IDM Service
		String idmUrl = messageSource.getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			IdmServiceClient idmService = new IdmServiceClient(idmUrl);
			idmService.setToken(
					messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
			idmService.setClientId(
					messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
			String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
			if (messageId == null) {
				messageId = String.valueOf(UUID.randomUUID());
			}
			idmService.setMessageId(messageId);
			String auth = request.getHeader(BaseConstants.HEADER_AUTHORIZATION);
			idmService.setAuthToken(auth);
			String str = idmService.checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(
					new ServiceCheck("IDM Service", idmUrl, BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}

}