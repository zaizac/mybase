/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.builder;


import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.report.sdk.client.ReportRestTemplate;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public abstract class AbstractService {

	private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);


	public abstract ReportRestTemplate restTemplate();


	public abstract Properties prop();


	public abstract String url();


	protected String getServiceURI(String serviceName) {
		if (serviceName.contains("${prefix}"))
			serviceName = serviceName.replace("${prefix}", prop().getProperty("prefix"));
		if (serviceName.contains("${version}"))
			serviceName = serviceName.replace("${version}", prop().getProperty("version"));
		String uri = url() + serviceName;
		logger.debug("Service Rest URL: {}", uri);
		return uri;
	}

}
