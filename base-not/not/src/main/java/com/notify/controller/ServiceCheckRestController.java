/**
 * Copyright 2019. 
 */
package com.notify.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notify.sdk.constants.NotUrlConstants;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.ServiceCheck;



/**
 * The Class ServiceCheckRestController.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(NotUrlConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractRestController {

	/** The data source. */
	@Autowired
	DataSource dataSource;

	/** The Constant SERVICE_NAME. */
	private static final String SERVICE_NAME = "NOTIFICATION Service";


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

		final String MYSQL_CONNECTION_TEXT = "MySQL Connection";
		// DB CONNECTION
		String mysqlUrl = messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		try {
			if (!BaseUtil.isObjNull(dataSource.getConnection())) {
				svcTest.setMysql(new ServiceCheck(MYSQL_CONNECTION_TEXT, mysqlUrl, BaseConstants.SUCCESS));
			} else {
				svcTest.setMysql(new ServiceCheck(MYSQL_CONNECTION_TEXT, mysqlUrl, BaseConstants.FAILED));
			}
		} catch (Exception e) {
			svcTest.setMysql(new ServiceCheck(MYSQL_CONNECTION_TEXT, mysqlUrl,
					BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		// IDM Service
		String idmUrl = messageSource.getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			String str = getIdmService(request).checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(
					new ServiceCheck("IDM Service", idmUrl, BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}

}