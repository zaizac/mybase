package com.report.sdk.client;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.report.sdk.builder.ReportService;
import com.report.sdk.constants.ReportErrorCodeEnum;
import com.report.sdk.constants.ReportUrlConstants;
import com.report.sdk.exception.ReportException;
import com.report.sdk.model.ReportSyncFussion;
import com.report.sdk.model.ServiceCheck;
import com.util.BaseUtil;
import com.util.http.HttpAuthClient;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ReportServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceClient.class);

	private static ReportRestTemplate restTemplate;

	private static Properties prop;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;
	
	private String portalType;

	private int readTimeout;


	public ReportServiceClient(String url) {
		this.url = url;
		initialize();
	}


	public ReportServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public ReportServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	private void initialize() {
		restTemplate = new ReportRestTemplate();
	}


	private ReportRestTemplate getRestTemplate() throws Exception {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new ReportException(ReportErrorCodeEnum.E400RPT001);
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
		LOGGER.info("Service Rest URL: {}", uri);
		return uri;
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
	

	public String checkConnection() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(ReportUrlConstants.SERVICE_CHECK + "/test"),
				String.class);
	}


	public ServiceCheck serviceTest() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(ReportUrlConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	public boolean evict() throws Exception {
		return evict(null);
	}


	public boolean evict(String prefixKey) throws Exception {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(ReportUrlConstants.REPORT_CACHE_EVICT);
		if (!BaseUtil.isObjNull(prefixKey)) {
			sbUrl.append("?prefixKey=" + prefixKey);
		}
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	public ReportService report() throws Exception {
		return new ReportService(getRestTemplate(), prop, url);
	}


	public ReportRestTemplate restTemplate() {
		return restTemplate;
	}
	
	/**
	 * Backward compatibility for existing call to generateReport(reportName, params) 
	 * @param reportName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public byte[] generateReport(String reportName, Map<String, Object> params) throws Exception {
		return generateReport(reportName, "pdf" , params);
	}


	/**
	 * 
	 * @param reportName
	 * @param reportType - pdf etc
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public byte[] generateReport(String reportName, String reportType, Map<String, Object> params) throws Exception {
		StringBuilder sbUrl = new StringBuilder();
		if (BaseUtil.isStringNull(reportName)) {
			throw new ReportException("Empty report name");
		} else {
			// format report name
			reportName = URLEncoder.encode(reportName, StandardCharsets.UTF_8.toString());
			sbUrl.append("?reportName=").append(reportName);
		}
		
		if (!BaseUtil.isObjNull(reportType)) {
			sbUrl.append("&reportType=").append(URLEncoder.encode(reportType, StandardCharsets.UTF_8.toString()));
		}

		String json = "";
		String encodedJson = "";

		if (!params.isEmpty()) {
			Gson gson = new GsonBuilder().create();
			json = gson.toJson(params);
			encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString());
			sbUrl.append("&Param=").append(encodedJson);
		}

		ReportSyncFussion rsf = new ReportSyncFussion();
		rsf.setReportName(reportName);
		rsf.setReportType(reportType);
		rsf.setParam(encodedJson);

		Map<String, Object> newparams = new HashMap<>();
		newparams.put("reportName", reportName);
		newparams.put("reportType", reportType);
		newparams.put("Param", encodedJson);

		return getRestTemplate().postForByteArray(getServiceURI(sbUrl.toString()), null, newparams);
	}

}