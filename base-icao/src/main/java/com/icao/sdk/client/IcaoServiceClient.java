/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.client;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icao.sdk.constants.IcaoConstants;
import com.icao.sdk.constants.IcaoErrorCodeEnum;
import com.icao.sdk.exception.IcaoException;
import com.icao.sdk.model.IcaoInfo;
import com.icao.sdk.model.IcaoRequest;
import com.icao.sdk.model.IcaoResponse;


/**
 * @author mary.jane
 * @since Nov 26, 2018
 */
public class IcaoServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(IcaoServiceClient.class);

	private String url;

	private String key;

	private int readTimeout;

	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	private static final String KEY_ICAO_RESPONSE = "icaoResponse";

	private static final String KEY_IS_PASS = "isPass";


	public IcaoServiceClient(String url, String key, int readTimeout) {
		this.url = url;
		this.key = key;
		this.readTimeout = readTimeout;
	}


	/**
	 *
	 *
	 * @param icaoRequest
	 * @return
	 */
	public IcaoResponse verifyPhoto(IcaoRequest icaoRequest) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonStr = objectMapper.writeValueAsString(icaoRequest);

			StringBuilder sbUrl = new StringBuilder();
			sbUrl.append(url);
			sbUrl.append("/IcaoCheck");

			LOGGER.info("Verify Photo >> URL: {}", sbUrl);
			LOGGER.debug("Verify Photo >> Entity: {}", jsonStr);

			HttpPost httpPost = new HttpPost(sbUrl.toString());
			StringEntity input = new StringEntity(jsonStr);
			input.setContentType(IcaoConstants.APPLICATION_JSON);
			httpPost.setEntity(input);

			CloseableHttpResponse httpResponse = getHttpClient().execute(httpPost);
			LOGGER.info("Verify Photo >> Status: {}", httpResponse.getStatusLine().getStatusCode());

			String result = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("Verify Photo >> Response: {}", result);
			JsonNode icaoNode = objectMapper.readTree(result);
			return objectMapper.treeToValue(icaoNode.get("IcaoCheckResult"), IcaoResponse.class);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new IcaoException(IcaoErrorCodeEnum.E503ICAO000, new Object[] { url });
		}

	}


	/**
	 * Verify photo with configurable ICAO Info
	 *
	 * @param photo
	 *             - Base64
	 * @param icaoInfo
	 *             - IcaoInfo.java
	 * @return
	 */
	public IcaoResponse verifyPhoto(String photo, List<IcaoInfo> icaoInfo) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			IcaoRequest icaoRequest = new IcaoRequest();
			icaoRequest.setPhoto(photo);

			String jsonStr = objectMapper.writeValueAsString(icaoRequest);

			StringBuilder sbUrl = new StringBuilder();
			sbUrl.append(url);
			sbUrl.append("/GetICAOInfo");

			LOGGER.info("Check ICAO by Info >> URL: {}", sbUrl);
			LOGGER.debug("Check ICAO by Info >> Entity: {}", jsonStr);

			HttpPost httpPost = new HttpPost(sbUrl.toString());
			StringEntity input = new StringEntity(jsonStr);
			input.setContentType(IcaoConstants.APPLICATION_JSON);
			httpPost.setEntity(input);

			CloseableHttpResponse httpResponse = getHttpClient().execute(httpPost);
			LOGGER.info("Check ICAO by Info >> Status: {}", httpResponse.getStatusLine().getStatusCode());

			String result = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("Check ICAO by Info >> Response: {}", result);
			JsonNode icaoNode = objectMapper.readTree(result);
			JsonNode icaoResult = icaoNode.get("GetICAOInfoResult");
			IcaoResponse icaoResponse = new IcaoResponse();
			boolean isPass = true;
			boolean isSuccess = false;

			if (icaoResult != null) {
				Map<String, Object> mapResult = checkResult(icaoResult, icaoInfo);
				icaoResponse = (IcaoResponse) mapResult.get(KEY_ICAO_RESPONSE);
				isPass = Boolean.parseBoolean(String.valueOf(mapResult.get(KEY_IS_PASS)));
				if (icaoResult.get("responseCode").asInt() == 200) {
					isSuccess = true;
				}
			}

			if (isSuccess && isPass) {
				icaoResponse.setResult("pass");
			} else {
				icaoResponse.setResult("fail");
			}

			return icaoResponse;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new IcaoException(IcaoErrorCodeEnum.E503ICAO000, new Object[] { url });
		}
	}


	private Map<String, Object> checkResult(JsonNode icaoResult, List<IcaoInfo> icaoInfo) {
		Map<String, Object> mapResult = new HashMap<>();
		boolean isPass = true;
		Map<String, Boolean> icaoMap = new HashMap<>();
		if (!icaoInfo.isEmpty()) {
			for (IcaoInfo icao : icaoInfo) {
				boolean confResult = Boolean.parseBoolean(icaoResult.get(icao.getConfig()).toString());
				LOGGER.info("ICAO Criteria: {} - Result: {}", icao.getConfig(), confResult);
				icaoMap.put(icao.getCriteria(), confResult);
				if (!confResult) {
					isPass = confResult;
				}
			}
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String icaoStr = mapper.writeValueAsString(icaoMap);
			IcaoResponse icaoResponse = mapper.readValue(icaoStr, IcaoResponse.class);
			mapResult.put(KEY_ICAO_RESPONSE, icaoResponse);
		} catch (IOException e) {
			mapResult.put(KEY_ICAO_RESPONSE, new IcaoResponse());
		}
		mapResult.put(KEY_IS_PASS, isPass);
		return mapResult;
	}


	private CloseableHttpClient getHttpClient() {
		TrustManagerFactory trustManagerFactory;
		TrustManager[] trustAllCerts = null;
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(cm);

		try {
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init((KeyStore) null);
			trustAllCerts = trustManagerFactory.getTrustManagers();

			HostnameVerifier hostnameVerifier = new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					LOGGER.info("SSLConnectionSocketFactory: {}", hostname);
					return true;
				}

			};

			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc, new String[] { "TLSv1.2" }, null,
					hostnameVerifier);

			RequestConfig rc = RequestConfig.custom().setAuthenticationEnabled(true)
					.setConnectTimeout(readTimeout * 1000).setConnectionRequestTimeout(readTimeout * 1000)
					.setSocketTimeout(readTimeout * 1000).build();

			builder.setDefaultRequestConfig(rc);
			builder.setSSLContext(sc);
			builder.setSSLSocketFactory(sslsf);
			builder.setSSLHostnameVerifier(hostnameVerifier);

		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			LOGGER.error("{}", e.getMessage());
		}

		String messageId = UUID.randomUUID().toString();

		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, key));
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, IcaoConstants.APPLICATION_JSON));
		headers.add(new BasicHeader(IcaoConstants.MESSAGE_ID, messageId));

		return builder.build();

	}
}