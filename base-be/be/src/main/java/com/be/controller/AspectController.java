/**
 * Copyright 2019
 */
package com.be.controller;


import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Locale;
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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.be.constants.ConfigConstants;
import com.be.model.BeAuditTrail;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.service.BeAuditTrailService;
import com.be.util.WebUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
@Aspect
@Component
public class AspectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	public static final String PERMISSION_CODE = "aspectPermCode";

	private static final String ASPECT_LOG = "aspectLog";

	private static final String ASPECT_WATCH = "aspectWatch";

	private static final String BIOREG_AUDIT_TRAIL = "bioRegBeAuditTrail";

	@Autowired
	IdmServiceClient idmService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	BeAuditTrailService bioRegBeAuditTrailSvc;


	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE_CONTROLLER + ".*.*(..))")
	public void controllerMethods() {
		// DO NOTHING
	}


	@Around("controllerMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		long start = System.currentTimeMillis();
		request.setAttribute(ASPECT_WATCH, start);

		String trxnNo = null;

		Object[] obj = pjp.getArgs();
		if (!BaseUtil.isObjNull(obj) && obj.length > 0) {
			trxnNo = BaseUtil.getStr(obj[0]);
		}

		String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		MDC.put("TraceId", messageId);

		if (auth == null) {
			throw new BeException(BeErrorCodeEnum.E400C001);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();

		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

		BeAuditTrail auditTrail = new BeAuditTrail();
		auditTrail.setRequest(reqBody);
		auditTrail.setMessageId(messageId);
		auditTrail.setTraceId(messageId);
		auditTrail.setSessionId(request.getSession().getId());
		auditTrail.setClientId(clientId);
		auditTrail.setTrxnUrl(request.getRequestURI());
		auditTrail.setBuildVersion(messageSource.getMessage("build.version", null, Locale.getDefault()));
		request.setAttribute(BIOREG_AUDIT_TRAIL, auditTrail);

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(request.getMethod()).append(" ").append(request.getRequestURL());
		if (!BaseUtil.isObjNull(request.getQueryString())) {
			sbUrl.append('?').append(request.getQueryString());
		}

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "REQUEST received for " + request.getMethod() + " '"
				+ request.getRequestURI() + "'");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE
				+ BaseConstants.NEW_LINE);
		sb.append("Client IP: " + getClientIp(request) + BaseConstants.NEW_LINE);
		sb.append("User Agent: " + getClientAgent(request) + BaseConstants.NEW_LINE);
		sb.append("Request Parameters : " + (reqParams != null ? reqParams : "") + BaseConstants.NEW_LINE);
		sb.append("Request Body : " + reqBody + BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
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
		} finally {
			request.setAttribute(ASPECT_LOG, sb);
		}

		Object output = pjp.proceed();
		if (!BaseUtil.isEqualsCaseIgnore("GET", request.getMethod())) {
			auditTrail.setResponse(BaseConstants.SUCCESS);
			saveAuditTrail(auditTrail);
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
		long stopwatch = (long) request.getAttribute(ASPECT_WATCH);
		if (BaseUtil.isObjNull(stopwatch)) {
			stopwatch = 0;
		}
		BeAuditTrail auditTrail = (BeAuditTrail) request.getAttribute(BIOREG_AUDIT_TRAIL);
		if (!BaseUtil.isObjNull(auditTrail)
				&& !BaseUtil.isEqualsCaseIgnore(HttpMethod.GET.name(), request.getMethod())) {
			auditTrail.setTrxnNo(request.getAttribute(PERMISSION_CODE) != null
					? String.valueOf(request.getAttribute(PERMISSION_CODE))
					: "");
			auditTrail.setResponse(error.getMessage());
			saveAuditTrail(auditTrail);
		}
		log(sb, stopwatch, error);
	}


	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "RESPONSE "
				+ (output instanceof Throwable ? BaseConstants.FAILED : BaseConstants.SUCCESS));
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


	private void saveAuditTrail(BeAuditTrail s) {
		try {
			bioRegBeAuditTrailSvc.create(s);
		} catch (Exception e1) {
			// DO NOTHING
		}
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