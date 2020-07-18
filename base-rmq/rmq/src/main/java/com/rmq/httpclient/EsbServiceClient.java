/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.rmq.httpclient;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 3, 2016
 */
public class EsbServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsbServiceClient.class);

	private static EsbServiceClient instance = null;

	private static EsbRestTemplate restTemplate;

	private static Properties prop;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;
	
	private int readTimeout;

	private EsbServiceClient() {}
	
	public EsbServiceClient(String url) {
		this.url = url;
		initialize();
	}
	
	public EsbServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}

	public EsbServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}

	private static EsbServiceClient getInstance() {
		if (instance == null) {
			instance = new EsbServiceClient();
		}

		return instance;
	}

	private void initialize() {
		restTemplate = new EsbRestTemplate();
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
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public static void setRestTemplate(EsbRestTemplate restTemplate) {
		EsbServiceClient.restTemplate = restTemplate;
	}
	
	private EsbRestTemplate getRestTemplate() throws Exception {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new EsbException(EsbErrorCodeEnum.E400ESB001);
		}
		if (authToken != null) {
			httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
		} else {
			httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}

	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.info("Service Rest URL: " + uri);
		return uri;
	}
	
	@SuppressWarnings("rawtypes")
	private String getServiceURI(String serviceName, Object param){	
		if(serviceName.contains("${rest.default.url}")) serviceName = serviceName.replace("${rest.default.url}", prop.getProperty("rest.default.url"));
		if(serviceName.contains("${version}")) serviceName = serviceName.replace("${version}", prop.getProperty("version"));
		String uri = url + serviceName;
		LOGGER.info("Service Rest URL: " + uri);
		for(Field f : param.getClass().getDeclaredFields()){
			 try {
				 f.setAccessible(true);
				 Object obj = f.get(param);
				 if(!BaseUtil.isObjNull(obj)) {
					 if(String.class == f.getType()) {
						 uri = uri + "&" + f.getName() + "=" + (String) obj;
					 } else if(Integer.class == f.getType()) {
						 uri = uri + "&" + f.getName() + "=" + obj;
					 } else if(Long.class == f.getType()) {
						 uri = uri + "&" + f.getName() + "=" + obj;
					 } else if(Date.class == f.getType()) {
						 uri = uri + "&" + f.getName() + "=" + obj;
					 } else if(List.class == f.getType()) {
						 List lst = (List) obj;
						 String str = "";
						 for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
							Object object = (Object) iterator.next();
							str = str + "," + object;
						 }
						 uri = uri + "&" + f.getName() + "=" + str.substring(1, str.length());
					 }
				 }
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				continue;
			}
		}
		return uri;
	}
	
	@SuppressWarnings("rawtypes")
	private String getServiceURI(String serviceName, Set<Map.Entry<String, Object>> reqParams) {
		if (serviceName.contains("${prefix}")) serviceName = serviceName.replace("${prefix}", prop.getProperty("prefix"));
		if (serviceName.contains("${version}")) serviceName = serviceName.replace("${version}", prop.getProperty("version"));
		StringBuffer sb = new StringBuffer();
		sb.append(url + serviceName);
		
		if(!BaseUtil.isObjNull(reqParams)) {
			sb.append("?1=1");
			for(Map.Entry<String, Object> entry : reqParams) {
				if(!BaseUtil.isObjNull(entry.getValue())) {
					LOGGER.info("PARAMS: {} - {}", entry.getKey(), entry.getValue() );
					if(entry.getValue() instanceof List) {
						List lst = (List) entry.getValue();
						String str = "";
						for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
							Object object = (Object) iterator.next();
							str = str + "," + object;
						}
						sb.append("&" + entry.getKey() + "=" + str.substring(1, str.length()));
					} else {
						sb.append("&" + entry.getKey() + "=" + entry.getValue());
					}
				}
			}
		}
		
		LOGGER.info("Service Rest URL: " + sb.toString());
		return sb.toString();
	}
	
	public Object getForObject(String url) throws Exception {
		return getRestTemplate().getForObject(getServiceURI(url));
	}
	
	public Object getForObject(String url, Object param) throws Exception {
		return getRestTemplate().getForObject(getServiceURI(url, param));
	}
	
	public Object getForObject(String url, Set<Map.Entry<String, Object>> reqParams) throws Exception {
		return getRestTemplate().getForObject(getServiceURI(url, reqParams));
	}
	
	public Object getForObject(String url, Map<String, Object> urlVariables) throws Exception {
		return getRestTemplate().getForObject(getServiceURI(url), urlVariables);
	}
	
	public Object getForObject(String url, Set<Map.Entry<String, Object>> reqParams, Map<String, Object> urlVariables) throws Exception {
		return getRestTemplate().getForObject(getServiceURI(url, reqParams), urlVariables);
	}
	
	public Object postForObject(String url, Object requestBody) throws Exception {
		return getRestTemplate().postForObject(getServiceURI(url), requestBody);
    }
	
	public Object postForObject(String url, Set<Map.Entry<String, Object>> reqParams, Object requestBody) throws Exception {
		return getRestTemplate().postForObject(getServiceURI(url, reqParams), requestBody);
    }
    
    public Object postForObject(String url, Object requestBody, Map<String, Object> uriVariables) throws Exception {
    	return getRestTemplate().postForObject(getServiceURI(url), requestBody, uriVariables);
    }
    
    public Object postForObject(String url, Set<Map.Entry<String, Object>> reqParams, Object requestBody, Map<String, Object> uriVariables) throws Exception {
    	return getRestTemplate().postForObject(getServiceURI(url, reqParams), requestBody, uriVariables);
    }
    
    public boolean deleteForObject(String url) throws Exception {
    	return getRestTemplate().deleteForObject(getServiceURI(url));
    }
    
    public boolean deleteForObject(String url, Map<String, Object> uriVariables) throws Exception {
    	return getRestTemplate().deleteForObject(getServiceURI(url), uriVariables);
    }
	
    public Object putForObject(String url, Map<String, Object> uriVariables) throws Exception {
    	return getRestTemplate().putForObject(getServiceURI(url), uriVariables);
    }

    public Object putForObject(String url, Object requestBody, Map<String, Object> uriVariables) throws Exception {
    	return getRestTemplate().putForObject(getServiceURI(url), requestBody, uriVariables);
    }
    
}