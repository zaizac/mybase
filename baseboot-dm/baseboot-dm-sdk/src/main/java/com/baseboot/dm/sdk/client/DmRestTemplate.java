/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.client;


import java.io.IOException;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.dm.sdk.client.constants.DmErrorCodeEnum;
import com.baseboot.dm.sdk.exception.DmException;
import com.baseboot.dm.sdk.model.ErrorResponse;
import com.baseboot.dm.sdk.util.BaseUtil;
import com.baseboot.dm.sdk.util.MediaType;
import com.baseboot.dm.sdk.util.UriUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DmRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(DmRestTemplate.class);

	CloseableHttpClient httpClient;


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public DmRestTemplate() {
		httpClient = HttpClients.createDefault();
	}


	public DmRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) throws DmException {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) throws DmException {
		Asserts.notEmpty(url, "'url' must not be null");
		url = UriUtil.expandUriComponent(url, urlVariables);
		LOGGER.info("DM GET Request: {}", url);
		T p = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			LOGGER.info("DM GET Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new DmException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (HttpHostConnectException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		} catch (IOException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		}
		return (T) p;
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) throws DmException {
		return postForObject(url, requestBody, responseType, null);
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType, Map<String, Object> uriVariables)
			throws DmException {
		Asserts.notEmpty(url, "'url' must not be null");
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("DM POST Request: {}", url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("DM POST Request Body: {}", node.toString());
				StringEntity input = new StringEntity(node.toString());
				input.setContentType("application/json");
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("DM POST Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new DmException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (HttpHostConnectException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		} catch (IOException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		}
		return (T) p;
	}


	public boolean deleteForObject(String url) throws DmException {
		return deleteForObject(url, null);
	}


	public boolean deleteForObject(String url, Map<String, Object> uriVariables) throws DmException {
		Asserts.notEmpty(url, "'url' must not be null");
		LOGGER.info("DM DELETE Request: {}", url);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("DM DELETE Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (HttpHostConnectException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		} catch (IOException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		}
		return isDel;
	}


	public <T> T putForObject(String url, Map<String, Object> uriVariables) throws Exception {
		return postForObject(url, null, null, uriVariables);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType, Map<String, Object> uriVariables)
			throws DmException {
		Asserts.notEmpty(url, "'url' must not be null");
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("DM PUT Request: {}", url);
		T p = null;
		try {
			HttpPut httpPut = new HttpPut(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("DM PUT Request Body: {}", node.toString());
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
			LOGGER.info("DM PUT Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				try {
					String result = EntityUtils.toString(httpResponse.getEntity());
					ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
					throw new DmException(er.getErrorCode(), er.getErrorMessage());
				} catch (Exception e) {
					throw new DmException(DmErrorCodeEnum.E503DOM000.name(),
							DmErrorCodeEnum.E503DOM000.getMessage());
				}
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (HttpHostConnectException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		} catch (IOException ex) {
			throw new DmException(DmErrorCodeEnum.E503DOM000, new Object[] { url });
		}
		return (T) p;
	}

}