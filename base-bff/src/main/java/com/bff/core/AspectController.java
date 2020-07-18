/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.core;


import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bff.util.WebUtil;
import com.bff.util.constants.ConfigConstants;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Aspect
@Component
public class AspectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	private static final String ASPECT_LOG = "aspectLog";

	private static final String ASPECT_WATCH = "aspectWatch";

	@Autowired
	IdmServiceClient idmService;


	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE + ".*.controller.*.*(..))")
	public void controllerMethods() {
		// default implementation ignored
	}


	@Around("controllerMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		Object output = pjp.proceed();

		// Skip authorization for the following URL
		if (request.getRequestURI().contains(PageConstants.DOCUMENTS_DOWNLOAD)
				|| request.getRequestURI().contains(PageConstants.LOGS)
				|| request.getRequestURI().contains(PageConstants.PAGE_LOGOUT)) {
			return output;
		}

		long start = System.currentTimeMillis();
		request.setAttribute(ASPECT_WATCH, start);

		String trxnNo = null;

		Object[] obj = pjp.getArgs();
		if (!BaseUtil.isObjNull(obj) && obj.length > 0) {
			trxnNo = BaseUtil.getStr(obj[0]);
		}

		String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
		if (BaseUtil.isObjNull(messageId)) {
			messageId = request.getHeader("x-message-id");
		}

		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (BaseUtil.isObjNull(auth)) {
			auth = request.getHeader("authorization");
		}

		LOGGER.info("AUTHORIZATION: {}", auth);

		MDC.put("TraceId", messageId);

		if (auth == null) {
			// TODO: throw new BffException(BffErrorCodeEnum.E400RPT001);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

		request.setAttribute("clientId", clientId);

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "REQUEST received for " + request.getMethod() + " '"
				+ request.getRequestURI() + "'");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE
				+ BaseConstants.NEW_LINE);
		sb.append("Client IP: " + getClientIp(request) + BaseConstants.NEW_LINE);
		sb.append("User Agent: " + getClientAgent(request) + BaseConstants.NEW_LINE);
		sb.append("Authorization : " + auth + BaseConstants.NEW_LINE);
		sb.append("X-Message-Id : " + messageId + BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append("Request Parameters : " + (reqParams != null ? reqParams : "") + BaseConstants.NEW_LINE);
		sb.append("Request Body : " + reqBody + BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append("Client Id : " + clientId + BaseConstants.NEW_LINE);
		sb.append("Secret Key : " + authCredentials.getSecKey() + BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append("Service Class : " + className + "." + methodName + BaseConstants.NEW_LINE);
		sb.append("Service Trxn No : " + (trxnNo != null ? trxnNo : ""));

		LOGGER.debug("{}", sb);

		// CHECK AUTHENTICATION
		try {
			idmService.setAuthToken(auth);
			idmService.setMessageId(messageId);
			Token token = idmService.validateAccessToken(true);
			if (token == null) {
				throw new GeneralSecurityException("Token is null.");
			}

			if (token.getUser() != null) {
				request.setAttribute("currUserId", token.getUser().getUserId());
				request.setAttribute("currUserFname", token.getUser().getFirstName());
				request.setAttribute("currUser", token.getUser());
			}

		} finally {
			request.setAttribute(ASPECT_LOG, sb);
		}

		log(sb, start, output);
		return output;
	}


	@AfterThrowing(value = "controllerMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		StringBuilder sb = new StringBuilder();
		sb.append(request.getAttribute(ASPECT_LOG));
		Object aopWatch = request.getAttribute(ASPECT_WATCH);
		long stopwatch = 0;
		if (!BaseUtil.isObjNull(aopWatch)) {
			stopwatch = (long) request.getAttribute(ASPECT_WATCH);
		}
		log(sb, stopwatch, error);
	}


	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "RESPONSE " + (output instanceof Throwable ? "FAILED" : "SUCCESS"));
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);

		if (output instanceof Throwable) {
			JsonNode jn = JsonUtil.objectMapper().valueToTree((Throwable) output);
			if (jn.get("internalErrorCode") != null) {
				sb.append(BaseConstants.NEW_LINE + "Error Code: " + jn.get("internalErrorCode").asText());
			}
			sb.append(BaseConstants.NEW_LINE + "Error Exception: " + BaseConstants.NEW_LINE
					+ WebUtil.getStackTrace((Throwable) output));
		} else {
//			sb.append(BaseConstants.NEW_LINE + "Response: " + BaseConstants.NEW_LINE
//					+ JsonUtil.objectMapper().valueToTree(output));
		}

		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append("Process completed within " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " s / " + elapsedTime
				+ " ms");
		sb.append(BaseConstants.NEW_LINE + "Finished at: " + new Date());
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE
				+ BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}


	private static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}


	private static String getClientAgent(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("USER-AGENT");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

}