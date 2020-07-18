/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.rest.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.ServiceCheck;
import com.wfw.core.AbstractController;
import com.wfw.sdk.constants.WorkflowConstants;


/**
 * @author Mary Jane Buenaventura
 * @since 22/08/2016
 */
@RestController
@RequestMapping(WorkflowConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	private static final String SERVICE_NAME = "WORKFLOW Service";


	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// DB CONNECTION
		try {
			svcTest.setMysql(new ServiceCheck("MySQL Connection",
					messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault()),
					BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setMysql(new ServiceCheck("MySQL Connection",
					messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault()),
					BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}


	@GetMapping(value = "test", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return SERVICE_NAME;
	}

}