/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.config.ConfigConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConfigConstants;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.model.ServiceCheck;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractRestController {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	DataSource dataSource;

	@Autowired
	private IdmUserDao idmUserDao;

	private static final String SERVICE_NAME = "IDM Service";

	private static final String MYSQL_CONN = "MySQL Connection";


	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// LDAP CONNECTION
		String ldapUrl = messageSource.getMessage(ConfigConstants.LDAP_CONF_URL, null, Locale.getDefault());
		try {
			idmUserDao.searchEntry("sysprtal");
			svcTest.setLdap(new ServiceCheck("LDAP Connection", ldapUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setLdap(new ServiceCheck("LDAP Connection", ldapUrl,
					BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		// DB CONNECTION
		String mysqlUrl = messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		try {
			if (!BaseUtil.isObjNull(dataSource.getConnection())) {
				svcTest.setMysql(new ServiceCheck(MYSQL_CONN, mysqlUrl, BaseConstants.SUCCESS));
			} else {
				svcTest.setMysql(new ServiceCheck(MYSQL_CONN, mysqlUrl, BaseConstants.FAILED));
			}
		} catch (Exception e) {
			svcTest.setMysql(
					new ServiceCheck(MYSQL_CONN, mysqlUrl, BaseConstants.FAILED + " [" + e.getMessage() + "]"));
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