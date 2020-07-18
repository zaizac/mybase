package com.idm.exception;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.util.WebUtil;
import com.util.MediaType;
import com.util.model.ErrorResponse;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ErrorHandlerFilter implements Filter {

	@Autowired
	private ObjectMapper objectMapper;


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
			// call ErrorHandler and dispatch to error jsp
			request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
			request.setAttribute("errorcode", "EXC");

			ErrorResponse err = new ErrorResponse("EXC", ex.getMessage());

			String objectToReturn = objectMapper.writeValueAsString(err);

			HttpServletResponse res = (HttpServletResponse) response;
			res.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON);
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