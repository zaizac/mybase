/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			ServletRequest req = (ServletRequest) request;
			ServletResponse resp = (ServletResponse) response;
			FilterInvocation filterInvocation = new FilterInvocation(req, resp, new FilterChain() {

				public void doFilter(ServletRequest request, ServletResponse response)
						throws IOException, ServletException {
					throw new UnsupportedOperationException();
				}
			});

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				WebSecurityExpressionRoot sec = new WebSecurityExpressionRoot(authentication, filterInvocation);
				sec.setTrustResolver(new AuthenticationTrustResolverImpl());
				modelAndView.getModel().put("sec", sec);
			}

			try {
				Cookie cookies[] = filterInvocation.getRequest().getCookies();
				if (!BaseUtil.isObjNull(cookies)) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equalsIgnoreCase("JSESSIONID")) {
							response.setHeader("Set-Cookie", cookies[i].getName() + "=" + cookies[i].getValue()
									+ ";Path=" + request.getContextPath() + "; Secure; HttpOnly;");
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Set-Cookie Error: {}", e.getMessage());
			}
		}

	}

}