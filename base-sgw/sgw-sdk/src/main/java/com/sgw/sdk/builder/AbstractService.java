/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.sdk.builder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgw.sdk.client.SgwRestTemplate;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public abstract class AbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract SgwRestTemplate restTemplate();


	public abstract String url();


	protected String getServiceURI(String serviceName) {
		String uri = url() + serviceName;
		LOGGER.info("Service Rest URL: {}", uri);
		return uri;
	}

}