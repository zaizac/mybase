/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.client;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstsb.notify.sdk.constants.NotErrorCodeEnum;
import com.bstsb.notify.sdk.constants.NotUrlConstants;
import com.bstsb.notify.sdk.exception.NotificationException;
import com.bstsb.notify.sdk.model.EmailTemplate;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.notify.sdk.model.Sms;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.JsonUtil;
import com.bstsb.util.http.HttpAuthClient;
import com.bstsb.util.model.ServiceCheck;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class NotServiceClient.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotServiceClient {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotServiceClient.class);

	/** The rest template. */
	private static NotRestTemplate restTemplate;

	/** The url. */
	private String url;

	/** The client id. */
	private String clientId;

	/** The token. */
	private String token;

	/** The auth token. */
	private String authToken;

	/** The message id. */
	private String messageId;

	/** The read timeout. */
	private int readTimeout;


	/**
	 * Instantiates a new not service client.
	 *
	 * @param url
	 *             the url
	 */
	public NotServiceClient(String url) {
		this.url = url;
	}


	/**
	 * Instantiates a new not service client.
	 *
	 * @param url
	 *             the url
	 * @param readTimeout
	 *             the read timeout
	 */
	public NotServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
	}


	/**
	 * Instantiates a new not service client.
	 *
	 * @param url
	 *             the url
	 * @param clientId
	 *             the client id
	 * @param readTimeout
	 *             the read timeout
	 */
	public NotServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
	}


	static {
		restTemplate = new NotRestTemplate("NOT");
	}


	/**
	 * Gets the rest template.
	 *
	 * @return the rest template
	 */
	private NotRestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT001);
		}
		if (authToken != null) {
			httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
		} else {
			httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	/**
	 * Gets the service URI.
	 *
	 * @param serviceName
	 *             the service name
	 * @return the service URI
	 */
	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.debug("Service Rest URL: {}", uri);
		return uri;
	}


	/**
	 * Sets the client id.
	 *
	 * @param clientId
	 *             the new client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	/**
	 * Sets the token.
	 *
	 * @param token
	 *             the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}


	/**
	 * Sets the auth token.
	 *
	 * @param authToken
	 *             the new auth token
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	/**
	 * Sets the message id.
	 *
	 * @param messageId
	 *             the new message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	/**
	 * Check connection.
	 *
	 * @return the string
	 */
	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.SERVICE_CHECK + "/test"), String.class);
	}


	/**
	 * Service test.
	 *
	 * @return the service check
	 */
	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	/**
	 * Add Notification.
	 *
	 * @param notification
	 *             the notification
	 * @param templateCode
	 *             the template code
	 * @return the notification
	 */
	public Notification addNotification(Notification notification, String templateCode) {
		if (BaseUtil.isObjNull(notification) || BaseUtil.isObjNull(templateCode)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(NotUrlConstants.NOTIFY_NEW);
		sbUrl.append("?templateCode={templateCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("templateCode", templateCode);

		ObjectMapper mapper = JsonUtil.objectMapper();
		JsonNode jsonStr = mapper.valueToTree(notification);
		Map<String, Object> map;
		try {
			map = mapper.readValue(jsonStr.toString(), new TypeReference<Map<String, Object>>() {
			});
			if (LOGGER.isDebugEnabled()) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (!BaseUtil.isObjNull(entry.getValue())) {
						LOGGER.debug("PARAMS: {} - {}", entry.getKey(), entry.getValue());
					}
				}
			}
			return getRestTemplate().postForObject(getServiceURI(sbUrl.toString()), map, Notification.class, params);
		} catch (IOException e) {
			throw new NotificationException(e.getMessage());
		}

	}


	/**
	 * Real time sending of SMS.
	 *
	 * @param mobileNo
	 *             the mobile no
	 * @param content
	 *             the content
	 * @param templateCode
	 *             the template code
	 * @return the sms
	 * @throws NotificationException
	 *              the notification exception
	 */
	public Sms sendSms(String mobileNo, String content, String templateCode) {
		return getRestTemplate().postForObject(getServiceURI(NotUrlConstants.NOTIFY_SMS_SEND),
				new Sms(mobileNo, content, templateCode), Sms.class);
	}


	/**
	 * Get All Email Templates.
	 *
	 * @return the all email template
	 * @throws NotificationException
	 *              the notification exception
	 */
	public List<EmailTemplate> getAllEmailTemplate() {
		EmailTemplate[] emailTemplateArr = getRestTemplate()
				.getForObject(getServiceURI(NotUrlConstants.MAIL_TEMPLATE), EmailTemplate[].class);
		return Arrays.asList(emailTemplateArr);
	}


	/**
	 * Update Email Template.
	 *
	 * @param emailTemplate
	 *             the email template
	 * @return the email template
	 * @throws NotificationException
	 *              the notification exception
	 */
	public EmailTemplate updateMailTemplateContent(EmailTemplate emailTemplate) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonStr = mapper.valueToTree(emailTemplate);
		Map<String, Object> map;
		try {
			map = mapper.readValue(jsonStr.toString(), new TypeReference<Map<String, Object>>() {
			});
			return getRestTemplate().postForObject(getServiceURI(NotUrlConstants.UPDATE_MAIL_TEMPLATE), map,
					EmailTemplate.class);
		} catch (IOException e) {
			throw new NotificationException(e.getMessage());
		}

	}


	/**
	 * Find all config.
	 *
	 * @return the map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findAllConfig() {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.NOTIFY_CONFIG), HashMap.class);
	}


	/**
	 * Gets the confi val by config code.
	 *
	 * @param configCode
	 *             the config code
	 * @return the confi val by config code
	 */
	public String getConfiValByConfigCode(String configCode) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(NotUrlConstants.GET_CONFIG_VAL_BY_CONFIG_CODE);
		sbUrl.append("?configCode={configCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("configCode", configCode);

		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), String.class, params);
	}


	/**
	 * Sets the read timeout.
	 *
	 * @param readTimeout
	 *             the new read timeout
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}


	/**
	 * Search Email Templates.
	 *
	 * @return the all email template
	 * @throws NotificationException
	 *              the notification exception
	 */
	public List<EmailTemplate> searchEmailTemplate(EmailTemplate template) {
		EmailTemplate[] emailTemplateArr = getRestTemplate().postForObject(
				getServiceURI(NotUrlConstants.MAIL_TEMPLATE + "/search"), template, EmailTemplate[].class);
		return Arrays.asList(emailTemplateArr);
	}


	/**
	 * Search Notification.
	 *
	 * @return the all email template
	 * @throws NotificationException
	 *              the notification exception
	 */
	public List<Notification> searchNotificationPayload(Notification notification) {
		Notification[] notificationArr = getRestTemplate().postForObject(
				getServiceURI(NotUrlConstants.NOTIFICATIONS + "/search"), notification, Notification[].class);
		return Arrays.asList(notificationArr);
	}


	public List<Notification> findWrkrEmailNotification(String notifyTo) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(NotUrlConstants.MAIL_NOTIFICATIONS);
		sbUrl.append("?notifyTo={notifyTo}");

		Map<String, Object> params = new HashMap<>();
		params.put("notifyTo", notifyTo);

		Notification[] emailArr = getRestTemplate().getForObject(getServiceURI(sbUrl.toString()),
				Notification[].class, params);
		return Arrays.asList(emailArr);
	}
}