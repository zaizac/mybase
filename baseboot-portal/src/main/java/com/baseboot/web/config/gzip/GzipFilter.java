/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.web.config.gzip;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 16, 2018
 */
public class GzipFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(GzipFilter.class);


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(supportsGzip(request)) {
			LOGGER.debug("Processing GZIPFilter");
			GzipResponseWrapper gzipResponse = new GzipResponseWrapper((HttpServletResponse) response);
			chain.doFilter(request, gzipResponse);
			gzipResponse.finish();
			return;
		}
		chain.doFilter(request, response);
	}


	private boolean supportsGzip(ServletRequest request) {
		String acceptEncoding = ((HttpServletRequest) request).getHeader("Accept-Encoding");
		return acceptEncoding != null && acceptEncoding.contains("gzip");
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
