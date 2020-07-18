/**
 * Copyright 2019. 
 */
package com.dm.sdk.client.constants;


/**
 * The Class DocMgtConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public final class DocMgtConstants {

	/**
	 * Instantiates a new doc mgt constants.
	 */
	private DocMgtConstants() {
		throw new IllegalStateException("ApplicationConstants Utility class");
	}


	/** The Constant SERVICE_CHECK. */
	public static final String SERVICE_CHECK = "/serviceCheck";

	/** The Constant DOCUMENTS. */
	public static final String DOCUMENTS = "/documents";

	/** The Constant REST_SVC_UPLOAD_BY_PROJ. */
	public static final String REST_SVC_UPLOAD_BY_PROJ = "/documents/{projId}";

	/** The Constant REST_SVC_DOWNLOAD. */
	public static final String REST_SVC_DOWNLOAD = "/documents/{id}";

	/** The Constant REST_SVC_DOWNLOAD_BY_PROJ. */
	public static final String REST_SVC_DOWNLOAD_BY_PROJ = "/documents/{projId}/{id}";

	/** The Constant REST_SVC_METADATA_BY_PROJ. */
	public static final String REST_SVC_METADATA_BY_PROJ = "/documents/{projId}/metadata/{id}";

	/** The Constant REST_SVC_SEARCH. */
	public static final String REST_SVC_SEARCH = "/search";

	/** The Constant REST_SVC_SEARCH_BY_PROJ. */
	public static final String REST_SVC_SEARCH_BY_PROJ = "/documents/{projId}/search";

	/** The Constant REST_SVC_METADATA. */
	public static final String REST_SVC_METADATA = "/metadata/{id}";

	/** The Constant REST_SVC_CACHE_EVICT. */
	public static final String REST_SVC_CACHE_EVICT = "/documents/cacheEvict";

	/** The Constant REST_SVC_DOWNLOAD_FILE. */
	public static final String REST_SVC_DOWNLOAD_FILE = "/download/file";
}