/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.notify.config.ConfigConstants;
import com.baseboot.notify.sdk.constants.NotUrlConstants;
import com.baseboot.notify.sdk.model.ServiceCheck;
import com.baseboot.notify.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(NotUrlConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractRestController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	DataSource dataSource;

	private static final String SERVICE_NAME = "NOTIFICATION Service";

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

		// DB CONNECTION
		String mysqlUrl = messageSource.getMessage(ConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		try {
			dataSource.getConnection().isClosed();
			if (!BaseUtil.isObjNull(dataSource.getConnection())) {
				svcTest.setMysql(new ServiceCheck("MySQL Connection", mysqlUrl, SUCCESS));
			} else {
				svcTest.setMysql(new ServiceCheck("MySQL Connection", mysqlUrl, FAILED));
			}
		} catch (Exception e) {
			svcTest.setMysql(new ServiceCheck("MySQL Connection", mysqlUrl, FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(FAILED);
		}

		// IDM Service
		String idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			String str = getIdmService(request).checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(new ServiceCheck("IDM Service", idmUrl, FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(FAILED);
		}

		// REPORT Service
		/*
		 * FIXME: String rptUrl =
		 * messageSource.getMessage(ConfigConstants.SVC_RPT_URL, null,
		 * Locale.getDefault()); try { String str =
		 * getRptService(request).checkConnection(); svcTest.setReport(new
		 * ServiceCheck(str, rptUrl, SUCCESS)); } catch (Exception e) {
		 * svcTest.setReport(new ServiceCheck("REPORT Service", rptUrl, FAILED
		 * + " [" + e.getMessage() + "]"));
		 * svcTest.setServiceResponse(FAILED); }
		 */

		return svcTest;
	}

}