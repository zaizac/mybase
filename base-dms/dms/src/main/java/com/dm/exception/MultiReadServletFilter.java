/**
 * Copyright 2019. 
 */
package com.dm.exception;


import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * The Class MultiReadServletFilter.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class MultiReadServletFilter implements Filter {

	/** The Constant MULTI_READ_HTTP_METHODS. */
	private static final Set<String> MULTI_READ_HTTP_METHODS = new TreeSet<>(
			// Enable Multi-Read for DELETE, PUT and POST requests
			Arrays.asList("DELETE", "PUT", "POST"));


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			// Check wether the current request needs to be able to support
			// the body to be read multiple times
			if (MULTI_READ_HTTP_METHODS.contains(request.getMethod())) {
				// Override current HttpServletRequest with custom
				// implementation
				filterChain.doFilter(new MultiReadHttpServletRequest(request), servletResponse);
				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Auto generated method
	}


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Auto generated method
	}
}
