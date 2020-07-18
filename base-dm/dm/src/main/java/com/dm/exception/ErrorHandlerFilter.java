/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.exception;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.util.MediaType;
import com.util.model.ErrorResponse;
import com.dm.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class ErrorHandlerFilter.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ErrorHandlerFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception ex) {
			// call ErrorHandler and dispatch to error jsp
			request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
			request.setAttribute("errorcode", "EXC");

			ErrorResponse err = new ErrorResponse("EXC", ex.getMessage());

			ObjectMapper mapper = new ObjectMapper();

			// Convert it as Strng to return

			String objectToReturn = mapper.writeValueAsString(err);

			// It returns the json with data or error

			HttpServletResponse res = (HttpServletResponse) response;
			res.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(objectToReturn);
			out.flush();
		}
	}


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// DO NOTHING
	}

}
