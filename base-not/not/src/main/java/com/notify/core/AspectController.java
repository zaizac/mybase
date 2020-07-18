/**
 * Copyright 2019. 
 */
package com.notify.core;


import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Locale;
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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.model.Token;
import com.notify.constants.ConfigConstants;
import com.notify.model.NotAuditTrail;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.exception.NotificationException;
import com.notify.service.NotAuditTrailService;
import com.notify.util.WebUtil;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;



/**
 * The Class AspectController.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Aspect
@Component
public class AspectController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	/** The Constant PERMISSION_CODE. */
	public static final String PERMISSION_CODE = "eqmTxnNo";

	/** The Constant TXN_REF_NO. */
	public static final String TXN_REF_NO = "eqmTxnRefNo";

	/** The Constant ASPECT_LOG. */
	private static final String ASPECT_LOG = "aspectLog";

	/** The Constant ASPECT_WATCH. */
	private static final String ASPECT_WATCH = "aspectWatch";

	/** The Constant NOT_AUDIT_TRAIL. */
	private static final String NOT_AUDIT_TRAIL = "notAuditTrail";

	/** The idm service. */
	@Autowired
	IdmServiceClient idmService;

	/** The message source. */
	@Autowired
	MessageSource messageSource;

	/** The not audit trail svc. */
	@Autowired
	NotAuditTrailService notAuditTrailSvc;


	/**
	 * Controller methods.
	 */
	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE_CONTROLLER + ".*.*(..))")
	public void controllerMethods() {
		// DO NOTHING
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

		String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		MDC.put("TraceId", messageId);

		if (auth == null) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT001);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

		NotAuditTrail notAuditTrail = new NotAuditTrail();
		notAuditTrail.setRequest(reqBody);
		notAuditTrail.setMessageId(messageId);
		notAuditTrail.setTraceId(messageId);
		notAuditTrail.setSessionId(request.getSession().getId());
		notAuditTrail.setClientId(clientId);
		notAuditTrail.setTrxnUrl(request.getRequestURI() + (reqParams != null ? "?".concat(reqParams) : ""));
		notAuditTrail.setBuildVersion(
				messageSource.getMessage(BaseConfigConstants.BUILD_VERSION, null, Locale.getDefault()));

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
			request.setAttribute(ASPECT_LOG, sb);
			request.setAttribute(ASPECT_WATCH, start);
			throw e;
		}

		request.setAttribute(ASPECT_LOG, sb);
		request.setAttribute(ASPECT_WATCH, start);
		request.setAttribute(NOT_AUDIT_TRAIL, notAuditTrail);

		Object output = pjp.proceed();
		log(sb, start, output);
		if (!BaseUtil.isEqualsCaseIgnore("GET", request.getMethod())) {
			notAuditTrail.setResponse(BaseConstants.SUCCESS);
			saveAuditTrail(notAuditTrail);
		}
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
		sb.append(request.getAttribute(ASPECT_LOG));
		long stopwatch = (long) request.getAttribute(ASPECT_WATCH);
		if (BaseUtil.isObjNull(stopwatch)) {
			stopwatch = 0;
		}
		log(sb, stopwatch, error);
		NotAuditTrail notAuditTrail = (NotAuditTrail) request.getAttribute(NOT_AUDIT_TRAIL);
		String reqMethod = request.getMethod();
		if (!BaseUtil.isObjNull(notAuditTrail) && (HttpMethod.POST.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.PUT.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.DELETE.name().equalsIgnoreCase(reqMethod))) {
			notAuditTrail.setTrxnNo(request.getAttribute(PERMISSION_CODE) != null
					? String.valueOf(request.getAttribute(PERMISSION_CODE))
					: "");
			notAuditTrail.setResponse(error.getMessage());
			saveAuditTrail(notAuditTrail);
		}
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
		sb.append(BaseConstants.NEW_LINE + "RESPONSE "
				+ (output instanceof Throwable ? BaseConstants.FAILED : BaseConstants.SUCCESS));
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR);

		if (output instanceof Throwable) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_DEFAULT);
			mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
			JsonNode jn = mapper.valueToTree((Throwable) output);
			if (jn.get("internalErrorCode") != null) {
				sb.append(BaseConstants.NEW_LINE + "Error Code: " + jn.get("internalErrorCode").asText());
			}
			sb.append(BaseConstants.NEW_LINE + "Error Exception: " + BaseConstants.NEW_LINE
					+ WebUtil.getStackTrace((Throwable) output));
		} else {
//			sb.append(BaseConstants.NEW_LINE + "Response: " + BaseConstants.NEW_LINE
//					+ new ObjectMapper().valueToTree(output));
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
	 * Save audit trail.
	 *
	 * @param s the s
	 */
	private void saveAuditTrail(NotAuditTrail s) {
		try {
			notAuditTrailSvc.create(s);
		} catch (Exception e1) {
			// DO NOTHING
		}
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