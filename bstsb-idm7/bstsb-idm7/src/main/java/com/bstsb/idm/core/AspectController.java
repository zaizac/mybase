/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.bstsb.idm.core;


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
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bstsb.idm.config.ConfigConstants;
import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.constants.Oauth2Constants;
import com.bstsb.idm.model.IdmAuditTrail;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.service.IdmAuditTrailService;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.MessageService;
import com.bstsb.idm.util.WebUtil;
import com.bstsb.util.AuthCredentials;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * @author Mary Jane Buenaventura
 * @since May 5, 2018
 */
@Aspect
@Component
public class AspectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

	private final String newline = System.lineSeparator();

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
		// DO NOTHING
	}


	@Around("controllerMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		long start = System.currentTimeMillis();

		boolean skipAuth = false;
		if (request.getRequestURI().contains(IdmUrlConstants.SERVICE_CHECK)
				|| request.getRequestURI().contains(IdmUrlConstants.ENCRYPT)
				|| request.getRequestURI().contains(IdmUrlConstants.DECRYPT)) {
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

		IdmAuditTrail idmAuditTrail = configureProfileAuditTrail(reqBody, messageId, request, clientId, reqParams);

		StringBuilder sb = new StringBuilder();
		sb.append(newline + newline + BaseConstants.LOG_SEPARATOR);
		sb.append(newline + "IDM Request received for " + request.getMethod() + " '" + request.getRequestURI() + "'");
		sb.append(newline + BaseConstants.LOG_SEPARATOR + newline + newline);
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

		Object output = null;
		request.setAttribute("messageId", messageId);
		request.setAttribute("aspectLog", sb);
		request.setAttribute("aspectWatch", start);
		ClientDetails client = null;

		try {
			client = clientDetailsService.loadClientByClientId(clientId);
		} catch (NoSuchClientException e1) {
			throw new IdmException(IdmErrorCodeEnum.E500IDM904, new String[] { clientId });
		}

		OAuth2AccessToken accessToken = null;
		if (secKey.equals(client.getClientSecret())) {
			request.setAttribute("clientId", clientId);
			idmAuditTrail.setCreateId(clientId);
			skipAuth = true;
		} else if (!skipAuth) {
			accessToken = profileAccessToken(auth);
		}

		if (!skipAuth) {
			readAuthenticationAndFindProfile(accessToken, sb, request, idmAuditTrail);
		}

		request.setAttribute("sgwAuditTrail", idmAuditTrail);
		output = pjp.proceed();
		log(sb, start, output);
		if (!BaseUtil.isEqualsCaseIgnore("GET", request.getMethod())) {
			idmAuditTrail.setResponse("SUCCESS");
			saveAuditTrail(idmAuditTrail);
		}
		return output;
	}


	private IdmAuditTrail configureProfileAuditTrail(String reqBody, String messageId, HttpServletRequest request,
			String clientId, String reqParams) {
		IdmAuditTrail idmAuditTrail = new IdmAuditTrail();
		idmAuditTrail.setRequest(reqBody);
		idmAuditTrail.setMessageId(messageId);
		idmAuditTrail.setTrxnNo(request.getAttribute(IdmTxnCodeConstants.TXN_REF_NO) != null
				? String.valueOf(request.getAttribute(IdmTxnCodeConstants.TXN_REF_NO))
				: "");
		idmAuditTrail.setTraceId(messageId);
		idmAuditTrail.setClientId(clientId);
		idmAuditTrail.setTrxnUrl(request.getRequestURI() + (reqParams != null ? "?".concat(reqParams) : ""));
		idmAuditTrail.setBuildVersion(messageService.getMessage("build.version"));

		return idmAuditTrail;
	}


	private void readAuthenticationAndFindProfile(OAuth2AccessToken accessToken, StringBuilder sb,
			HttpServletRequest request, IdmAuditTrail idmAuditTrail) throws IdmException {
		OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
		IdmProfile user = idmUserProfileService.findProfileByUserId(oAuth2Authentication.getName());

		if (user == null) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM102, new String[] { oAuth2Authentication.getName() });
		}

		if (!BaseUtil.isObjNull(user)) {
			sb.append(newline + "Client Name: " + user.getFullName());
			request.setAttribute("currUserId", user.getUserId());
			request.setAttribute("currUserFname", user.getFullName());
			request.setAttribute("currUser", user);
			idmAuditTrail.setCreateId(user.getUserId());
		}
	}


	private OAuth2AccessToken profileAccessToken(String auth) throws IdmException {
		String token = null;
		OAuth2AccessToken accessToken = null;
		try {
			token = AuthCredentials.createCredentialsFromHeader(auth, Oauth2Constants.TOKEN_TYPE_BASIC).getToken();
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
		return accessToken;
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
		log(sb, stopwatch, error);
		IdmAuditTrail idmAuditTrail = (IdmAuditTrail) request.getAttribute("sgwAuditTrail");
		String reqMethod = request.getMethod();
		if (!BaseUtil.isObjNull(idmAuditTrail) && (HttpMethod.POST.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.PUT.name().equalsIgnoreCase(reqMethod)
				|| HttpMethod.DELETE.name().equalsIgnoreCase(reqMethod))) {
			idmAuditTrail.setTrxnNo(request.getAttribute(IdmTxnCodeConstants.PERMISSION_CODE) != null
					? String.valueOf(request.getAttribute(IdmTxnCodeConstants.PERMISSION_CODE))
					: "");
			String errCode = getInternalErrorCode(error);
			idmAuditTrail.setResponse(error.getMessage() + (errCode != null ? "[" + errCode + "]" : ""));
			saveAuditTrail(idmAuditTrail);
		}

	}


	private String getInternalErrorCode(Throwable output) {
		String internalErrorCode = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		JsonNode jn = mapper.valueToTree(output);
		if (jn.get("internalErrorCode") != null) {
			internalErrorCode = jn.get("internalErrorCode").asText();
		}
		return internalErrorCode;
	}


	private void log(StringBuilder sb, long stopwatch, Object output) {
		long elapsedTime = System.currentTimeMillis() - stopwatch;
		sb.append(newline + newline + BaseConstants.LOG_SEPARATOR);
		sb.append(newline + "RESPONSE " + (output instanceof Throwable ? "FAILED" : "SUCCESS"));
		sb.append(newline + BaseConstants.LOG_SEPARATOR);

		if (output instanceof Throwable) {
			String errCode = getInternalErrorCode((Throwable) output);
			if (errCode != null) {
				sb.append(newline + "Error Code: " + errCode);
			}
			sb.append(newline + "Error Exception: " + newline + WebUtil.getStackTrace((Throwable) output));
		} else {
			sb.append(newline + "Response: " + newline + new ObjectMapper().valueToTree(output));
		}

		sb.append(newline + newline);
		sb.append("Process completed within " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " s / " + elapsedTime
				+ " ms");
		sb.append(newline + "Finished at: " + new Date());
		sb.append(newline + BaseConstants.LOG_SEPARATOR + newline + newline);
		LOGGER.info("{}", sb);

	}


	private void saveAuditTrail(IdmAuditTrail s) {
		try {
			idmAuditTrailSvc.create(s);
		} catch (Exception e) {
			LOGGER.error("Error Saving Audit Trail: {}", e.getMessage());
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