/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);


	@Override
	public void sessionCreated(HttpSessionEvent event) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Session is CREATED");
		}
		event.getSession().setMaxInactiveInterval(40 * 60);
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Session is DESTROYED");
		}
	}

}