/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.client;


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
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.UriUtil;
import com.util.model.ErrorResponse;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.exception.WfwException;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 10, 2015
 */
public class WfwRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwRestTemplate.class);

	private static final String URL_MUST_NOT_NULL_TEXT = "'url' must not be null";

	CloseableHttpClient httpClient;


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public WfwRestTemplate() {
		httpClient = HttpClients.createDefault();
	}


	public WfwRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) {
		Asserts.notEmpty(url, URL_MUST_NOT_NULL_TEXT);
		url = UriUtil.expandUriComponent(url, urlVariables);
		LOGGER.info("RPT GET Request: {}", url);
		T p = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			LOGGER.info("RPT GET Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new WfwException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (IOException ex) {
			throw new WfwException(WfwErrorCodeEnum.E503WFW000, new Object[] { url });
		}
		return p;
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
		return postForObject(url, requestBody, responseType, null);
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, URL_MUST_NOT_NULL_TEXT);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("RPT POST Request: {}", url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("RPT POST Request Body: {}", node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("RPT POST Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new WfwException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException ex) {
			throw new WfwException(WfwErrorCodeEnum.E503WFW000, new Object[] { url });
		}
		return p;
	}


	public boolean deleteForObject(String url) {
		return deleteForObject(url, null);
	}


	public boolean deleteForObject(String url, Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, URL_MUST_NOT_NULL_TEXT);
		LOGGER.info("RPT DELETE Request: {} : variables : {}", url, uriVariables);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("RPT DELETE Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (IOException ex) {
			throw new WfwException(WfwErrorCodeEnum.E503WFW000, new Object[] { url });
		}
		return isDel;
	}


	public <T> T putForObject(String url, Map<String, Object> uriVariables) {
		return postForObject(url, null, null, uriVariables);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType,
			Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, URL_MUST_NOT_NULL_TEXT);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("RPT PUT Request: {}", url);
		T p = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("RPT PUT Request Body: {}", node);
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
			LOGGER.info("RPT PUT Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				retryGetEntityUtils(httpResponse, url);
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException ex) {
			throw new WfwException(WfwErrorCodeEnum.E503WFW000, new Object[] { url });
		}
		return p;
	}


	private void retryGetEntityUtils(CloseableHttpResponse httpResponse, String url) {
		try {
			String result = EntityUtils.toString(httpResponse.getEntity());
			ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
			throw new WfwException(er.getErrorCode(), er.getErrorMessage());
		} catch (Exception e) {
			throw new WfwException(WfwErrorCodeEnum.E503WFW000, new Object[] { url });
		}
	}

}
