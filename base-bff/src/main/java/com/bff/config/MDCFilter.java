/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.JsonObject;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 25, 2018
 */
public class MDCFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MDCFilter.class);

	private static final String TRACE_ID = "TraceId";

	private static final String LOGGED_IN_USER = "LoggedInUser";


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
		if (BaseUtil.isObjNull(messageId)) {
			messageId = String.valueOf(UUID.randomUUID());
			request.setAttribute(BaseConstants.HEADER_MESSAGE_ID, messageId);
		}
		JsonObject obj = new JsonObject();
		obj.addProperty(TRACE_ID, messageId);
		obj.addProperty(LOGGED_IN_USER, String.valueOf(request.getAttribute("currUserId")));
		LOGGER.debug("MDC Trace ID: {}", obj);
		MDC.put(TRACE_ID, obj.toString());
		
		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(TRACE_ID);
		}

	}
}
