/**
 * Copyright 2019. 
 */
package com.util.http;


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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.MediaType;


/**
 * The Class HttpAuthClient.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class HttpAuthClient {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpAuthClient.class);

	/** The read timeout. */
	private int readTimeout;

	/** The client ID. */
	private String clientID;

	/** The secret key. */
	private String secretKey;

	/** The auth token. */
	private String authToken;

	/** The message id. */
	private String messageId;
	
	/** The message id. */
	private String portalType;
	
	/** The system type. */
	private String systemType;

	/** The cm. */
	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();


	/**
	 * Instantiates a new http auth client.
	 *
	 * @param authToken the auth token
	 * @param messageId the message id
	 * @param readTimeout the read timeout
	 */
	public HttpAuthClient(String authToken, String messageId, int readTimeout) {
		this.authToken = authToken;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
	}

	/**
	 * Instantiates a new http auth client.
	 *
	 * @param authToken the auth token
	 * @param messageId the message id
	 * @param readTimeout the read timeout
	 */
	public HttpAuthClient(String authToken, String messageId, int readTimeout, String portalType) {
		this.authToken = authToken;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
		this.portalType = portalType;
	}

	/**
	 * Instantiates a new http auth client.
	 *
	 * @param clientID the client ID
	 * @param secretKey the secret key
	 * @param messageId the message id
	 * @param readTimeout the read timeout
	 */
	public HttpAuthClient(String clientID, String secretKey, String messageId, int readTimeout) {
		this.clientID = clientID;
		this.secretKey = secretKey;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
	}
	
	/**
	 * Instantiates a new http auth client.
	 *
	 * @param clientID the client ID
	 * @param secretKey the secret key
	 * @param messageId the message id
	 * @param readTimeout the read timeout
	 */
	public HttpAuthClient(String clientID, String secretKey, String messageId, int readTimeout, String portalType) {
		this.clientID = clientID;
		this.secretKey = secretKey;
		this.messageId = messageId;
		this.readTimeout = readTimeout;
		this.portalType = portalType;
	}


	/**
	 * Builds the credentials.
	 *
	 * @param uname the uname
	 * @param pword the pword
	 * @param authScheme the auth scheme
	 * @return the credentials provider
	 */
	private static CredentialsProvider buildCredentials(String uname, String pword, String authScheme) {
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, "", authScheme);
		CredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(authScope, new UsernamePasswordCredentials(uname, pword));
		return cp;
	}


	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public CloseableHttpClient getHttpClient() {
		RequestConfig rc = RequestConfig.custom().setAuthenticationEnabled(true).setConnectTimeout(readTimeout * 1000)
				.setConnectionRequestTimeout(readTimeout * 1000).setSocketTimeout(readTimeout * 1000).build();
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(cm);
		builder.setDefaultRequestConfig(rc);

		String authorization = null;

		if (authToken != null) {
			authorization = authToken;
		} else {
			String uname = clientID;
			String pword = secretKey;

			String authScheme = AuthSchemes.BASIC;
			CredentialsProvider credsProvider = buildCredentials(uname, pword, authScheme);
			builder.setDefaultCredentialsProvider(credsProvider);

			String auth = uname + ":" + pword;
			LOGGER.debug("Authorization: {}", auth);
			authorization = authScheme + " " + Base64.encodeBase64String(auth.getBytes());
		}

		List<Header> headers = new ArrayList<>();
		Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(header);
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
		headers.add(new BasicHeader("X-Message-Id", messageId));
		headers.add(new BasicHeader("X-Portal-Type", portalType));
		if (systemType != null) {
			headers.add(new BasicHeader("X-System-Type", systemType));
		}
		builder.setDefaultHeaders(headers);
		return builder.build();
	}
	
	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
}