/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.sdk.client;


import java.io.IOException;
import java.util.Map;

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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.sgw.sdk.exception.SgwException;
import com.sgw.sdk.model.MessageResponse;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.UriUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(SgwRestTemplate.class);

	CloseableHttpClient httpClient;

	private static final String URL_NULL_MESSAGE = "'url' must not be null";

	private static final String IOEXCEPTION = "IOException: {}";


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public SgwRestTemplate() {
		httpClient = HttpClients.createDefault();
	}


	public SgwRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) {
		Asserts.notEmpty(url, URL_NULL_MESSAGE);
		url = UriUtil.expandUriComponent(url, urlVariables);
		LOGGER.info("SGW GET Request:: {}", url);
		T p = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			LOGGER.info("SGW GET Response Status:: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				MessageResponse er = new ObjectMapper().readValue(result, MessageResponse.class);
				throw new SgwException(er.getCode(), er.getMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (IOException e) {
			LOGGER.error(IOEXCEPTION, e.getMessage());
			throw new SgwException(SgwErrorCodeEnum.E503SGW000, new Object[] { url });
		}
		return p;
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
		return postForObject(url, requestBody, responseType, null);
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, URL_NULL_MESSAGE);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("SGW POST Request: {}", url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("SGW POST Request Body: {}", node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("SGW POST Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				MessageResponse er = new ObjectMapper().readValue(result, MessageResponse.class);
				throw new SgwException(er.getCode(), er.getMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException e) {
			LOGGER.error(IOEXCEPTION, e.getMessage());
			throw new SgwException(SgwErrorCodeEnum.E503SGW000, new Object[] { url });
		}
		return p;
	}


	public boolean deleteForObject(String url) {
		Asserts.notEmpty(url, URL_NULL_MESSAGE);
		LOGGER.info("SGW DELETE Request: {}", url);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("SGW DELETE Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (IOException e) {
			LOGGER.error(IOEXCEPTION, e.getMessage());
			throw new SgwException(SgwErrorCodeEnum.E503SGW000, new Object[] { url });
		}
		return isDel;
	}


	public <T> T putForObject(String url, Map<String, Object> uriVariables) {
		return postForObject(url, null, null, uriVariables);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, URL_NULL_MESSAGE);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("SGW PUT Request: {}", url);
		T p = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("SGW PUT Request Body: {}", node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
			LOGGER.info("SGW PUT Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				mapHttpResponse(httpResponse);
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException e) {
			LOGGER.error(IOEXCEPTION, e.getMessage());
			throw new SgwException(SgwErrorCodeEnum.E503SGW000, new Object[] { url });
		}
		return p;
	}


	private void mapHttpResponse(CloseableHttpResponse httpResponse) {
		try {
			String result = EntityUtils.toString(httpResponse.getEntity());
			MessageResponse er = new ObjectMapper().readValue(result, MessageResponse.class);
			throw new SgwException(er.getCode(), er.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new SgwException(SgwErrorCodeEnum.E503SGW000.name(), SgwErrorCodeEnum.E503SGW000.getMessage());
		}
	}
}