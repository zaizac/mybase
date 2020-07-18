/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.client;


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

import com.baseboot.report.sdk.constants.ReportErrorCodeEnum;
import com.baseboot.report.sdk.exception.ReportException;
import com.baseboot.report.sdk.model.ErrorResponse;
import com.baseboot.report.sdk.util.BaseUtil;
import com.baseboot.report.sdk.util.MediaType;
import com.baseboot.report.sdk.util.UriUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ReportRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportRestTemplate.class);

	CloseableHttpClient httpClient;


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public ReportRestTemplate() {
		httpClient = HttpClients.createDefault();
	}


	public ReportRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) throws Exception {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) throws Exception {
		Asserts.notEmpty(url, "'url' must not be null");
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
				throw new ReportException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (IOException ex) {
			throw new ReportException(ReportErrorCodeEnum.E503RPT000, new Object[] { url });
		}
		return (T) p;
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) throws Exception {
		return postForObject(url, requestBody, responseType, null);
	}


	public <T> T postForObject(String url, Object requestBody, Class<T> responseType, Map<String, Object> uriVariables)
			throws Exception {
		Asserts.notEmpty(url, "'url' must not be null");
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("RPT POST Request: {}", url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("RPT POST Request Body: {}", node.toString());
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("RPT POST Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new ReportException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException ex) {
			throw new ReportException(ReportErrorCodeEnum.E503RPT000, new Object[] { url });
		}
		return (T) p;
	}


	public boolean deleteForObject(String url) throws Exception {
		return deleteForObject(url, null);
	}


	public boolean deleteForObject(String url, Map<String, Object> uriVariables) throws Exception {
		Asserts.notEmpty(url, "'url' must not be null");
		LOGGER.info("RPT DELETE Request: {}", url);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("RPT DELETE Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (IOException ex) {
			throw new ReportException(ReportErrorCodeEnum.E503RPT000, new Object[] { url });
		}
		return isDel;
	}


	public <T> T putForObject(String url, Map<String, Object> uriVariables) throws Exception {
		return postForObject(url, null, null, uriVariables);
	}


	public <T> T putForObject(String url, Object requestBody, Class<T> responseType, Map<String, Object> uriVariables)
			throws Exception {
		Asserts.notEmpty(url, "'url' must not be null");
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("RPT PUT Request: {}", url);
		T p = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				LOGGER.info("RPT PUT Request Body: {}", node.toString());
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
			LOGGER.info("RPT PUT Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				try {
					String result = EntityUtils.toString(httpResponse.getEntity());
					ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
					throw new ReportException(er.getErrorCode(), er.getErrorMessage());
				} catch (Exception e) {
					throw new ReportException(ReportErrorCodeEnum.E503RPT000, new Object[] { url });
				}
			}

			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException ex) {
			throw new ReportException(ReportErrorCodeEnum.E503RPT000, new Object[] { url });
		}
		return (T) p;
	}

}