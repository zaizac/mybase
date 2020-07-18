/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.helper.WebHelper;
import com.baseboot.web.util.MessageService;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.DateUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccessHandler.class);

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
					/*
					 * if (StringUtils.equalsIgnoreCase(BaseConstants.
					 * USER_FIRST_TIME, userProfile.getStatus())) {
					 * response.sendRedirect(request.getContextPath() +
					 * PageConstants.PAGE_IDM_USR_CHNG_PWORD); } else {
					 */
					String roleRedirectPg = WebHelper.redirectPageByRole(userProfile.getRoles());
					if (!BaseUtil.isObjNull(roleRedirectPg)) {
						response.sendRedirect(request.getContextPath() + roleRedirectPg);
					} else {
						response.sendRedirect(request.getContextPath() + PageConstants.PAGE_HOME);
					}
					// }
				}
			}

			LOGGER.info("\n\tUser: " + authentication.getName() + " logged in at " + DateUtil.getCurrentDate()
					+ "\n\tRole: " + authentication.getAuthorities().toString());

		} else {
			response.sendRedirect(request.getContextPath() + PageConstants.PAGE_DASHBOARD);
		}

	}

}