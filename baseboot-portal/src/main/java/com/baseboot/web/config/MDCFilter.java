/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.web.config;


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

import com.baseboot.web.util.WebUtil;
import com.google.gson.JsonObject;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 25, 2018
 */
public class MDCFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MDCFilter.class);

	private static final String traceId = "TraceId" ;
	private static final String loggedInUser = "LoggedInUser" ;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String messageId = ((HttpServletRequest) request).getHeader(BaseConstants.HEADER_MESSAGE_ID);
		if (BaseUtil.isObjNull(messageId)) {
			messageId = String.valueOf(UUID.randomUUID());
			((HttpServletRequest) request).setAttribute(BaseConstants.HEADER_MESSAGE_ID, messageId);
		}
		JsonObject obj = new JsonObject();
		obj.addProperty(traceId, messageId);
		obj.addProperty(loggedInUser, WebUtil.getCurrentUserId());
		LOGGER.debug("MDC Trace ID: {}", obj.toString());
		MDC.put(traceId, obj.toString());

		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(traceId);
		}

	}
}
