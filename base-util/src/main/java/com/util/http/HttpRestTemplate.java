/**
 * Copyright 2019. 
 */
package com.util.http;


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

import com.fasterxml.jackson.databind.JsonNode;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.UriUtil;
import com.util.constants.ResponseCodeEnum;
import com.util.http.exception.HttpBaseException;
import com.util.model.ErrorResponse;


/**
 * The Class HttpRestTemplate.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class HttpRestTemplate {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRestTemplate.class);

	/** The Constant ASSERT_NOT_NULL. */
	private static final String ASSERT_NOT_NULL = "'url' must not be null";

	/** The http client. */
	CloseableHttpClient httpClient;

	/** The sys prefix. */
	String sysPrefix;


	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	/**
	 * Sets the http client.
	 *
	 * @param httpClient the new http client
	 */
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	/**
	 * Instantiates a new http rest template.
	 *
	 * @param sysPrefix the sys prefix
	 */
	public HttpRestTemplate(String sysPrefix) {
		this.sysPrefix = sysPrefix;
		httpClient = HttpClients.createDefault();
	}


	/**
	 * Instantiates a new http rest template.
	 *
	 * @param httpClient the http client
	 */
	public HttpRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	/**
	 * Gets the for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param responseType the response type
	 * @return the for object
	 */
	public <T> T getForObject(String url, Class<T> responseType) {
		return getForObject(url, responseType, null);
	}


	/**
	 * Gets the for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param responseType the response type
	 * @param urlVariables the url variables
	 * @return the for object
	 */
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
				ErrorResponse er = JsonUtil.objectMapper().readValue(result, ErrorResponse.class);
				throw new HttpBaseException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (IOException ex) {
			LOGGER.error("{}", ex);
			throw new HttpBaseException(ResponseCodeEnum.E503C000, new Object[] { sysPrefix, url });
		}
		return p;
	}


	/**
	 * Post for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param requestBody the request body
	 * @param responseType the response type
	 * @return the t
	 */
	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
		return postForObject(url, requestBody, responseType, null);
	}


	/**
	 * Post for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param requestBody the request body
	 * @param responseType the response type
	 * @param uriVariables the uri variables
	 * @return the t
	 */
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
					ErrorResponse er = JsonUtil.objectMapper().readValue(result, ErrorResponse.class);
					throw new HttpBaseException(er.getErrorCode(), er.getErrorMessage());
				}
				throw new HttpBaseException(String.valueOf(httpResponse.getStatusLine().getStatusCode()),
						httpResponse.getStatusLine().getReasonPhrase());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (IOException ex) {
			LOGGER.error("{}", ex);
			throw new HttpBaseException(ResponseCodeEnum.E503C000, new Object[] { sysPrefix, url });
		} catch (Exception ex) {
			throw ex;
		}
		return p;
	}


	/**
	 * Delete for object.
	 *
	 * @param url the url
	 * @return true, if successful
	 */
	public boolean deleteForObject(String url) {
		return deleteForObject(url, null);
	}


	/**
	 * Delete for object.
	 *
	 * @param url the url
	 * @param uriVariables the uri variables
	 * @return true, if successful
	 */
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
			throw new HttpBaseException(ResponseCodeEnum.E503C000, new Object[] { sysPrefix, url });
		}
		return isDel;
	}


	/**
	 * Put for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param uriVariables the uri variables
	 * @return the t
	 */
	public <T> T putForObject(String url, Map<String, Object> uriVariables) {
		return postForObject(url, null, null, uriVariables);
	}


	/**
	 * Put for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param requestBody the request body
	 * @param responseType the response type
	 * @return the t
	 */
	public <T> T putForObject(String url, Object requestBody, Class<T> responseType) {
		return putForObject(url, requestBody, responseType, null);
	}


	/**
	 * Put for object.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param requestBody the request body
	 * @param responseType the response type
	 * @param uriVariables the uri variables
	 * @return the t
	 */
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
				ErrorResponse er = JsonUtil.objectMapper().readValue(result, ErrorResponse.class);
				throw new HttpBaseException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				p = JsonUtil.objectMapper().readValue(result, responseType);
			}
		} catch (ParseException | IOException e) {
			LOGGER.error(e.getMessage());
			throw new HttpBaseException(ResponseCodeEnum.E503C000, new Object[] { sysPrefix, url });
		}
		return p;
	}

}