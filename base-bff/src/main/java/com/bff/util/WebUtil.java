/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.bff.cmn.form.CustomMultipartFile;
import com.bff.idm.form.ImageSizeConfig;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.exception.NotificationException;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class WebUtil {

	private WebUtil() {
		throw new IllegalStateException("Utility class");
	}


	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

	protected static final List<String> SERVICE_DOWN_ERROR = new ArrayList<>();

	protected static final List<String> SYSTEM_DOWN_ERROR = new ArrayList<>();

	protected static final List<String> FORCE_LOGOUT_ERROR = new ArrayList<>();

	static {
		SERVICE_DOWN_ERROR.add(IdmErrorCodeEnum.E503IDM000.name());
		SERVICE_DOWN_ERROR.add(BeErrorCodeEnum.E503C003.name());
		SERVICE_DOWN_ERROR.add(NotErrorCodeEnum.E503NOT000.name());

		SYSTEM_DOWN_ERROR.add(IdmErrorCodeEnum.E503IDM000.name());
		SYSTEM_DOWN_ERROR.add(IdmErrorCodeEnum.E503IDM901.name());
		SYSTEM_DOWN_ERROR.add(IdmErrorCodeEnum.E408IDM902.name());
		SYSTEM_DOWN_ERROR.add(IdmErrorCodeEnum.E500IDM903.name());

		FORCE_LOGOUT_ERROR.add(IdmErrorCodeEnum.I404IDM113.name());
		FORCE_LOGOUT_ERROR.add(IdmErrorCodeEnum.I404IDM115.name());
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


	public static boolean checkTokenError(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		} else if (e instanceof BeException) {
			BeException ex = (BeException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		} else if (e instanceof NotificationException) {
			NotificationException ex = (NotificationException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		}
		return false;
	}


	private static boolean checkException(String intErrCode, String intErrMsg) {
		if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), intErrCode)
				|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(), intErrCode)) {
			LOGGER.info("checkTokenError: IdmException: {} - {} ", intErrCode, intErrMsg);
 			// FIXME		
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()) {
//				CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
//				cud.setForceLogout(true);
//				cud.setErrorCode(intErrCode);
//				LOGGER.info("LOGOUT FORCE TRUE");
//			}
			return true;
		}
		return false;
	}


	public static boolean checkSystemDown(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			if (SYSTEM_DOWN_ERROR.contains(ex.getInternalErrorCode())) {
				LOGGER.info("checkSystemDown: IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
				// FIXME
//				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//				if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()
//						&& auth.getPrincipal() instanceof CustomUserDetails) {
//					CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
//					cud.setForceLogout(true);
//					cud.setErrorCode(ex.getInternalErrorCode());
//				}
				return true;
			}
		}
		return false;
	}


	public static Map<String, Object> checkServiceDown(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("systemDown", false);
		map.put("systemDownErrCd", null);
		String intErrCd = null;
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			LOGGER.info("IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
			intErrCd = ex.getInternalErrorCode();
		} else if (e instanceof NotificationException) {
			NotificationException ex = (NotificationException) e;
			LOGGER.info("NotificationException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
			intErrCd = ex.getInternalErrorCode();
		} else if (e instanceof BeException) {
			BeException ex = (BeException) e;
			LOGGER.info("SvcException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
			intErrCd = ex.getInternalErrorCode();
		}
		if (!BaseUtil.isObjNull(intErrCd) && SERVICE_DOWN_ERROR.contains(intErrCd)) {
			map.put("systemDown", true);
			map.put("systemDownErrCd", intErrCd);
		}
		return map;
	}


	public static boolean imageControl(ImageSizeConfig imageProp, CustomMultipartFile file) throws IOException {
		if (file.getContent().length == 0 || file.getContent() == null) {
			return false;
		}

		InputStream in = new ByteArrayInputStream(file.getContent());
		BufferedImage originalImage = ImageIO.read(in);

		if ((originalImage.getHeight() > imageProp.getMaxHeight()
				|| originalImage.getHeight() < imageProp.getMinHeight())
				|| (originalImage.getWidth() > imageProp.getMaxWidth()
						|| originalImage.getWidth() < imageProp.getMinWidth())) {
			return false;
		}

		return true;
	}


	public static String getRequestBody(final InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
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
			LOGGER.error("{}", ex.getMessage());
			throw ex;
		}
		return stringBuilder.toString();
	}


	
}