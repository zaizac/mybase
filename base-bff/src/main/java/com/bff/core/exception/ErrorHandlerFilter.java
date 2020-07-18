/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.core.exception;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.idm.sdk.constants.IdmErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
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
			if (ex.getMessage().contains(IdmErrorCodeEnum.I404IDM113.name())
					|| ex.getMessage().contains(IdmErrorCodeEnum.I404IDM115.name())) {
				request.getRequestDispatcher(PageConstants.PAGE_LOGOUT).forward(request, response);
			} else {
				// call ErrorHandler and dispatch to error page
				request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
				request.setAttribute("errorcode", "EXC");
				// request.getRequestDispatcher(PageConstants.PAGE_ERROR).forward(request,
				// response);
			}
		}
	}


	@Override
	public void destroy() {
		// DO NOTHING
	}

}