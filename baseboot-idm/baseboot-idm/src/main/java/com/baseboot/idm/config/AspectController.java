/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.config;


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
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.Oauth2Constants;
import com.baseboot.idm.model.IdmAuditTrail;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.util.AuthCredentials;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.service.IdmAuditTrailService;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.service.MessageService;
import com.baseboot.idm.util.WebUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Aspect
@Configuration
public class AspectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	private final String newline = System.lineSeparator();

	private final String lineSeparator = "---------------------------------------------------------------------------------------";

	@Autowired
	MessageService messageService;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	IdmProfileService idmUserProfileService;

	@Autowired
	ClientDetailsService clientDetailsService;

	@Autowired
	IdmAuditTrailService idmAuditTrailSvc;


	@Pointcut("execution(* " + ConfigConstants.BASE_PACKAGE_CONTROLLER + ".*.*(..))")
	public void controllerMethods() {
		// Do Nothing
	}


	@Around("controllerMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		long start = System.currentTimeMillis();
		LOGGER.info("Start AspectController..");
		request.setAttribute("aspectWatch", start);

		boolean skipAuth = false;
		if (request.getRequestURI().contains(IdmUrlConstrants.SERVICE_CHECK)
				|| request.getRequestURI().contains(IdmUrlConstrants.ENCRYPT)
				|| request.getRequestURI().contains(IdmUrlConstrants.DECRYPT)) {
			skipAuth = true;
		}

		String trxnNo = null;

		Object[] obj = pjp.getArgs();
		if (!BaseUtil.isObjNull(obj) && obj.length > 0) {
			trxnNo = BaseUtil.getStr(obj[0]);
		}

		String messageId = request.getHeader(IdmTxnCodeConstants.HEADER_MESSAGE_ID);
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		MDC.put("TraceId", messageId);

		if (auth == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM912);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		String clientId = authCredentials.getClient();
		String secKey = authCredentials.getSecKey();
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		String reqParams = request.getQueryString();
		String reqBody = WebUtil.getRequestBody(request.getInputStream());

		IdmAuditTrail s = new IdmAuditTrail();
		s.setRequest(reqBody);
		s.setTraceId(messageId);
		s.setSessionId(request.getAttribute(IdmTxnCodeConstants.TXN_REF_NO) != null
				? String.valueOf(request.getAttribute(IdmTxnCodeConstants.TXN_REF_NO))
				: "");

		StringBuilder sb = new StringBuilder();
		sb.append(newline + newline + lineSeparator);
		sb.append(newline + "IDM Request received for " + request.getMethod() + " '" + request.getRequestURI() + "'");
		sb.append(newline + lineSeparator + newline + newline);
		sb.append("Client IP: " + getClientIp(request) + newline);
		sb.append("User Agent: " + getClientAgent(request) + newline);
		sb.append("Authorization : " + auth + newline);
		sb.append("X-Message-Id : " + messageId + newline + newline);
		sb.append("Request Parameters : " + (reqParams != null ? reqParams : "") + newline);
		sb.append("Request Body : " + reqBody + newline + newline);
		sb.append("Client Id : " + clientId + newline);
		sb.append("Secret Key : " + authCredentials.getSecKey() + newline + newline);
		sb.append("Service Class : " + className + "." + methodName + newline);
		sb.append("Service Trxn No : " + (trxnNo != null ? trxnNo : ""));

		String token = null;
		OAuth2AccessToken accessToken = null;

		Object output = null;
		request.setAttribute("messageId", messageId);
		request.setAttribute("aspectLog", sb);

		ClientDetails client = null;

		try {
			client = clientDetailsService.loadClientByClientId(clientId);
		} catch (NoSuchClientException e1) {
			throw new IdmException(IdmErrorCodeEnum.E500IDM904, new String[] { clientId });
		}

		if (secKey.equals(client.getClientSecret())) {
			request.setAttribute("clientId", clientId);
			s.setClientId(clientId);
			skipAuth = true;
		} else if (!skipAuth) {
			try {
				token = AuthCredentials.createCredentialsFromHeader(auth, Oauth2Constants.TOKEN_TYPE_BASIC)
						.getToken();
				accessToken = tokenStore.readAccessToken(token);
			} catch (Exception e) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM115, new String[] { token });
			}

			if (accessToken == null) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM115, new String[] { token });
			}

			if (accessToken.isExpired()) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM111, new String[] { token });
			}

		}

		if (!skipAuth) {
			OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
			IdmProfile user = idmUserProfileService.findProfileByUserId(oAuth2Authentication.getName());

			if (user == null) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM102,
						new String[] { oAuth2Authentication.getName() });
			}

			if (!BaseUtil.isObjNull(user)) {
				sb.append(newline + "Client Name: " + user.getFullName());
				request.setAttribute("currUserId", user.getUserId());
				request.setAttribute("currUserFname", user.getFullName());
				request.setAttribute("currUser", user);
				s.setClientId(user.getUserId());
			}
		}

		request.setAttribute("sgwAuditTrail", s);
		output = pjp.proceed();
		log(sb, start, output);

		saveAuditTrail(s);
		return output;
	}


	@AfterThrowing(value = "controllerMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		StringBuilder sb = new StringBuilder();
		sb.append(request.getAttribute("aspectLog"));
		long stopwatch = (long) request.getAttribute("aspectWatch");
		if (BaseUtil.isObjNull(stopwatch)) {
			stopwatch = 0;
		}
		error.printStackTrace();
		log(sb, stopwatch, error);
		IdmAuditTrail s = (IdmAuditTrail) request.getAttribute("sgwAuditTrail");
		String reqMethod = request.getMethod();
		if (!BaseUtil.isObjNull(s) && (HttpMethod.POST.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.PUT.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.DELETE.name().equalsIgnoreCase(reqMethod))) {
			s.setSessionId(request.getAttribute(IdmTxnCodeConstants.PERMISSION_CODE) != null
					? String.valueOf(request.getAttribute(IdmTxnCodeConstants.PERMISSION_CODE))
					: "");
			saveAuditTrail(s);
		}

	}


	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(newline + newline + lineSeparator);
		sb.append(newline + "RESPONSE " + (output instanceof Throwable ? "FAILED" : "SUCCESS"));
		sb.append(newline + lineSeparator);

		if (output instanceof Throwable) {
			JsonNode jn = new ObjectMapper().valueToTree((Throwable) output);
			if (jn.get("internalErrorCode") != null) {
				sb.append(newline + "Error Code: " + jn.get("internalErrorCode").asText());
			}
			sb.append(newline + "Error Exception: " + newline + WebUtil.getStackTrace((Throwable) output));
		} else {
			sb.append(newline + "Response: " + newline + new ObjectMapper().valueToTree(output));
		}

		sb.append(newline + newline);
		sb.append("Process completed within " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " s / " + elapsedTime
				+ " ms");
		sb.append(newline + "Finished at: " + new Date());
		sb.append(newline + lineSeparator + newline + newline);
		LOGGER.info("{}", sb.toString());

	}


	private void saveAuditTrail(IdmAuditTrail s) {
		try {
			idmAuditTrailSvc.create(s);
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