/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core.exception;


import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);


	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		LOGGER.info("Exception message: {}", throwable.getMessage());
		LOGGER.info("Method name: {}", method.getName());
		for (Object param : obj) {
			LOGGER.info("Parameter value: {}", param);
		}
	}

}