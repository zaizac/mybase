/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.exception;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.notify.sdk.model.ErrorResponse;
import com.baseboot.notify.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ErrorHandlerFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception ex) {
			// call ErrorHandler and dispatch to error jsp
			// throw new GeneralException(ex.getMessage());
			request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
			request.setAttribute("errorcode", "EXC");
			// request.getRequestDispatcher("/error").forward(request,
			// response);

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


	public void destroy() {
		// DO NOTHING
	}

}
