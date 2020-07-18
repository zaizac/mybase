/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.util.MessageService;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.model.Token;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.UidGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Component
public class LogoutHandler extends SimpleUrlLogoutSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutHandler.class);

	@Autowired
	protected ServletContext context;

	@Autowired
	private IdmServiceClient idmService;

	@Autowired
	protected MessageService messageService;


	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LOGGER.info("Forcing user to logout? {}", request.getAttribute("FORCE_LOGOUT"));
		boolean forceLogout = false;
		String errorCode = null;

		if (authentication != null) {
			String authToken = null;
			if (authentication.getPrincipal() instanceof CustomUserDetails) {
				CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
				Token token = cud.getAuthToken();
				if (token != null) {
					LOGGER.debug("authToken: {}", token);
					authToken = token.getAccessToken();
				}
				forceLogout = cud.getForceLogout();
				errorCode = cud.getErrorCode();
			}
			LOGGER.info("{} logged out at {}", authentication.getName(), DateUtil.getCurrentDate());
			if (!forceLogout) {
				// staticData.addUserAction(AuditActionPolicy.LOGOUT,
				// authentication.getName(), null);
				try {
					getIdmService(authToken).logout();
				} catch (Exception e) {
					LOGGER.error("Exception: {}", e.getMessage());
				}
			}
		}

		setAlwaysUseDefaultTargetUrl(true);
		if (forceLogout || BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.E503IDM000.name(), errorCode)) {
			// staticData.addUserAction(AuditActionPolicy.FORCE_LOGOUT,
			// authentication.getName(), null);
			setDefaultTargetUrl(PageConstants.PAGE_LOGIN + "?error=" + errorCode);
			response.sendRedirect(context.getContextPath() + getDefaultTargetUrl());
		} else {
			setDefaultTargetUrl(PageConstants.PAGE_LOGIN);
			request.getSession(false);
			// session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", null);
			response.sendRedirect(context.getContextPath() + getDefaultTargetUrl());
		}

		super.onLogoutSuccess(request, response, authentication);
	}


	public IdmServiceClient getIdmService(String authToken) {
		if (authToken != null) {
			idmService.setToken(authToken);
		} else {
			idmService.setToken(messageService.getMessage(ConfigConstants.SVC_IDM_SKEY));
		}

		idmService.setClientId(messageService.getMessage(ConfigConstants.SVC_IDM_CLIENT));
		idmService.setMessageId(UidGenerator.getMessageId());
		return idmService;
	}

}