/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.bstsb.notify.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstsb.notify.constants.ApplicationConstants;
import com.bstsb.notify.controller.GenericAbstract;
import com.bstsb.notify.model.NotEmailTemplate;
import com.bstsb.notify.sdk.constants.NotConfigConstants;
import com.bstsb.notify.sdk.constants.NotErrorCodeEnum;
import com.bstsb.notify.sdk.exception.NotificationException;
import com.bstsb.notify.sdk.model.Sms;
import com.bstsb.notify.util.DataEncryptor;
import com.bstsb.util.BaseUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * The Class SmsManagerService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service
public class SmsManagerService extends GenericAbstract {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsManagerService.class);

	/** The sms content. */
	private String smsContent;

	/** The not email template svc. */
	@Autowired
	NotEmailTemplateService notEmailTemplateSvc;


	/**
	 * Send OTP through ICE.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String sendOTPThroughICE(Sms userDetails) {

		if (userDetails == null) {
			throw new MissingResourceException("SMS: userDetails is null", Sms.class.getName(), "userDetails");
		}

		smsContent = StringUtils.trim(constructSMSContent(userDetails));

		try {
			return sendMessage(userDetails.getMobileNo(), smsContent, createHttpUrl(userDetails));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(" sendOTPThroughICE Exception: {}", e);
			return "";
		}

	}


	/**
	 * Construct SMS content.
	 *
	 * @param userDetails the user details
	 * @return 		How mapping is done. Example: Map valuesMap = HashMap();
	 *         valuesMap.put("animal", "quick brown fox");
	 *         valuesMap.put("target", "lazy dog"); String templateString =
	 *         "The ${animal} jumped over the ${target}."; StrSubstitutor sub =
	 *         new StrSubstitutor(valuesMap); String resolvedString =
	 *         sub.replace(templateString);
	 */
	private String constructSMSContent(Sms userDetails) {

		NotEmailTemplate temp = notEmailTemplateSvc.findByTemplateCode(userDetails.getTemplateCode());
		if (BaseUtil.isObjNull(temp)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		try {
			map = mapper.readValue(userDetails.getContent(), typeRef);
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException: sendOTPThroughICE: {}", e);
		} catch (JsonMappingException e) {
			LOGGER.error("JsonMappingException: sendOTPThroughICE: {}", e);
		} catch (IOException e) {
			LOGGER.error("IOException: sendOTPThroughICE: {}", e);
		}

		StrSubstitutor sub = new StrSubstitutor(map);
		return sub.replace(temp.getEmailContent());
	}


	/**
	 * Creates the http url.
	 *
	 * @param sms the sms
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private String createHttpUrl(Sms sms) throws UnsupportedEncodingException {
		StringBuilder sbhttpUrl = new StringBuilder();

		String uname = messageSource.getMessage("sms.gateway.username", null, Locale.getDefault());
		String pword = messageSource.getMessage("sms.gateway.password", null, Locale.getDefault());

		if (BaseUtil.isObjNull(uname)) {
			uname = getConfigValue(NotConfigConstants.SMS_GATEWAY_UNAME);
		}
		if (BaseUtil.isObjNull(pword)) {
			pword = getConfigValue(NotConfigConstants.SMS_GATEWAY_PWORD);
		}

		LOGGER.info("username: {}", uname);
		LOGGER.info("password: {}", pword);
		String smsSgwPwd = getDecryptedPassword(pword);

		String smsToMobileNo = StringUtils.trim(sms.getMobileNo());

		LOGGER.info(" SMS To Mobile No: {}, Message: {}", smsToMobileNo, smsContent);

		sbhttpUrl.append(URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_USER_KEY,
				ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF) + "="
				+ URLEncoder.encode(uname, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_PASS_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(smsSgwPwd, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		sbhttpUrl.append("&"
				+ URLEncoder.encode(ApplicationConstants.ICE_GATEWAY_TO_CONTACT_KEY,
						ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF)
				+ "=" + URLEncoder.encode(smsToMobileNo, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
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
				+ "=" + URLEncoder.encode(smsContent, ApplicationConstants.CHARACTER_ENCODE_FORMAT_UTF));
		return sbhttpUrl.toString();
	}


	/**
	 * Gets the decrypted password.
	 *
	 * @param iceGatewayAuthPasswordVal the ice gateway auth password val
	 * @return the decrypted password
	 */
	private static String getDecryptedPassword(String iceGatewayAuthPasswordVal) {
		try {
			return StringUtils.trim(new DataEncryptor().decrypt(iceGatewayAuthPasswordVal));
		} catch (Exception e) {
			LOGGER.error(" Password Decryption Exception: {}", e);
			return "";
		}
	}


	/**
	 * Send message.
	 *
	 * @param mobileNumber the mobile number
	 * @param userOTP the user OTP
	 * @param sbhttpUrl the sbhttp url
	 * @return the string
	 */
	private String sendMessage(String mobileNumber, String userOTP, String sbhttpUrl) {
		try {
			LOGGER.info(" SMS To Mobile NO: {} - Message: {} - URL: {}", mobileNumber, userOTP, sbhttpUrl);
			String authUrl = messageSource.getMessage("sms.gateway.auth.url", null, Locale.getDefault());
			if (BaseUtil.isObjNull(authUrl)) {
				authUrl = getConfigValue(NotConfigConstants.SMS_GATEWAY_URL);
			}

			URL url = new URL(authUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(sbhttpUrl);
			wr.flush();

			// Get the response
			BufferedReader resp = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String status = resp.readLine();
			LOGGER.info("ICE sms response message: {}", status);
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

}