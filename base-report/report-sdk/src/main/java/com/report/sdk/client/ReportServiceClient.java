/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.sdk.client;


import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.report.sdk.builder.ReportService;
import com.report.sdk.constants.ReportConstants;
import com.report.sdk.constants.ReportErrorCodeEnum;
import com.report.sdk.constants.ReportUrlConstants;
import com.report.sdk.exception.ReportException;
import com.report.sdk.model.Report;
import com.report.sdk.model.ServiceCheck;
import com.util.BaseUtil;


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
			httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
		} else {
			httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
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


	public Report genWrkrRpt(String asgTxnNo, Integer raProfId, String rptType) throws Exception {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(ReportConstants.REPORT_URL);
		sbUrl.append(ReportUrlConstants.WORKER_REPORT);
		sbUrl.append("?asgTxnNo=").append(asgTxnNo);
		sbUrl.append("&raProfId=").append(raProfId);
		sbUrl.append("&rptType=").append(rptType);

		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), Report.class);

	}

}