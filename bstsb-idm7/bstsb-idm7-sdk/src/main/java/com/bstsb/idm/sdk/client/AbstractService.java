/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mary Jane Buenaventura
 * @since 07 Jul 2018
 */
public abstract class AbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
	
	public abstract IdmRestTemplate restTemplate();
	public abstract String url();
	
	protected String getServiceURI(String serviceName){	
		String uri = url() + serviceName;
		LOGGER.info("Service Rest URL: " + uri);
		return uri;
	}
	
}
