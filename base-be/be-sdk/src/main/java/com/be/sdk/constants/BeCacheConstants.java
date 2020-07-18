/**
 * Copyright 2019
 */
package com.be.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public class BeCacheConstants {

	private BeCacheConstants() {
		throw new IllegalStateException(BeCacheConstants.class.getName());
	}


	// CACHE BUCKET
	public static final String CACHE_PREFIX = "BE";

	public static final String CACHE_BUCKET = CACHE_PREFIX + "_BUCKET";

	// CACHE DURATION in seconds
	public static final int CACHE_DURATION_MINUTE = 900;

	public static final int CACHE_DURATION_HOURLY = 3600;

	public static final int CACHE_DURATION_DAILY = 86400;

	// CACHE KEY
	public static final String CACHE_STATIC_SVC = CACHE_PREFIX + "_REF_";

	public static final String CACHE_KEY_CITY_ALL = CACHE_STATIC_SVC + "CITY_ALL";

	public static final String CACHE_KEY_CITY = CACHE_STATIC_SVC + "CITY_";
	
	public static final String CACHE_KEY_STATE_ALL = CACHE_STATIC_SVC + "STATE_ALL";
	
	public static final String CACHE_KEY_STATE = CACHE_STATIC_SVC + "STATE_";
	
	public static final String CACHE_KEY_COUNTRY_ALL = CACHE_STATIC_SVC + "COUNTRY_ALL";
	
	public static final String CACHE_KEY_COUNTRY = CACHE_STATIC_SVC + "COUNTRY_";
	
	public static final String CACHE_KEY_STATUS_ALL = CACHE_STATIC_SVC + "STATUS_ALL";

	public static final String CACHE_KEY_DOCUMENTS_ALL = CACHE_STATIC_SVC + "DOCUMENT_ALL";

	public static final String CACHE_KEY_DOCUMENTS = CACHE_STATIC_SVC + "DOCUMENT_";

}