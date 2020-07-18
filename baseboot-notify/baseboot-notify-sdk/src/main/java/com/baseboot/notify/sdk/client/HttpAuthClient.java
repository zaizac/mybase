/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.client;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.notify.sdk.util.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class HttpAuthClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpAuthClient.class);

	private int readTimeout;

	private String clientID;

	private String secretKey;

	private String authToken;

	private String messageId;

	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();


	public HttpAuthClient(String authToken, String messageId, int readTimeout) {
		this.authToken = authToken;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
	}


	public HttpAuthClient(String clientID, String secretKey, String messageId, int readTimeout) {
		this.clientID = clientID;
		this.secretKey = secretKey;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
	}


	private static CredentialsProvider buildCredentials(String uname, String pword, String authScheme) {
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, "", authScheme);
		CredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(authScope, new UsernamePasswordCredentials(uname, pword));
		return cp;
	}


	public CloseableHttpClient getHttpClient() {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		RequestConfig rc = RequestConfig.custom().setAuthenticationEnabled(true).setConnectTimeout(readTimeout * 1000)
				.setConnectionRequestTimeout(readTimeout * 1000).setSocketTimeout(readTimeout * 1000).build();
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(cm);
		builder.setDefaultRequestConfig(rc);

		String authorization = null;

		if (authToken != null) {
			authorization = authToken;
		} else {
			String uname = this.clientID;
			String pword = this.secretKey;

			String authScheme = AuthSchemes.BASIC;
			credsProvider = buildCredentials(uname, pword, authScheme);
			builder.setDefaultCredentialsProvider(credsProvider);

			String auth = uname + ":" + pword;
			LOGGER.debug("Authorization : {}", auth);
			authorization = authScheme + " " + Base64.encodeBase64String(auth.getBytes());
		}

		List<Header> headers = new ArrayList<Header>();
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(header);
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()));
		headers.add(new BasicHeader("X-Message-Id", messageId));
		LOGGER.debug("HEADERS: {}", new ObjectMapper().valueToTree(headers));
		builder.setDefaultHeaders(headers);
		return builder.build();
	}

}