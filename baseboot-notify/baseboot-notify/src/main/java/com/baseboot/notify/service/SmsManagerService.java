/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.baseboot.notify.controller.GenericAbstract;
import com.baseboot.notify.sdk.constants.NotConfigConstants;
import com.baseboot.notify.sdk.model.Sms;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.baseboot.notify.util.ApplicationConstants;
import com.baseboot.notify.util.DataEncryptor;
import com.baseboot.notify.util.SMSNotificationStatusType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service
public class SmsManagerService extends GenericAbstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsManagerService.class);

	@Autowired
	MessageSource messageSource;


	public String sendOTPThroughICE(Sms userDetails) throws Exception {
		try {
			return sendMessage(userDetails.getMobileNo(), userDetails.getContent(), createHttpUrl(userDetails));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(" sendOTPThroughICE Exception: {}", e);
			return "";
		}
	}


	private String createHttpUrl(Sms sms) throws UnsupportedEncodingException, Exception {
		StringBuilder sbhttpUrl = new StringBuilder();

		String uname = messageSource.getMessage("sms.gateway.username", null, Locale.getDefault());
		String pword = messageSource.getMessage("sms.gateway.password", null, Locale.getDefault());

		if (BaseUtil.isObjNull(uname))
			uname = getConfigValue(NotConfigConstants.SMS_GATEWAY_UNAME);
		if (BaseUtil.isObjNull(pword))
			pword = getConfigValue(NotConfigConstants.SMS_GATEWAY_PWORD);

		LOGGER.info("username: {}", uname);
		LOGGER.info("password: {}", pword);
		String SMSGWPwd = getDecryptedPassword(pword);

		String SMSToMobileNo = StringUtils.trim(sms.getMobileNo());
		String SMSContent = StringUtils.trim(sms.getContent());

		LOGGER.info(" SMS To Mobile No: {}, Message: {}", SMSToMobileNo, SMSContent);

		sbhttpUrl.append(URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_USER_KEY,
				ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF) + "="
				+ URLEncoder.encode(uname, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_PASSWORD_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(SMSGWPwd, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_TO_CONTACT_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(SMSToMobileNo, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_FROM_CONTACT_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_FROM_VAL,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_CODING_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_CODING_VAL,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_MSG_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(SMSContent, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		return sbhttpUrl.toString();
	}


	private static String getDecryptedPassword(String iceGatewayAuthPasswordVal) throws Exception {
		try {
			return StringUtils.trim(new DataEncryptor().decrypt(iceGatewayAuthPasswordVal));
		} catch (Exception e) {
			LOGGER.error(" Password Decryption Exception: {}", e);
			return "";
		}
	}


	private String sendMessage(String mobileNumber, String userOTP, String sbhttpUrl) throws Exception {
		try {
			LOGGER.info(" SMS To Mobile NO: {} - Message: {} - URL: {}", mobileNumber, userOTP, sbhttpUrl);
			String authUrl = messageSource.getMessage("sms.gateway.auth.url", null, Locale.getDefault());
			if (BaseUtil.isObjNull(authUrl))
				authUrl = getConfigValue(NotConfigConstants.SMS_GATEWAY_URL);

			URL url = new URL(authUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(sbhttpUrl);
			wr.flush();

			// Get the response
			BufferedReader resp = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String status = resp.readLine();
			LOGGER.info("ICE sms response message: {}", resp.readLine());
			resp.close();

			return status;

		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Send Message UnsupportedEncoding Exception : {}", e);
		} catch (MalformedURLException e) {
			LOGGER.error("Send Message MalformedURLException Exception : {}", e);
		} catch (IOException e) {
			LOGGER.error("Send Message IOException Exception : {}", e);
		}
		return "";
	}


	@SuppressWarnings("unused")
	private String getResponseMsg(String status) {
		String respMsg = "";

		if (StringUtils.isNotBlank(status)) {
			String subString = status.split(ApplicationConstants.AMPERSAND_STRING_SEPERATOR)[0];
			subString = subString.split(ApplicationConstants.EQUALS_STRING_SEPERATOR)[1];
			status = ApplicationConstants.STATUS_PREFIX + subString;
			if (SMSNotificationStatusType.validSMSNotificationStatusType(status)) {
				respMsg = SMSNotificationStatusType.valueOf(status).getSMSNotificationStatusType();
			}
		}

		return respMsg;
	}

}