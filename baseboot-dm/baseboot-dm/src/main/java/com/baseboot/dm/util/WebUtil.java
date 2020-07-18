/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.dm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.util.BaseUtil;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 3, 2016
 */
public class WebUtil {

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
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return stringBuilder.toString();
	}

	public static boolean checkTokenError(Exception e) {
		if(e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			if(BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), ex.getInternalErrorCode())
					|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(), ex.getInternalErrorCode())) {
				LOGGER.info("checkTokenError: IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
				return true;
			}
		}
		return false;
	}
	
}