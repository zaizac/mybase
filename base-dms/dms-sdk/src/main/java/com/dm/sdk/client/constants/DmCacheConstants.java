/**
 * Copyright 2019. 
 */
package com.dm.sdk.client.constants;


/**
 * The Class DmCacheConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public final class DmCacheConstants {

	/**
	 * Instantiates a new dm cache constants.
	 */
	private DmCacheConstants() {
		throw new IllegalStateException("DmCacheConstants Utility class");
	}


	/** The Constant CACHE_PREFIX. */
	public static final String CACHE_PREFIX = "DM";

	/** The Constant CACHE_BUCKET. */
	public static final String CACHE_BUCKET = CACHE_PREFIX + "_BUCKET";

	/** The Constant CACHE_DURATION_DAILY. */
	public static final int CACHE_DURATION_DAILY = 86400;

	/** The Constant CACHE_KEY_DM_DOWNLOAD. */
	public static final String CACHE_KEY_DM_DOWNLOAD = CACHE_PREFIX + "_DOWNLOAD_";

}