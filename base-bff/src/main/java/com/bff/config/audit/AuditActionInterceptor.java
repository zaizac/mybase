/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config.audit;


import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bff.cmn.form.StaticData;
import com.bff.core.MessageService;
import com.idm.sdk.client.IdmServiceClient;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.UidGenerator;
import com.util.constants.BaseConfigConstants;


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

	@Autowired
	StaticData staticData;


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
			assignAuditActionControlHeader(request, handler);
		}
	}


	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			ModelAndView mav) throws Exception {
		if (handler instanceof HandlerMethod) {
			assignAuditActionControlHeader(request, handler);
		}
	}


	protected final AuditActionControl assignAuditActionControlHeader(final HttpServletRequest request,
			final Object handler) {

		if (!(handler instanceof HandlerMethod)) {
			return null;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		AuditActionControl auditActionControl = handlerMethod.getMethodAnnotation(AuditActionControl.class);
		if (BaseUtil.isObjNull(auditActionControl)) {
			return handlerMethod.getBeanType().getAnnotation(AuditActionControl.class);
		}

		try {
			String reqMsg = JsonUtil.requestParamsToJSON(request.getParameterMap());
			// TODO: FIX IT
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
//				CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
//				Token token = cud.getAuthToken();
//				if (token != null) {
//					staticData.addUserAction(auditActionControl.action(), cud.getUsername(), reqMsg, request);
//				}
//			}

		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}

		return auditActionControl;
	}


	public IdmServiceClient getIdmService(String authToken) {
		if (authToken != null) {
			idmService.setToken(authToken);
		} else {
			idmService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
		}

		idmService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
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