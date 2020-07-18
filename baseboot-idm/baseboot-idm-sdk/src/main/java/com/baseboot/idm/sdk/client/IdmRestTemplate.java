/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.client;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.ErrorResponse;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.sdk.util.UriUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmRestTemplate.class);

	private static final String ASSERT_NOT_NULL = "'url' must not be null";

	CloseableHttpClient httpClient;


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public IdmRestTemplate() {
		httpClient = HttpClients.createDefault();
	}


	public IdmRestTemplate(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public <T> T getForObject(String url, Class<T> responseType) {
		return getForObject(url, responseType, null);
	}


	public <T> T getForObject(String url, Class<T> responseType, Map<String, Object> urlVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, urlVariables);
		LOGGER.info("IDM GET Request: {}", url);
		T p = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			LOGGER.info("IDM GET Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new IdmException(er.getErrorCode(), er.getErrorMessage());
			}

			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			if (!BaseUtil.isObjNull(result)) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_DEFAULT);
				p = mapper.readValue(result, responseType);
			}
		} catch (IOException ex) {
			throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
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
		LOGGER.info("IDM POST Request: {}", url);
		T p = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			if (!BaseUtil.isObjNull(requestBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("IDM POST Request Body: {}", node);
				}
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPost.setEntity(input);
			}

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			LOGGER.info("IDM POST Response Status: {}", httpResponse.getStatusLine().getStatusCode());

			String result = EntityUtils.toString(httpResponse.getEntity());

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				throw new IdmException(er.getErrorCode(), er.getErrorMessage());
			}

			p = new ObjectMapper().readValue(result, responseType);
		} catch (IOException ex) {
			throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
		}
		return p;
	}


	public boolean deleteForObject(String url) {
		return deleteForObject(url, null);
	}


	public boolean deleteForObject(String url, Map<String, Object> uriVariables) {
		Asserts.notEmpty(url, ASSERT_NOT_NULL);
		url = UriUtil.expandUriComponent(url, uriVariables);
		LOGGER.info("IDM DELETE Request: {}", url);
		boolean isDel = false;
		try {
			HttpDelete httpDel = new HttpDelete(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
			LOGGER.info("IDM DELETE Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (IOException ex) {
			throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
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
		LOGGER.info("IDM PUT Request: {}", url);
		T p = null;
		HttpPut httpPut = new HttpPut(url);
		if (!BaseUtil.isObjNull(requestBody)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("IDM PUT Request Body: {}", node);
			}
			try {
				StringEntity input = new StringEntity(node.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				httpPut.setEntity(input);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage());
			}
		}

		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPut);
			LOGGER.info("IDM PUT Response Status: {}", httpResponse.getStatusLine().getStatusCode());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
		}

		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			ErrorResponse er = null;
			try {
				String result = EntityUtils.toString(httpResponse.getEntity());
				er = new ObjectMapper().readValue(result, ErrorResponse.class);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
			}
			throw new IdmException(er.getErrorCode(), er.getErrorMessage());
		}

		try {
			String result = EntityUtils.toString(httpResponse.getEntity());
			p = new ObjectMapper().readValue(result, responseType);
		} catch (ParseException | IOException e) {
			LOGGER.error(e.getMessage());
			throw new IdmException(IdmErrorCodeEnum.E503IDM000, new Object[] { url });
		}
		return p;
	}

}