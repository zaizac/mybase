/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.exception;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.MediaType;

import org.springframework.http.MediaType;

import com.baseboot.report.sdk.model.ErrorResponse;
import com.baseboot.report.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ErrorHandlerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception ex) {
			request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
			request.setAttribute("errorcode", "EXC");
			ErrorResponse err = new ErrorResponse("EXC", ex.getMessage());
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString(err);

			HttpServletResponse res = (HttpServletResponse) response;
			res.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(objectToReturn);
			out.flush();
		}
	}


	@Override
	public void destroy() {
		// DO NOTHING
	}

}
