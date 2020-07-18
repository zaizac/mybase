/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.thymeleaf.util.StringUtils;

import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.constants.BaseConstants;
import com.wfw.config.iam.CustomUserDetails;
import com.wfw.constant.PageConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		if (authentication != null) {
			if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
				CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
				if (!BaseUtil.isObjNull(cud)) {
					if (StringUtils.equalsIgnoreCase(BaseConstants.USER_FIRST_TIME, cud.getIsActive())) {
						response.sendRedirect(request.getContextPath() + PageConstants.PAGE_IDM_USR_CHNG_PWORD);
					} else {
						response.sendRedirect(request.getContextPath() + PageConstants.PAGE_URL_HOME);
					}
				}
			}

			LOG.info("\n\tUser: {} logged in at {} \n\tRole: {}", authentication.getName(),
					DateUtil.getCurrentDate(), authentication.getAuthorities());

		} else {
			response.sendRedirect(request.getContextPath() + PageConstants.PAGE_URL_HOME);
		}

	}

}