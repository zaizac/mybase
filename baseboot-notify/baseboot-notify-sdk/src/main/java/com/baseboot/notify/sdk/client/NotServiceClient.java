/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.notify.sdk.constants.MailTypeEnum;
import com.baseboot.notify.sdk.constants.NotErrorCodeEnum;
import com.baseboot.notify.sdk.constants.NotUrlConstants;
import com.baseboot.notify.sdk.constants.SmsTemplate;
import com.baseboot.notify.sdk.exception.NotificationException;
import com.baseboot.notify.sdk.model.EmailTemplate;
import com.baseboot.notify.sdk.model.Notification;
import com.baseboot.notify.sdk.model.ServiceCheck;
import com.baseboot.notify.sdk.model.Sms;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotServiceClient.class);

	private static NotRestTemplate restTemplate;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;

	private int readTimeout;


	private NotServiceClient() {
		// Block Initialization
	}


	public NotServiceClient(String url) {
		this.url = url;
		initialize();
	}


	public NotServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public NotServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	private void initialize() {
		restTemplate = new NotRestTemplate();
	}


	private NotRestTemplate getRestTemplate() throws NotificationException {
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


	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.debug("Service Rest URL: {}", uri);
		return uri;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String checkConnection() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.SERVICE_CHECK + "/test"), String.class);
	}


	public ServiceCheck serviceTest() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	/**
	 * Add Notification
	 * 
	 * @param notification
	 * @param mailType
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public Notification addNotification(Notification notification, MailTypeEnum mailType, Object template)
			throws NotificationException {
		if (BaseUtil.isObjNull(notification) || BaseUtil.isObjNull(notification)
				|| BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400EQM003);
		}

		if (BaseUtil.isEqualsCaseIgnore(MailTypeEnum.MAIL.name(), mailType.name())
				&& !(template instanceof MailTemplate)
				|| BaseUtil.isEqualsCaseIgnore(MailTypeEnum.SMS.name(), mailType.name())
						&& !(template instanceof SmsTemplate)) {
			throw new NotificationException(NotErrorCodeEnum.E400EQM003);
		}

		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(NotUrlConstants.NOTIFY);
		sbUrl.append("?notifyType={notifyType}");
		sbUrl.append("&templateCode={templateCode}");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notifyType", mailType.getType());
		if (BaseUtil.isEqualsCaseIgnore(MailTypeEnum.MAIL.name(), mailType.name())) {
			MailTemplate mailTemp = (MailTemplate) template;
			params.put("templateCode", mailTemp.name());
		} else if (BaseUtil.isEqualsCaseIgnore(MailTypeEnum.SMS.name(), mailType.name())) {
			SmsTemplate smsTemp = (SmsTemplate) template;
			params.put("templateCode", smsTemp.name());
		}

		ObjectMapper mapper = new ObjectMapper();
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
			return (Notification) getRestTemplate().postForObject(getServiceURI(sbUrl.toString()), map,
					Notification.class, params);
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
			throw new NotificationException(e.getMessage());
		}
		
	}


	/**
	 * Real time sending of SMS
	 * 
	 * @param mobileNo
	 * @param content
	 * @return
	 * @throws NotificationException
	 */
	public Sms sendSms(String mobileNo, String content) throws NotificationException {
		return (Sms) getRestTemplate().postForObject(getServiceURI(NotUrlConstants.NOTIFY_SMS_SEND),
				new Sms(mobileNo, content), Sms.class);
	}

	/**
	 * Get All Email Templates
	 * 
	 * @return
	 * @throws NotificationException
	 */
	public List<EmailTemplate> getAllEmailTemplate() throws NotificationException {
		List<EmailTemplate> emailTemplateList = new ArrayList<EmailTemplate>();
		EmailTemplate emailTemplateArr[] = getRestTemplate()
				.getForObject(getServiceURI(NotUrlConstants.MAIL_TEMPLATE), EmailTemplate[].class);
		emailTemplateList = Arrays.asList(emailTemplateArr);
		return emailTemplateList;
	}


	/**
	 * Update Email Template
	 * 
	 * @param emailTemplate
	 * @return
	 * @throws NotificationException
	 */
	public EmailTemplate updateMailTemplateContent(EmailTemplate emailTemplate) throws NotificationException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonStr = mapper.valueToTree(emailTemplate);
		Map<String, Object> map;
		try {
			map = mapper.readValue(jsonStr.toString(), new TypeReference<Map<String, Object>>() {
			});
			return (EmailTemplate) getRestTemplate().postForObject(getServiceURI(NotUrlConstants.UPDATE_MAIL_TEMPLATE),
					map, EmailTemplate.class);
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
			throw new NotificationException(e.getMessage());
		}

	}


	@SuppressWarnings("unchecked")
	public Map<String, String> findAllConfig() throws NotificationException {
		return getRestTemplate().getForObject(getServiceURI(NotUrlConstants.NOTIFY_CONFIG), HashMap.class);
	}


	public String getConfiValByConfigCode(String configCode) throws NotificationException {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(NotUrlConstants.GET_CONFIG_VAL_BY_CONFIG_CODE);
		sbUrl.append("?configCode={configCode}");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configCode", configCode);

		String configVal = (String) getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), String.class,
				params);

		return configVal;
	}


	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}