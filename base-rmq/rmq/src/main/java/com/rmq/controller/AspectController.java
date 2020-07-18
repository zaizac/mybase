/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.controller;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHeaders;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.rmq.constants.ConfigConstants;
import com.rmq.constants.SgwTxnCodeConstants;
import com.rmq.model.RmqAuditTrail;
import com.rmq.model.RmqAuthorization;
import com.rmq.sdk.client.constants.SgwErrorCodeEnum;
import com.rmq.sdk.exception.SgwException;
import com.rmq.service.RmqAuditTrailService;
import com.rmq.service.RmqAuthorizationService;
import com.rmq.util.WebUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Aspect
@Component
public class AspectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	private static final String ASPECT_LOG = "aspectLog";

	private static final String ASPECT_WATCH = "aspectWatch";

	@Autowired
	IdmServiceClient idmService;

	@Autowired
	RmqAuthorizationService authSvc;

	@Autowired
	RmqAuditTrailService auditTrailSvc;


	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE_CONTROLLER + ".*.*(..))")
	public void controllerMethods() {
		// Do nothing
	}


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

		String messageId = request.getHeader(SgwTxnCodeConstants.HEADER_MESSAGE_ID);
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		MDC.put("TraceId", messageId);

		if (auth == null) {
			throw new SgwException(SgwErrorCodeEnum.E400SGW001);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

		request.setAttribute("sgwClientId", clientId);
		request.setAttribute("sgwSecKey", authCredentials.getSecKey());

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(request.getMethod()).append(" ").append(request.getRequestURL());
		if (!BaseUtil.isObjNull(request.getQueryString())) {
			sbUrl.append('?').append(request.getQueryString());
		}
		RmqAuditTrail s = new RmqAuditTrail();
		s.setClientId(clientId);
		s.setTrxnNo(trxnNo);
		s.setTrxnUrl(sbUrl.toString());
		s.setRequest(reqBody);

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
				throw new Exception();
			}

			if (token.getUser() != null) {
				sb.append(BaseConstants.NEW_LINE + "Client Name: " + token.getUser().getFullName());
				request.setAttribute("currUserId", token.getUser().getUserId());
				request.setAttribute("currUserFname", token.getUser().getFullName());
				request.setAttribute("currUser", token.getUser());
			}
		} catch (Exception e) {
			request.setAttribute(ASPECT_LOG, sb);
			request.setAttribute(ASPECT_WATCH, start);
			throw e;
		}

		// CHECK AUTHORIZATION
		RmqAuthorization sgwAuth = null;
//		try {
//			sgwAuth = authSvc.findByClientIdAndTxnNo(clientId, trxnNo);
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage());
//		}

		request.setAttribute(ASPECT_LOG, sb);
		request.setAttribute(ASPECT_WATCH, start);
		request.setAttribute("sgwAuditTrail", s);

//		if (BaseUtil.isObjNull(sgwAuth)
//				|| (!BaseUtil.isObjNull(sgwAuth) && BaseUtil.isEqualsCaseIgnore("I", sgwAuth.getStatus()))) {
//			request.setAttribute(ASPECT_LOG, sb);
//			request.setAttribute(ASPECT_WATCH, start);
//			throw new SgwException(SgwErrorCodeEnum.E401SGW001);
//		}

		Object output = pjp.proceed();
		log(sb, start, output);
		if (!BaseUtil.isEqualsCaseIgnore("GET", request.getMethod())) {
//			saveAuditTrail(s);
		}
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
		log(sb, stopwatch, error);
		RmqAuditTrail s = (RmqAuditTrail) request.getAttribute("sgwAuditTrail");
		if (!BaseUtil.isObjNull(s)) {
			s.setResponse(error.getMessage());
//			saveAuditTrail(s);
		}
	}


	@Async
	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);
		sb.append(BaseConstants.NEW_LINE + "RESPONSE " + (output instanceof Throwable ? "FAILED" : "SUCCESS"));
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);

		if (output instanceof Throwable) {
//			JsonNode jn = new ObjectMapper().valueToTree((Throwable) output);
//			if (jn.get("internalErrorCode") != null) {
//				sb.append(BaseConstants.NEW_LINE + "Error Code: " + jn.get("internalErrorCode").asText());
//			}
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

	@Async
	private void saveAuditTrail(RmqAuditTrail s) {
		try {
			auditTrailSvc.create(s);
		} catch (Exception e) {
			LOGGER.info("Exception: {}", e.getMessage());
		}
	}

}