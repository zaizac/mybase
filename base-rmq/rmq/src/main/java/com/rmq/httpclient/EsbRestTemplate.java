/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.rmq.httpclient;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.UriUtil;
import com.util.model.ErrorResponse;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 3, 2016
 */
public class EsbRestTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsbRestTemplate.class);
	
	CloseableHttpClient httpClient;
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
    public EsbRestTemplate() {
    	httpClient = HttpClients.createDefault();
    }

    public EsbRestTemplate(CloseableHttpClient httpClient) {
    	this.httpClient = httpClient;
    }

    public Object getForObject(String url) throws Exception {
    	return getForObject(url, null);
    }
    
    public Object getForObject(String url, Map<String, Object> urlVariables) throws Exception {
    	Asserts.notEmpty(url, "'url' must not be null");
    	url = UriUtil.expandUriComponent(url, urlVariables);
    	StringBuffer sbLog = new StringBuffer();
    	sbLog.append("ESB GET Request:: " + url);
    	try {
	        HttpGet httpGet = new HttpGet(url);
	        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
	        sbLog.append("\nESB GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
	 
	        if (httpResponse.getStatusLine().getStatusCode() != 200) {
	        	String result = EntityUtils.toString(httpResponse.getEntity());
	        	ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
	        	sbLog.append("\nERROR: ESB GET Exception:: " + er.getErrorMessage() + " [" + er.getErrorCode() + "]");
	    		throw new EsbException(er.getErrorCode(), er.getErrorMessage());
			}
	        
//	        String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
//	        JsonNode jnode = new ObjectMapper().readTree(result);
//	        if(jnode.isArray()) {
//	        	List<String> str = new ArrayList<String>();
//	        	for(JsonNode node : jnode) {
//	        		str.add(new ObjectMapper().writeValueAsString(node));
//	        	}
//	        	return str;
//	        } 
//	        return new ObjectMapper().writeValueAsString(result);
//	        JsonElement je = JsonParser.parseString(EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
//	        return je.getAsJsonObject();
	        return httpResponse.getEntity();
    	} catch (SocketException e) {
    		sbLog.append("\nERROR: ESB POST SocketException:: " + e.getMessage());
    		throw e;
    	} catch (IOException ex) {
    		sbLog.append("\nERROR: ESB GET IOException:: " + ex.getMessage());
    		throw new EsbException(EsbErrorCodeEnum.E404ESB005, new Object[]{url});  
    	} catch (Exception ex) {
    		sbLog.append("\nERROR: ESB GET Exception:: " + ex.getMessage());
    		throw ex; 
	    } finally {
			//httpClient.close();
			LOGGER.info(sbLog.toString());
		}
	}
    
    public Object postForObject(String url, Object requestBody) throws Exception {
    	return postForObject(url, requestBody, null);
    }
    
    public Object postForObject(String url, Object requestBody, Map<String, Object> uriVariables) throws Exception {
    	Asserts.notEmpty(url, "'url' must not be null");
    	url = UriUtil.expandUriComponent(url, uriVariables);
    	StringBuffer sbLog = new StringBuffer();
    	sbLog.append("ESB POST Request:: " + url);
    	try {
    		HttpPost httpPost = new HttpPost(url);
    		
    		if(!BaseUtil.isObjNull(requestBody)) {
    			ObjectMapper mapper = new ObjectMapper();
    			JsonNode node = mapper.readTree(BaseUtil.getStr(requestBody)); //mapper.convertValue(requestBody, JsonNode.class);
    			sbLog.append("\nESB POST Request Body:: " +node.toString());
    			StringEntity input = new StringEntity(node.toString());
    			input.setContentType(MediaType.APPLICATION_JSON);
    			httpPost.setEntity(input);
    		}
    		
    		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
    		sbLog.append("\nESB POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());
    		
    		if (httpResponse.getStatusLine().getStatusCode() != 200) {
    			String result = EntityUtils.toString(httpResponse.getEntity());
    			ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
    			sbLog.append("\nERROR: ESB GET Exception:: " + er.getErrorMessage() + " [" + er.getErrorCode() + "]");
    			throw new EsbException(er.getErrorCode(), er.getErrorMessage());
    		}
    		
    		String result = EntityUtils.toString(httpResponse.getEntity());
    		JsonNode jnode = new ObjectMapper().readTree(result);
	        if(jnode.isArray()) {
	        	List<String> str = new ArrayList<String>();
	        	for(JsonNode node : jnode) {
	        		str.add(new ObjectMapper().writeValueAsString(node));
	        	}
	        	return str;
	        } 
	        return new ObjectMapper().writeValueAsString(jnode);
    	} catch (SocketException e) {
    		sbLog.append("\nERROR: ESB POST SocketException:: " + e.getMessage());
    		throw e;	
    	} catch (IOException ex) {
    		sbLog.append("\nERROR: ESB POST IOException:: " + ex.getMessage());
    		throw new EsbException(EsbErrorCodeEnum.E404ESB005, new Object[]{url});
    	} catch (Exception ex) {
    		sbLog.append("\nERROR: ESB POST Exception:: " + ex.getMessage());
    		throw ex; 
    	} finally {
    		//httpClient.close();
    		LOGGER.info(sbLog.toString());
    	}
	}
    
    public boolean deleteForObject(String url) throws Exception {
    	return deleteForObject(url, null);
    }
    
    public boolean deleteForObject(String url, Map<String, Object> uriVariables) throws Exception {
    	Asserts.notEmpty(url, "'url' must not be null");
    	StringBuffer sbLog = new StringBuffer();
    	sbLog.append("ESB DELETE Request:: " + url);
    	boolean isDel = false;
    	try {
    		HttpDelete httpDel = new HttpDelete(url);
    		CloseableHttpResponse httpResponse = httpClient.execute(httpDel);
    		sbLog.append("\nESB DELETE Response Status:: " + httpResponse.getStatusLine().getStatusCode());
    	} catch (SocketException e) {
    		sbLog.append("\nERROR: ESB POST SocketException:: " + e.getMessage());
    		throw e;
    	} catch (IOException ex) {
    		sbLog.append("\nERROR: ESB DELETE IOException:: " + ex.getMessage());
    		throw new EsbException(EsbErrorCodeEnum.E404ESB005, new Object[]{url});
    	} finally {
    		//httpClient.close();
    		LOGGER.info(sbLog.toString());
    	}
    	return isDel;
    }
    
    public Object putForObject(String url, Map<String, Object> uriVariables) throws Exception {
    	return postForObject(url, null, uriVariables);
    }

    public Object putForObject(String url, Object requestBody, Map<String, Object> uriVariables) throws Exception {
    	Asserts.notEmpty(url, "'url' must not be null");
    	url = UriUtil.expandUriComponent(url, uriVariables);
    	StringBuffer sbLog = new StringBuffer();
    	sbLog.append("ESB PUT Request:: " + url);
    	try {
    		HttpPut httpPut = new HttpPut(url);
    		if(!BaseUtil.isObjNull(requestBody)) {
    			ObjectMapper mapper = new ObjectMapper();
    			JsonNode node = mapper.convertValue(requestBody, JsonNode.class);
    			sbLog.append("\nESB PUT Request Body:: " + node.toString());
    			StringEntity input = new StringEntity(node.toString());
    			input.setContentType(MediaType.APPLICATION_JSON);
    			httpPut.setEntity(input);
    		}
    		
    		CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
    		sbLog.append("\nESB PUT Response Status:: " + httpResponse.getStatusLine().getStatusCode());
    		
    		if (httpResponse.getStatusLine().getStatusCode() != 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				ErrorResponse er = new ObjectMapper().readValue(result, ErrorResponse.class);
				sbLog.append("\nERROR: ESB GET Exception:: " + er.getErrorMessage() + " [" + er.getErrorCode() + "]");
				throw new EsbException(er.getErrorCode(), er.getErrorMessage());
    		}
    		
    		String result = EntityUtils.toString(httpResponse.getEntity());
    		JsonNode jnode = new ObjectMapper().readTree(result);
	        if(jnode.isArray()) {
	        	List<String> str = new ArrayList<String>();
	        	for(JsonNode node : jnode) {
	        		str.add(new ObjectMapper().writeValueAsString(node));
	        	}
	        	return str;
	        } 
	        return new ObjectMapper().writeValueAsString(jnode);
    	} catch (SocketException e) {
    		sbLog.append("\nERROR: ESB POST SocketException:: " + e.getMessage());
    		throw e;
    	} catch (IOException ex) {
    		sbLog.append("\nERROR: ESB PUT IOException:: " + ex.getMessage());
    		throw new EsbException(EsbErrorCodeEnum.E404ESB005, new Object[]{url});
    	} finally {
    		//httpClient.close();
    		LOGGER.info(sbLog.toString());
    	}
	}	

}