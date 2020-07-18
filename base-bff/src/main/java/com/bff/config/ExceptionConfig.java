/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
public class ExceptionConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionConfig.class);


	public ExceptionConfig() {
		LOGGER.info("Creating Exception Configuration");
	}


	@Bean(name = "simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		LOGGER.debug("Creating SimpleMappingExceptionResolver");
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

		Properties mappings = new Properties();
		r.setExceptionMappings(mappings); // None by default
		r.setExceptionAttribute("ErrorOccurred"); // Default is "exception"
		r.setDefaultErrorView("500");
		return r;
	}

}