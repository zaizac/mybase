/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class WebUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);


	private WebUtil() {
		// Avoid Initialization
	}


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
		InputStream inputStream = is;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			if (inputStream != null) {
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
		}
		return stringBuilder.toString();
	}


	public static boolean checkTokenError(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), ex.getInternalErrorCode())
					|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(),
							ex.getInternalErrorCode())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
				}
				return true;
			}
		}
		return false;
	}
}