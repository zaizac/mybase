/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util.helper;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class WebHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebHelper.class);

	@Autowired
	private HttpServletRequest request;


	public String baseUrl() {
		String host = request.getHeader("X-Forwarded-Host");
		return "//" + (!BaseUtil.isObjNull(host) ? host : request.getHeader("Host"));
	}


}