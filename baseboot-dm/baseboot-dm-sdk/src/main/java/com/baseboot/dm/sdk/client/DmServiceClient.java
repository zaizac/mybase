/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.client;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.dm.sdk.client.constants.DmErrorCodeEnum;
import com.baseboot.dm.sdk.client.constants.DocMgtConstants;
import com.baseboot.dm.sdk.exception.DmException;
import com.baseboot.dm.sdk.model.Documents;
import com.baseboot.dm.sdk.model.ServiceCheck;
import com.baseboot.dm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DmServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(DmServiceClient.class);

	private static DmRestTemplate restTemplate;

	private String url;

	private String projId;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;

	private int readTimeout;

	
	@SuppressWarnings("unused")
	private DmServiceClient() {
		// Block Initialization
	}

	public DmServiceClient(String url) {
		this.url = url;
		initialize();
	}


	public DmServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public DmServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	private void initialize() {
		restTemplate = new DmRestTemplate();
	}


	private DmRestTemplate getRestTemplate() throws DmException {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new DmException(DmErrorCodeEnum.E400DOM911);
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


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public void setProjId(String projId) {
		this.projId = projId;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public String checkConnection() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.SERVICE_CHECK + "/test"), String.class);
	}


	public ServiceCheck serviceTest() throws Exception {
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	public boolean evict() throws Exception {
		return equals(null);
	}


	public boolean evict(String prefixKey) throws Exception {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(DocMgtConstants.REST_SVC_CACHE_EVICT);
		if (!BaseUtil.isObjNull(prefixKey))
			sbUrl.append("?prefixKey=" + prefixKey);
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	public Documents upload(Documents documents) throws DmException {
		Map<String, Object> params = new HashMap<String, Object>();
		String URL = DocMgtConstants.DOCUMENTS;
		if (projId != null) {
			URL = DocMgtConstants.REST_SVC_UPLOAD_BY_PROJ;
			params.put("projId", projId);
		}
		return getRestTemplate().postForObject(getServiceURI(URL), documents, Documents.class, params);
	}


	/**
	 * Download Document by docMgtId
	 * 
	 * @param docMgtId
	 * @return
	 * @throws Exception
	 */
	public Documents download(String docMgtId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", docMgtId);

		String URL = DocMgtConstants.REST_SVC_DOWNLOAD;
		if (projId != null) {
			URL = DocMgtConstants.REST_SVC_DOWNLOAD_BY_PROJ;
			params.put("projId", projId);
		}
		return getRestTemplate().getForObject(getServiceURI(URL), Documents.class, params);
	}


	/**
	 * Get Document Metadata by docMgtId
	 * 
	 * @param docMgtId
	 * @return
	 * @throws Exception
	 */
	public Documents getMetadata(String docMgtId) throws Exception {
		return getMetadata(docMgtId, null);
	}


	/**
	 * Get Document metadata by docMgtId and projId
	 * 
	 * @param docMgtId
	 * @param custProjId
	 * @return
	 * @throws Exception
	 */
	public Documents getMetadata(String docMgtId, String custProjId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", docMgtId);

		String URL = DocMgtConstants.REST_SVC_METADATA;
		if (!BaseUtil.isObjNull(custProjId)) {
			URL = DocMgtConstants.REST_SVC_METADATA_BY_PROJ;
			params.put("projId", custProjId);
		} else {
			if (projId != null) {
				URL = DocMgtConstants.REST_SVC_METADATA_BY_PROJ;
				params.put("projId", projId);
			}
		}
		return getRestTemplate().getForObject(getServiceURI(URL), Documents.class, params);
	}


	/**
	 * Get Metadata by other info
	 * 
	 * @param projId
	 * @param txnNo
	 * @param docId
	 * @param refNo
	 * @return
	 * @throws Exception
	 */
	public Documents getMetadata(String projId, String txnNo, String docId, String refNo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projId", projId);
		params.put("txnNo", txnNo);
		params.put("docId", docId);
		params.put("refNo", refNo);

		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.REST_SVC_SEARCH_BY_PROJ), Documents.class,
				params);
	}

	public Documents updateMetadata(String projId, String docMgtId, String txnNo, String docId, String refNo)
			throws Exception {
		if (BaseUtil.isObjNull(projId) || BaseUtil.isObjNull(docMgtId)) {
			throw new DmException(DmErrorCodeEnum.E400DOM913, new String[] { "projId, docMgtId" });
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projId", projId);

		Documents document = new Documents();
		document.setId(docMgtId);
		document.setRefno(refNo);
		document.setTxnno(txnNo);
		document.setDocid(docId);

		return getRestTemplate().postForObject(getServiceURI(DocMgtConstants.REST_SVC_SEARCH_BY_PROJ), document,
				Documents.class, params);
	}
}