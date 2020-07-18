/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.thymeleaf.util.StringUtils;

import com.dialect.MessageService;
import com.dialect.iam.CustomUserDetails;
import com.idm.sdk.model.UserProfile;
import com.portal.constants.PageConstants;
import com.portal.web.util.helper.WebHelper;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Autowired
	protected MessageService messageService;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		if (authentication != null) {
			UserProfile userProfile = null;
			if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
				CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
				userProfile = cud.getProfile();
				if (!BaseUtil.isObjNull(userProfile)) {
					if (StringUtils.equalsIgnoreCase(BaseConstants.USER_FIRST_TIME, userProfile.getStatus())) {
						response.sendRedirect(request.getContextPath() + PageConstants.PAGE_IDM_USR_CHNG_PWORD);
					} else {
						response.sendRedirect(request.getContextPath() + PageConstants.PAGE_HOME);
					}
				}
			}

			LOG.info("\n\tUser: {} logged in at {} \n\tRole: {}", authentication.getName(),
					DateUtil.getCurrentDate(), authentication.getAuthorities());

		} else {
			response.sendRedirect(request.getContextPath() + PageConstants.PAGE_HOME);
		}

	}

}