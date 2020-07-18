/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.wfw.config.iam.CustomUserDetailsService;
import com.wfw.sdk.exception.WfwException;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public final class WebUtil {

	private WebUtil() {
		throw new IllegalStateException("WebUtil Utility class");
	}


	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);


	public static String getStackTrace(final Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		String[] lines = writer.toString().split("\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(lines.length, 10); i++) {
			sb.append(lines[i]).append("\n");
		}
		return sb.toString();
	}


	public static String getRequestBody(final InputStream is) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = is;
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			LOGGER.error("IOException: {}", ex.getMessage());
			throw ex;
		}
		return stringBuilder.toString();
	}


	public static boolean checkTokenError(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		} else if (e instanceof WfwException) {
			WfwException ex = (WfwException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		}
		return false;
	}


	private static boolean checkException(String intErrCode, String intErrMsg) {
		if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), intErrCode)
				|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(), intErrCode)) {
			LOGGER.info("checkTokenError: TokenException: {} - {} ", intErrCode, intErrMsg);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()) {
				auth.getPrincipal();
				LOGGER.info("LOGOUT FORCE TRUE");
			}
			return true;
		}
		return false;
	}


	public static boolean checkSystemDown(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			if (SYSTEM_DOWN_ERROR.contains(ex.getInternalErrorCode())) {
				LOGGER.info("checkSystemDown: IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()
						&& (auth instanceof CustomUserDetailsService)) {
					auth.getPrincipal();
				}
				return true;
			}
		}
		return false;
	}


	public static final List<String> SYSTEM_DOWN_ERROR = Collections
			.unmodifiableList(Arrays.asList(IdmErrorCodeEnum.E503IDM000.name(), IdmErrorCodeEnum.E503IDM901.name(),
					IdmErrorCodeEnum.E408IDM902.name(), IdmErrorCodeEnum.E500IDM903.name()));

	public static final List<String> FORCE_LOGOUT_ERROR = Collections
			.unmodifiableList(Arrays.asList(IdmErrorCodeEnum.I404IDM113.name(), IdmErrorCodeEnum.I404IDM115.name()));
}