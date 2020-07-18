/**
 * Copyright 2019
 */
package com.be.sdk.client;

import java.io.IOException;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.fasterxml.jackson.databind.JsonNode;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.UriUtil;
import com.util.model.MessageResponse;

/**
 * @author Mary Jane Buenaventura
 * @since Sep 15, 2015
 */
public class BeRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeRestTemplate.class);

	private static final String ASSERT_NOT_NULL = "'url' must not be null";

	CloseableHttpClient httpClient;

	String sysPrefix;


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public BeRestTemplate(String sysPrefix) {
		this.sysPrefix = sysPrefix;
		httpClient = HttpClients.createDefault();
	}


	public BeRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, urlVariables);
		LOGGER.info("{} GET Request: {}", sysPrefix, url);
		T p = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			LOGGER.info("{} GET Response Status: {}", sysPrefix, httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				MessageResponse er = JsonUtil.objectMapper().readValue(result, MessageResponse.class);
				throw new BeException(er.getCode(), er.getMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (IOException ex) {
			LOGGER.error("{}", ex);
			throw new BeException(BeErrorCodeEnum.E503C000, new Object[] { url });
		}
		return p;
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
		return postForObject(url, requestBody, responseType, null);
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("{} POST Request: {}", sysPrefix, url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				JsonNode node = JsonUtil.objectMapper().convertValue(requestBody, JsonNode.class);
				LOGGER.info("{} POST Request Body: {}", sysPrefix, node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("{} POST Response Status: {}", sysPrefix, httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				LOGGER.info("{} POST Response Error: {}", sysPrefix, result);
				if (!BaseUtil.isObjNull(result)) {
					MessageResponse er = JsonUtil.objectMapper().readValue(result, MessageResponse.class);
					throw new BeException(er.getCode(), er.getMessage());
				}
				throw new BeException(String.valueOf(httpResponse.getStatusLine().getStatusCode()),
						httpResponse.getStatusLine().getReasonPhrase());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (IOException ex) {
			LOGGER.error("{}", ex);
			throw new BeException(BeErrorCodeEnum.E503C000, new Object[] { url });
		} catch (Exception ex) {
			throw ex;
		}
		return p;
	}


	public boolean deleteForObject(String url) {
		return deleteForObject(url, null);
	}


	public boolean deleteForObject(String url, Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("{} DELETE Request: {}", sysPrefix, url);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("{} DELETE Response Status: {}", sysPrefix, httpResponse.getStatusLine().getStatusCode());
		} catch (IOException ex) {
			throw new BeException(BeErrorCodeEnum.E503C000, new Object[] { url });
		}
		return isDel;
	}


	public <T> T putForObject(String url, Map<String, Object> uriVariables) {
		return postForObject(url, null, null, uriVariables);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType) {
		return putForObject(url, requestBody, responseType, null);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("{} PUT Request: {}", sysPrefix, url);
		T p = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			if (!BaseUtil.isObjNull(requestBody)) {
				JsonNode node = JsonUtil.objectMapper().convertValue(requestBody, JsonNode.class);
				LOGGER.info("{} PUT Request Body: {}", sysPrefix, node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
			LOGGER.info("{} PUT Response Status: {}", sysPrefix, httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				MessageResponse er = JsonUtil.objectMapper().readValue(result, MessageResponse.class);
				throw new BeException(er.getCode(), er.getMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (ParseException | IOException e) {
			LOGGER.error(e.getMessage());
			throw new BeException(BeErrorCodeEnum.E503C000, new Object[] { url });
		}
		return p;
	}

}