/**
 * Copyright 2019. 
 */
package com.notify.sdk.client;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.constants.NotUrlConstants;
import com.notify.sdk.exception.NotificationException;
import com.notify.sdk.model.EmailTemplate;
import com.notify.sdk.model.Notification;
import com.notify.sdk.model.Sms;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.http.HttpAuthClient;
import com.util.model.ServiceCheck;


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
	
	/** The portal type. */
	private String portalType;

	/** The read timeout. */
	private int readTimeout;

	/** The system type */
	private String systemType;
	
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
		HttpAuthClient hac = null;
		if (authToken != null) {
			if(portalType != null) {
				hac = new HttpAuthClient(authToken, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(authToken, messageId, readTimeout);
			}
		} else {
			if(portalType != null) {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout);
			}
		}
		LOGGER.info("System Type: {} ", systemType);
		if (systemType != null) {
			hac.setSystemType(systemType);
		}
		httpClient = hac.getHttpClient();
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
	 * @param portalType the portalType to set
	 */
	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}
	
	/**
	 * @param systemType
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
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
			return getRestTemplate().postForObject(getServiceURI(NotUrlConstants.MAIL_TEMPLATE_UPDATE), map,
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

	
	public List<Notification> searchNotPayload(Notification not) {
		Notification [] resp = getRestTemplate().postForObject(getServiceURI(NotUrlConstants.NOTIFY_SEARCH), not, Notification[].class);
		List<Notification> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	
	public List<EmailTemplate> searchNotEmailTemplate(EmailTemplate et) {
		EmailTemplate [] resp = getRestTemplate().postForObject(getServiceURI(NotUrlConstants.MAIL_TEMPLATE_SEARCH), et, EmailTemplate[].class);
		List<EmailTemplate> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
}