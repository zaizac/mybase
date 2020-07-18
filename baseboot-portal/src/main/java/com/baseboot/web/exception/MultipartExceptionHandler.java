/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.exception;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class MultipartExceptionHandler extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultipartExceptionHandler.class);


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (MaxUploadSizeExceededException e) {
			LOGGER.error("MaxUploadSizeExceededException: {}", e.getMessage());
			handle(request, response, e);
		} catch (ServletException e) {
			LOGGER.error("ServletException: {}", e.getMessage());
			if (e.getRootCause() instanceof MaxUploadSizeExceededException) {
				handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
			} else {
				throw e;
			}
		}
	}


	private void handle(HttpServletRequest request, HttpServletResponse response, MaxUploadSizeExceededException e)
			throws ServletException, IOException {

		String redirect = UrlUtils.buildFullRequestUrl(request) + "?error";
		response.sendRedirect(redirect);
	}

}