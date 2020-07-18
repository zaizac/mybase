/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.client;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.report.sdk.builder.ReportService;
import com.baseboot.report.sdk.constants.ReportErrorCodeEnum;
import com.baseboot.report.sdk.constants.ReportUrlConstants;
import com.baseboot.report.sdk.exception.ReportException;
import com.baseboot.report.sdk.model.Report;
import com.baseboot.report.sdk.model.ServiceCheck;
import com.baseboot.report.sdk.util.BaseUtil;


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

	private int readTimeout;


	private ReportServiceClient() {
		// Block Initialization
	}


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
			throw new ReportException(ReportErrorCodeEnum.E400RPT011);
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


	public String checkConnection() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(ReportUrlConstants.SERVICE_CHECK + "/test"),
				String.class);
	}


	public ServiceCheck serviceTest() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(ReportUrlConstants.SERVICE_CHECK),
				ServiceCheck.class);
	}


	public boolean evict() throws Exception {
		return evict(null);
	}


	public boolean evict(String prefixKey) throws Exception {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(ReportUrlConstants.REPORT_CACHE_EVICT);
		if (!BaseUtil.isObjNull(prefixKey))
			sbUrl.append("?prefixKey=" + prefixKey);
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	public ReportService report() throws Exception {
		return new ReportService(getRestTemplate(), prop, url);
	}
	
	public Report viewWalletReport(String dateFrom,String dateTo, Integer apsProfId) throws Exception {
		StringBuilder url = new StringBuilder();
		url.append(ReportUrlConstants.REPORT_WALLET);
		/*url.append("?type={type}");*/
		url.append("?dateFrom={dateFrom}");
		url.append("&dateTo={dateTo}");
		url.append("&apsProfId={apsProfId}");
	 	Map<String, Object> params = new HashMap<String, Object>();
		/*params.put("type", type);*/
		params.put("dateFrom", dateFrom);
		params.put("dateTo", dateTo);
		params.put("apsProfId", apsProfId);
		return (Report) getRestTemplate().getForObject(getServiceURI(url.toString()), Report.class, params);
	}

}