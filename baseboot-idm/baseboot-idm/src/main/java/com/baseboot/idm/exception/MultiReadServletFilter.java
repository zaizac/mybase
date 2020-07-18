/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.exception;


import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Component
public class MultiReadServletFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			// Check wether the current request needs to be able to support
			// the body to be read multiple times
			final Set<String> multiReadHttpMethods = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			multiReadHttpMethods.add("DELETE");
			multiReadHttpMethods.add("PUT");
			multiReadHttpMethods.add("POST");
			if (multiReadHttpMethods.contains(request.getMethod())) {
				// Override current HttpServletRequest with custom
				// implementation
				filterChain.doFilter(new MultiReadHttpServletRequest(request), servletResponse);
				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	@Override
	public void destroy() {
		// DO NOTHING
	}
}
