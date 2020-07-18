/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.util.concurrent.atomic.AtomicInteger;

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

	private final AtomicInteger sessionCount = new AtomicInteger();


	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.info("Session is CREATED");
		event.getSession().setMaxInactiveInterval(30 * 60);
		sessionCount.incrementAndGet();
		setActiveSessionCount(event);
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOGGER.info("Session is DESTROYED");
		sessionCount.decrementAndGet();
		setActiveSessionCount(event);
	}


	private void setActiveSessionCount(HttpSessionEvent se) {
		se.getSession().getServletContext().setAttribute("activeSessions", sessionCount.get());
		LOGGER.info("Total Active Session: {}", sessionCount.get());
	}

}