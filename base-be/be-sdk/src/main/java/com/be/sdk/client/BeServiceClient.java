/**
 * Copyright 2019
 */
package com.be.sdk.client;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.be.sdk.builder.ReferenceBaseService;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.constants.BeUrlConstants;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.SignUp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.BaseUtil;
import com.util.UriUtil;
import com.util.http.HttpAuthClient;
import com.util.model.ServiceCheck;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public class BeServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeServiceClient.class);

	private BeRestTemplate restTemplate;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;
	
	private String portalType;

	private int readTimeout;

	public BeServiceClient(String url) {
		this.url = url;
		initialize();
	}


	public BeServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public BeServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	private void initialize() {
		restTemplate = new BeRestTemplate("BE");
	}


	private BeRestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new BeException(BeErrorCodeEnum.E400C001);
		}
		if (authToken != null) {
			if(portalType != null) {
				httpClient = new HttpAuthClient(authToken, messageId, readTimeout, portalType).getHttpClient();
			} else {
				httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
			}
		} else {
			if(portalType != null) {
				httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout, portalType).getHttpClient();
			} else {
				httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
			}
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.info("Service Rest URL: {} ", uri);
		return uri;
	}


	protected String getServiceURI(String serviceName, Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		JsonNode jnode = mapper.valueToTree(obj);
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		sb.append(getServiceURI(serviceName));

		if (!BaseUtil.isObjNull(obj)) {
			try {
				Map<String, Object> maps = mapper.readValue(jnode.toString(),
						new TypeReference<Map<String, Object>>() {
						});
				for (Map.Entry<String, Object> entry : maps.entrySet()) {
					String mKey = entry.getKey();
					Object mValue = entry.getValue();
					if (!BaseUtil.isObjNull(mValue) && !BaseUtil.isEquals(mKey, "serialVersionUID")) {
						if (isFirst) {
							sb.append("?");
							isFirst = false;
						}
						if (mValue instanceof String) {
							mValue = UriUtil.getVariableValueAsString(mValue);
						}
						sb.append(mKey + "=" + mValue + "&");
					}
				}
			} catch (JsonParseException e) {
				LOGGER.error("JsonParseException: {}", e.getMessage());
			} catch (JsonMappingException e) {
				LOGGER.error("JsonMappingException: {}", e.getMessage());
			} catch (IOException e) {
				LOGGER.error("IOException: {}", e.getMessage());
			}
		}
		return !isFirst ? (sb.toString()).substring(0, sb.length() - 1) : sb.toString();
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	
	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}
	

	public void setUrl(String url) {
		this.url = url;
	}


	public void setRestTemplate(BeRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	/**
	 * BE Service Connection Check
	 *
	 * @return
	 */
	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(BeUrlConstants.SERVICE_CHECK + "/test"), String.class);
	}


	/**
	 * BE Service Check
	 *
	 * @return
	 */
	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(BeUrlConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	/**
	 * Access Static References Service
	 *
	 * @return
	 */
	public ReferenceBaseService reference() {
		return new ReferenceBaseService(getRestTemplate(), url);
	}


	/**
	 * THIS IS FOR TEST ONLY
	 *
	 * @return
	 */
	public SignUp testSignUpByRefNo(String refNo) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(BeUrlConstants.SERVICE_CHECK + "/signUpByRefNo").append("/{refNo}");

		Map<String, Object> params = new HashMap<>();
		params.put("refNo", refNo);

		return getRestTemplate().getForObject(sbUrl.toString(), SignUp.class, params);
	}


	public SignUp testSignUp(SignUp signUp) {
		return getRestTemplate().postForObject(getServiceURI(BeUrlConstants.SERVICE_CHECK + "/signUp"), signUp,
				SignUp.class);
	}


	public SignUp testUpdateSignUp(SignUp signUp) {
		return getRestTemplate().postForObject(getServiceURI(BeUrlConstants.SERVICE_CHECK + "/updateSignUp"), signUp,
				SignUp.class);
	}

}