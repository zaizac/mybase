/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.sdk.client;


import org.apache.http.impl.client.CloseableHttpClient;

import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.sgw.sdk.exception.SgwException;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwServiceClient {

	private static SgwRestTemplate restTemplate;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;

	private int readTimeout;


	public SgwServiceClient(String url) {
		this.url = url;
		initialize();
	}


	public SgwServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public SgwServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	private void initialize() {
		restTemplate = new SgwRestTemplate();
	}


	private SgwRestTemplate getRestTemplate() throws Exception {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new SgwException(SgwErrorCodeEnum.E400SGW001);
		}
		if (authToken != null) {
			httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
		} else {
			httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
