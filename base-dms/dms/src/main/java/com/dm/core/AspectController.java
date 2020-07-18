/**
 * Copyright 2019. 
 */
package com.dm.core;


import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dm.constants.ConfigConstants;
import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.dm.util.WebUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * The Class AspectController.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Aspect
@Component
public class AspectController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	/** The Constant HEADER_MESSAGE_ID. */
	public static final String HEADER_MESSAGE_ID = "X-Message-Id";

	/** The Constant HEADER_AUTHORIZATION. */
	public static final String HEADER_AUTHORIZATION = "Authorization";

	/** The Constant ASPECT_LOG_TEXT. */
	private static final String ASPECT_LOG_TEXT = "aspectLog";

	/** The Constant ASPECT_WATCH_TEXT. */
	private static final String ASPECT_WATCH_TEXT = "aspectWatch";

	/** The idm service. */
	@Autowired
	IdmServiceClient idmService;


	/**
	 * Controller methods.
	 */
	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE_CONTROLLER + ".*.*(..))")
	public void controllerMethods() {
		// Initialize controller
	}


	/**
	 * Profile.
	 *
	 * @param pjp the pjp
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("controllerMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		long start = System.currentTimeMillis();

		String trxnNo = null;

		Object[] obj = pjp.getArgs();
		if (!BaseUtil.isObjNull(obj) && obj.length > 0) {
			trxnNo = BaseUtil.getStr(obj[0]);
		}

		String messageId = request.getHeader(HEADER_MESSAGE_ID);
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		MDC.put("TraceId", messageId);

		if (auth == null) {
			throw new DmException(DmErrorCodeEnum.E400DOM911);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

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

		// CHECK AUTHENTICATION
		try {
			idmService.setAuthToken(auth);
			idmService.setMessageId(messageId);
			Token token = idmService.validateAccessToken(true);
			if (token == null) {
				throw new GeneralSecurityException("Token is null.");
			}

			if (token.getUser() != null) {
				sb.append(BaseConstants.NEW_LINE + "Client Name: " + token.getUser().getFullName());
				request.setAttribute("currUserId", token.getUser().getUserId());
				request.setAttribute("currUserFname", token.getUser().getFullName());
				request.setAttribute("currUser", token.getUser());
			}
		} catch (Exception e) {
			request.setAttribute(ASPECT_LOG_TEXT, sb);
			request.setAttribute(ASPECT_WATCH_TEXT, start);
			throw e;
		}

		request.setAttribute(ASPECT_LOG_TEXT, sb);
		request.setAttribute(ASPECT_WATCH_TEXT, start);

		Object output = pjp.proceed();
		log(sb, start, output);
		return output;
	}


	/**
	 * Log after throwing.
	 *
	 * @param joinPoint the join point
	 * @param error the error
	 */
	@AfterThrowing(value = "controllerMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		StringBuilder sb = new StringBuilder();
		sb.append(request.getAttribute(ASPECT_LOG_TEXT));
		long stopwatch = (long) request.getAttribute(ASPECT_WATCH_TEXT);
		if (BaseUtil.isObjNull(stopwatch)) {
			stopwatch = 0;
		}
		log(sb, stopwatch, error);
	}


	/**
	 * Log.
	 *
	 * @param sb the sb
	 * @param stopwatch the stopwatch
	 * @param output the output
	 */
	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "RESPONSE " + (output instanceof Throwable ? "FAILED" : "SUCCESS"));
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);

		if (output instanceof Throwable) {
			JsonNode jn = new ObjectMapper().valueToTree((Throwable) output);
			if (jn.get("internalErrorCode") != null) {
				sb.append(BaseConstants.NEW_LINE + "Error Code: " + jn.get("internalErrorCode").asText());
			}
			sb.append(BaseConstants.NEW_LINE + "Error Exception: " + BaseConstants.NEW_LINE
					+ WebUtil.getStackTrace((Throwable) output));
		} else {
			sb.append(BaseConstants.NEW_LINE + "Response: " + BaseConstants.NEW_LINE
					+ new ObjectMapper().valueToTree(output));
		}

		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append("Process completed within " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " s / " + elapsedTime
				+ " ms");
		sb.append(BaseConstants.NEW_LINE + "Finished at: " + new Date());
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE
				+ BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}


	/**
	 * Gets the client ip.
	 *
	 * @param request the request
	 * @return the client ip
	 */
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


	/**
	 * Gets the client agent.
	 *
	 * @param request the request
	 * @return the client agent
	 */
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