/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config.audit;


import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baseboot.web.config.ConfigConstants;
import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.util.JsonUtil;
import com.baseboot.web.util.MessageService;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.util.BaseUtil;
import com.util.UidGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class AuditActionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditActionInterceptor.class);

	@Autowired
	IdmServiceClient idmService;

	@Autowired
	MessageService messageService;


	public AuditActionInterceptor() {
		super();
	}


	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler) throws Exception {
		return true;
	}


	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, Exception exception) throws Exception {
		if (handler instanceof HandlerMethod) {
			assignAuditActionControlHeader(request, response, handler);
		}
	}


	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			ModelAndView mav) throws Exception {
		if (handler instanceof HandlerMethod) {
			assignAuditActionControlHeader(request, response, handler);
		}
	}


	protected final AuditActionControl assignAuditActionControlHeader(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler) {

		if (handler == null || !(handler instanceof HandlerMethod)) {
			return null;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		AuditActionControl auditActionControl = handlerMethod.getMethodAnnotation(AuditActionControl.class);
		if (BaseUtil.isObjNull(auditActionControl)) {
			return handlerMethod.getBeanType().getAnnotation(AuditActionControl.class);
		}

		try {
			JsonUtil.requestParamsToJSON(request.getParameterMap());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.isAuthenticated()) {
				if (auth.getPrincipal() instanceof CustomUserDetails) {
					CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
					Token token = cud.getAuthToken();
					if (token != null) {
						// staticData.addUserAction(auditActionControl.action(),
						// cud.getUsername(), reqMsg);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}

		return auditActionControl;
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


	AuditActionControl defaultAuditActionControl() {
		return new AuditActionControl() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}


			@Override
			public AuditActionPolicy action() {
				return null;
			}
		};
	}

}