/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface DocMgtConstants {

	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String DOCUMENTS = "/documents";

	public static final String REST_SVC_UPLOAD_BY_PROJ = "/documents/{projId}";

	public static final String REST_SVC_DOWNLOAD = "/documents/{id}";

	public static final String REST_SVC_DOWNLOAD_BY_PROJ = "/documents/{projId}/{id}";

	public static final String REST_SVC_METADATA_BY_PROJ = "/documents/{projId}/metadata/{id}";

	public static final String REST_SVC_SEARCH = "/search";

	public static final String REST_SVC_SEARCH_BY_PROJ = "/documents/{projId}/search";

	public static final String REST_SVC_METADATA = "/metadata/{id}";

	public static final String REST_SVC_CACHE_EVICT = "/documents/cacheEvict";
}