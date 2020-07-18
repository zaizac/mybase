/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);


	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.debug("Session is CREATED");
		event.getSession().setMaxInactiveInterval(30 * 60);
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOGGER.debug("Session is DESTROYED");
	}

}