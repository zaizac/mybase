/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class CacheConstants {

	private CacheConstants() {
		throw new IllegalStateException("Utility class");
	}


	// CACHE BUCKET
	public static final String CACHE_PREFIX = "EMP";

	public static final String CACHE_BUCKET = CACHE_PREFIX + "_BUCKET";

	// CACHE DURATION in seconds
	public static final int CACHE_DURATION_MINUTE = 900;

	public static final int CACHE_DURATION_HOURLY = 3600;

	public static final int CACHE_DURATION_DAILY = 86400;

	public static final String CACHE_KEY_SECTOR_AGENCY = CACHE_PREFIX + "_REF_SECTOR_AGENCY";

	public static final String CACHE_KEY_SUB_SECTOR = CACHE_PREFIX + "_REF_SUB_SECTOR_";

	public static final String CACHE_KEY_REF = CACHE_PREFIX + "_REF_";

	public static final String CACHE_KEY_REF_CMN = CACHE_PREFIX + "_REF_CMN_";

	public static final String CACHE_KEY_NOT = CACHE_PREFIX + "_NOT_";

	public static final String CACHE_KEY_CAPTCHA = CACHE_PREFIX + "_CAPTCHA_";

	public static final String CACHE_KEY_OTP = CACHE_PREFIX + "_OTP_";

	public static final String CACHE_KEY_QR = CACHE_PREFIX + "_QR_";

}