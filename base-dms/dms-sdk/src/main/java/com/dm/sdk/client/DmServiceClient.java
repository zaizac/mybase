/**
 * Copyright 2019. 
 */
package com.dm.sdk.client;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.client.constants.DocMgtConstants;
import com.dm.sdk.exception.DmException;
import com.dm.sdk.model.Documents;
import com.util.BaseUtil;
import com.util.http.HttpAuthClient;
import com.util.model.ServiceCheck;


/**
 * The Class DmServiceClient.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DmServiceClient {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DmServiceClient.class);

	/** The rest template. */
	private static DmRestTemplate restTemplate;

	/** The url. */
	private String url;

	/** The proj id. */
	private String projId;

	/** The client id. */
	private String clientId;

	/** The token. */
	private String token;

	/** The auth token. */
	private String authToken;

	/** The message id. */
	private String messageId;
	
	/** The portal type. */
	private String portalType;

	/** The read timeout. */
	private int readTimeout;
	
	private String systemType;

	/** The Constant PROJ_ID_TEXT. */
	private static final String PROJ_ID_TEXT = "projId";


	/**
	 * Instantiates a new dm service client.
	 *
	 * @param url the url
	 */
	public DmServiceClient(String url) {
		this(url, null, 0);
	}


	/**
	 * Instantiates a new dm service client.
	 *
	 * @param url the url
	 * @param readTimeout the read timeout
	 */
	public DmServiceClient(String url, int readTimeout) {
		this(url, null, readTimeout);
	}


	/**
	 * Instantiates a new dm service client.
	 *
	 * @param url the url
	 * @param clientId the client id
	 * @param readTimeout the read timeout
	 */
	public DmServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
	}


	static {
		restTemplate = new DmRestTemplate("DM");
	}


	/**
	 * Gets the rest template.
	 *
	 * @return the rest template
	 */
	private DmRestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new DmException(DmErrorCodeEnum.E400DOM911);
		}
		HttpAuthClient hac = null;
		if (authToken != null) {
			if(portalType != null) {
				hac = new HttpAuthClient(authToken, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(authToken, messageId, readTimeout);
			}
		} else {
			if(portalType != null) {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout);
			}
		}
		LOGGER.info("System Type: {} ", systemType);
		if (systemType != null) {
			hac.setSystemType(systemType);
		}
		httpClient = hac.getHttpClient();
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	/**
	 * Gets the service URI.
	 *
	 * @param serviceName the service name
	 * @return the service URI
	 */
	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.info("Service Rest URL: {}", uri);
		return uri;
	}


	/**
	 * Sets the client id.
	 *
	 * @param clientId the new client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}


	/**
	 * Sets the message id.
	 *
	 * @param messageId the new message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	
	/**
	 * @param portalType the portalType to set
	 */
	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	/**
	 * @param systemType
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
	/**
	 * Sets the proj id.
	 *
	 * @param projId the new proj id
	 */
	public void setProjId(String projId) {
		this.projId = projId;
	}


	/**
	 * Sets the auth token.
	 *
	 * @param authToken the new auth token
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	/**
	 * Check connection.
	 *
	 * @return the string
	 */
	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.SERVICE_CHECK + "/test"), String.class);
	}


	/**
	 * Service test.
	 *
	 * @return the service check
	 */
	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	/**
	 * Evict.
	 *
	 * @param prefixKey the prefix key
	 * @return true, if successful
	 */
	public boolean evict(String prefixKey) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(DocMgtConstants.REST_SVC_CACHE_EVICT);
		if (!BaseUtil.isObjNull(prefixKey)) {
			sbUrl.append("?prefixKey=" + prefixKey);
		}
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	/**
	 * Upload.
	 *
	 * @param documents the documents
	 * @return the documents
	 */
	public Documents upload(Documents documents) {
		Map<String, Object> params = new HashMap<>();
		String urlSvc = DocMgtConstants.DOCUMENTS;
		if (projId != null) {
			urlSvc = DocMgtConstants.REST_SVC_UPLOAD_BY_PROJ;
			params.put(PROJ_ID_TEXT, projId);
		}
		return getRestTemplate().postForObject(getServiceURI(urlSvc), documents, Documents.class, params);
	}


	/**
	 * Download Document by docMgtId.
	 *
	 * @param docMgtId the doc mgt id
	 * @return the documents
	 */
	public Documents download(String docMgtId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", docMgtId);

		String urlSvc = DocMgtConstants.REST_SVC_DOWNLOAD;
		if (projId != null) {
			urlSvc = DocMgtConstants.REST_SVC_DOWNLOAD_BY_PROJ;
			params.put(PROJ_ID_TEXT, projId);
		}
		return getRestTemplate().getForObject(getServiceURI(urlSvc), Documents.class, params);
	}


	/**
	 * Download Document by docCollection and docMgtId.
	 *
	 * @param projId the proj id
	 * @param docMgtId the doc mgt id
	 * @return the documents
	 */
	public Documents download(String projId, String docMgtId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", docMgtId);
		params.put(PROJ_ID_TEXT, projId);
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.REST_SVC_DOWNLOAD_BY_PROJ),
				Documents.class, params);
	}


	/**
	 * Get Document Metadata by docMgtId.
	 *
	 * @param docMgtId the doc mgt id
	 * @return the metadata
	 */
	public Documents getMetadata(String docMgtId) {
		return getMetadata(docMgtId, null);
	}


	/**
	 * Get Document metadata by docMgtId and projId.
	 *
	 * @param docMgtId the doc mgt id
	 * @param custProjId the cust proj id
	 * @return the metadata
	 */
	public Documents getMetadata(String docMgtId, String custProjId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", docMgtId);

		String urlSvc = DocMgtConstants.REST_SVC_METADATA;
		if (!BaseUtil.isObjNull(custProjId)) {
			urlSvc = DocMgtConstants.REST_SVC_METADATA_BY_PROJ;
			params.put(PROJ_ID_TEXT, custProjId);
		} else {
			if (projId != null) {
				urlSvc = DocMgtConstants.REST_SVC_METADATA_BY_PROJ;
				params.put(PROJ_ID_TEXT, projId);
			}
		}
		return getRestTemplate().getForObject(getServiceURI(urlSvc), Documents.class, params);
	}


	/**
	 * Get Metadata by other info.
	 *
	 * @param projId the proj id
	 * @param txnNo the txn no
	 * @param docId the doc id
	 * @param refNo the ref no
	 * @param docMgtId the doc mgt id
	 * @return the metadata
	 */
	public Documents getMetadata(String projId, String txnNo, String docId, String refNo, String docMgtId) {
		Map<String, Object> params = new HashMap<>();
		params.put(PROJ_ID_TEXT, projId);
		params.put("txnNo", txnNo);
		params.put("docId", docId);
		params.put("refNo", refNo);
		params.put("id", docMgtId);
		return getRestTemplate().getForObject(getServiceURI(DocMgtConstants.REST_SVC_SEARCH_BY_PROJ), Documents.class,
				params);
	}


	/**
	 * Update metadata.
	 *
	 * @param projId the proj id
	 * @param docMgtId the doc mgt id
	 * @param txnNo the txn no
	 * @param docId the doc id
	 * @param refNo the ref no
	 * @return the documents
	 */
	public Documents updateMetadata(String projId, String docMgtId, String txnNo, Integer docId, String refNo) {
		if (BaseUtil.isObjNull(projId) || BaseUtil.isObjNull(docMgtId)) {
			throw new DmException(DmErrorCodeEnum.E400DOM913, new String[] { "projId, docMgtId" });
		}

		Map<String, Object> params = new HashMap<>();
		params.put(PROJ_ID_TEXT, projId);

		Documents document = new Documents();
		document.setId(docMgtId);
		document.setRefno(refNo);
		document.setTxnno(txnNo);
		document.setDocid(docId);

		return getRestTemplate().postForObject(getServiceURI(DocMgtConstants.REST_SVC_SEARCH_BY_PROJ), document,
				Documents.class, params);
	}


	/**
	 * Download Document bytes by docCollection and docMgtId.
	 *
	 * @param docMgtId the doc mgt id
	 * @return the byte[]
	 */
	public byte[] downloadByte(String docMgtId) {
		StringBuilder sb = new StringBuilder();
		sb.append(DocMgtConstants.DOCUMENTS + DocMgtConstants.REST_SVC_DOWNLOAD_FILE);
		sb.append("?docMgtId={docMgtId}");
		sb.append("&projId={projId}");
		Map<String, Object> params = new HashMap<>();
		params.put("docMgtId", docMgtId);
		if (projId != null) {
			params.put(PROJ_ID_TEXT, projId);
		}
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), byte[].class, params);
	}

}