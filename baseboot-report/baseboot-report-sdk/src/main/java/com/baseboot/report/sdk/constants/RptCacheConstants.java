/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface RptCacheConstants {

	// CACHE NAMESPACE
	public static final String CACHE_PREFIX = "FDHP_RPT";

	public static final String CACHE_BUCKET = CACHE_PREFIX + "_BUCKET";

	// CACHE DURATION in seconds
	public static final int CACHE_DURATION_MINUTE = 900;

	public static final int CACHE_DURATION_HOURLY = 3600;

	public static final int CACHE_DURATION_DAILY = 86400;

	// CACHE KEY
	public static final String CACHE_KEY_REPORT = CACHE_PREFIX + "_";

	public static final String CACHE_STATIC_RPT = CACHE_PREFIX + "_REF_";

	public static final String CACHE_STATIC_RPT_SUB_SEC = CACHE_PREFIX + "_REF_SUB_SECTOR_";

}