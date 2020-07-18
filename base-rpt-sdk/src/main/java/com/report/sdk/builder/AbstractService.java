/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.report.sdk.builder;


import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.report.sdk.client.ReportRestTemplate;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 17, 2015
 */
public abstract class AbstractService {

	protected Logger logger = LoggerFactory.getLogger(getClass());


	public abstract ReportRestTemplate restTemplate();


	public abstract Properties prop();


	public abstract String url();


	protected String getServiceURI(String serviceName) {
		if (serviceName.contains("${prefix}")) {
			serviceName = serviceName.replace("${prefix}", prop().getProperty("prefix"));
		}
		if (serviceName.contains("${version}")) {
			serviceName = serviceName.replace("${version}", prop().getProperty("version"));
		}
		String uri = url() + serviceName;
		logger.info("Service Rest URL: {}", uri);
		return uri;
	}

}
