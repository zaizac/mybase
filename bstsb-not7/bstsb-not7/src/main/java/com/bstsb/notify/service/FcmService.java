/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.bstsb.notify.constants.ConfigConstants;
import com.bstsb.notify.controller.GenericAbstract;
import com.bstsb.notify.model.NotEmailTemplate;
import com.bstsb.util.constants.BaseConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



/**
 * The Class FcmService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service
public class FcmService extends GenericAbstract {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FcmService.class);

	/** The email from. */
	@Value("${" + ConfigConstants.MAIL_CONF_SENDER + "}")
	private String emailFrom;

	/** The fcm url. */
	@Value("${" + ConfigConstants.FCM_CONF_URL + "}")
	private String fcmUrl;

	/** The key. */
	@Value("${" + ConfigConstants.FCM_CONF_KEY + "}")
	private String key;

	/** The time out value. */
	@Value("${" + ConfigConstants.FCM_CONF_TIMEOUT + "}")
	private Integer timeOutValue;

	/** The not email template svc. */
	@Autowired
	NotEmailTemplateService notEmailTemplateSvc;


	/**
	 * Send noti fcm.
	 *
	 * @param subject the subject
	 * @param emailTO the email TO
	 * @param emailCC the email CC
	 * @param emailParam the email param
	 * @param templateCode the template code
	 */
	public void sendNotiFcm(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateCode) {

		NotEmailTemplate temp = notEmailTemplateSvc.findByTemplateCode(templateCode);

		String notifyContent = temp.getEmailContent();
		notifyContent = notifyContent.replace("token", emailCC);
		notifyContent = notifyContent.replace("subject", subject);
		notifyContent = notifyContent.replace("emailTO", emailTO);

		try {
			ObjectNode tempJson = new ObjectMapper().readValue(notifyContent, ObjectNode.class);

			String body = null;
			if (tempJson.has("notification")) {
				body = tempJson.get("notification").findValue("body").textValue();
				String newBody = body;
				for (Entry<String, Object> a : emailParam.entrySet()) {
					newBody = newBody.replace(a.getKey(), a.getValue().toString());
				}
				notifyContent = notifyContent.replace(body, newBody);
			}

		} catch (Exception e) {
			LOGGER.error("Exception: sendNotiFcm: ", e);
		}

		LOGGER.info("request for google api Android: {}", notifyContent);
		try {
			callRestEndpoint(HttpMethod.POST, notifyContent);
		} catch (Exception e) {
			LOGGER.error("Exception: sendNotiFcm: ", e);
		}

	}


	/**
	 * Call rest endpoint.
	 *
	 * @param httpMethod the http method
	 * @param jsonStr the json str
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String callRestEndpoint(HttpMethod httpMethod, String jsonStr) throws IOException {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(timeOutValue);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(timeOutValue);
		requestBuilder = requestBuilder.setSocketTimeout(timeOutValue);

		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(requestBuilder.build());
		HttpClient client = builder.build();

		String authorization = key;
		Header[] headers = new Header[3];

		String messageId = UUID.randomUUID().toString();
		headers[0] = new BasicHeader(HttpHeaders.AUTHORIZATION, authorization);
		headers[1] = new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers[2] = new BasicHeader(BaseConstants.HEADER_MESSAGE_ID, messageId);

		HttpResponse response = null;

		if (httpMethod.equals(HttpMethod.POST)) {
			HttpPost post = new HttpPost(fcmUrl);
			StringEntity input = new StringEntity(jsonStr);
			input.setContentType(MediaType.APPLICATION_JSON_VALUE);

			post.setHeaders(headers);
			post.setEntity(input);
			post.setConfig(requestBuilder.build());
			response = client.execute(post);

		} else if (httpMethod.equals(HttpMethod.GET)) {
			HttpGet getObj = new HttpGet(fcmUrl);
			getObj.setHeaders(headers);
			getObj.setConfig(requestBuilder.build());
			response = client.execute(getObj);
		}

		String result = null;
		try (BufferedReader rd = new BufferedReader(
				new InputStreamReader(response != null ? response.getEntity().getContent() : null))) {
			result = rd.readLine();
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
		}
		return result;

	}


	/**
	 * Gets the time out value.
	 *
	 * @return the time out value
	 */
	public Integer getTimeOutValue() {
		return timeOutValue;
	}


	/**
	 * Sets the time out value.
	 *
	 * @param timeOutValue the new time out value
	 */
	public void setTimeOutValue(Integer timeOutValue) {
		this.timeOutValue = timeOutValue;
	}


	/**
	 * Gets the fcm url.
	 *
	 * @return the fcm url
	 */
	public String getFcmUrl() {
		return fcmUrl;
	}


	/**
	 * Sets the fcm url.
	 *
	 * @param fcmUrl the new fcm url
	 */
	public void setFcmUrl(String fcmUrl) {
		this.fcmUrl = fcmUrl;
	}


	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

}