package com.idm.exception;


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


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class MultiReadServletFilter implements Filter {

	private static final Set<String> MULTI_READ_HTTP_METHODS = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

	static {
		MULTI_READ_HTTP_METHODS.add("DELETE");
		MULTI_READ_HTTP_METHODS.add("PUT");
		MULTI_READ_HTTP_METHODS.add("POST");
	}


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


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	@Override
	public void destroy() {
		// DO NOTHING
	}

}
