/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.util;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.idm.form.ImageSizeConfig;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.exception.NotificationException;
import com.notify.sdk.model.EmailTemplate;
import com.report.sdk.constants.ReportErrorCodeEnum;
import com.report.sdk.exception.ReportException;
import com.util.BaseUtil;
import com.util.model.CustomMultipartFile;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
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


	public static boolean checkTokenError(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			return checkException(ex.getInternalErrorCode(), ex.getMessage());
		} else if (e instanceof ReportException) {
			ReportException ex = (ReportException) e;
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
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()) {
				CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
				cud.setForceLogout(true);
				cud.setErrorCode(intErrCode);
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
				if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()) {
					if (auth.getPrincipal() instanceof CustomUserDetails) {
						CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
						cud.setForceLogout(true);
						cud.setErrorCode(ex.getInternalErrorCode());
					}
				}
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
		} else if (e instanceof ReportException) {
			ReportException ex = (ReportException) e;
			LOGGER.info("ReportException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
			intErrCd = ex.getInternalErrorCode();
		}
		if (!BaseUtil.isObjNull(intErrCd) && SERVICE_DOWN_ERROR.contains(intErrCd)) {
			map.put("systemDown", true);
			map.put("systemDownErrCd", intErrCd);
		}
		return map;
	}


	@SuppressWarnings("serial")
	public static final List<String> SERVICE_DOWN_ERROR = Collections.unmodifiableList(new ArrayList<String>() {

		{
			add(IdmErrorCodeEnum.E503IDM000.name());
			add(NotErrorCodeEnum.E503NOT000.name());
			add(ReportErrorCodeEnum.E503RPT000.name());
		}
	});

	@SuppressWarnings("serial")
	public static final List<String> SYSTEM_DOWN_ERROR = Collections.unmodifiableList(new ArrayList<String>() {

		{
			add(IdmErrorCodeEnum.E503IDM000.name());
			add(IdmErrorCodeEnum.E503IDM901.name());
			add(IdmErrorCodeEnum.E408IDM902.name());
			add(IdmErrorCodeEnum.E500IDM903.name());
		}
	});

	@SuppressWarnings("serial")
	public static final List<String> FORCE_LOGOUT_ERROR = Collections.unmodifiableList(new ArrayList<String>() {

		{
			add(IdmErrorCodeEnum.I404IDM113.name());
			add(IdmErrorCodeEnum.I404IDM115.name());
		}
	});


	public static List<EmailTemplate> customTemplateToVariable(List<EmailTemplate> emailtemplateList) {
		String reqContentTemplate = "";
		for (EmailTemplate emailTemplate : emailtemplateList) {
			reqContentTemplate = emailTemplate.getEmailContent().replace(PageConstants.NAME_BLOCK,
					PageConstants.NAME_VARIABLE);
			reqContentTemplate = reqContentTemplate.replace(PageConstants.LOGIN_BLOCK,
					PageConstants.LOGIN_NAME_VARIABLE);
			reqContentTemplate = reqContentTemplate.replace(PageConstants.PASSWORD_BLOCK,
					PageConstants.PASSWORD_VARIABLE);
			reqContentTemplate = reqContentTemplate.replace(PageConstants.FWCMS_URL_BLOCK,
					PageConstants.FWCMS_URL_VARIABLE);
			reqContentTemplate = reqContentTemplate.replace(PageConstants.OTP_BLOCK, PageConstants.OTP_VARIABLE);
			emailTemplate.setEmailContent(reqContentTemplate);
		}

		return emailtemplateList;
	}


	public static boolean imageControl(ImageSizeConfig imageProp, CustomMultipartFile file) throws IOException {
		if (!(file.getContent().length > 0) || file.getContent() == null) {
			return false;
		}

		InputStream in = new ByteArrayInputStream(file.getContent());
		BufferedImage originalImage = ImageIO.read(in);

		if (originalImage.getHeight() > imageProp.getMaxHeight()
				|| originalImage.getHeight() < imageProp.getMinHeight()) {
			return false;
		}
		if (originalImage.getWidth() > imageProp.getMaxWidth()
				|| originalImage.getWidth() < imageProp.getMinWidth()) {
			return false;
		}

		return true;
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


	public static String getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return auth.getName();
		}
		return "";
	}
}