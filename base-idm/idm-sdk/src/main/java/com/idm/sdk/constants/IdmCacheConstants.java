/**
 * Copyright 2019. 
 */
package com.idm.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmCacheConstants {

	private IdmCacheConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String CACHE_PREFIX = "IDM";

	public static final String CACHE_BUCKET = CACHE_PREFIX + "_BUCKET";

	// CACHE DURATION in seconds
	public static final int CACHE_DURATION_MINUTE = 900;

	public static final int CACHE_DURATION_HOURLY = 3600;

	public static final int CACHE_DURATION_DAILY = 86400;

	// CACHE KEY
	public static final String CACHE_KEY_MENU_ALL = CACHE_PREFIX + "_MENU_ALL";

	public static final String CACHE_KEY_MENU_LST = CACHE_PREFIX + "_MENU_LST";

	public static final String CACHE_KEY_MENU = CACHE_PREFIX + "_MENU_";

	public static final String CACHE_KEY_ROLE_ALL = CACHE_PREFIX + "_ROLE_ALL";

	public static final String CACHE_KEY_ROLE = CACHE_PREFIX + "_ROLE_";

	public static final String CACHE_KEY_ROLE_MENU_ALL = CACHE_PREFIX + "_ROLE_MENU_ALL";

	public static final String CACHE_KEY_ROLE_MENU = CACHE_PREFIX + "_ROLE_MENU_";

	public static final String CACHE_KEY_ROLE_MENU_GR = CACHE_PREFIX + "_ROLE_MENU_GR_";

	public static final String CACHE_KEY_USR_GRP_ALL = CACHE_PREFIX + "_USER_GROUP_ALL";

	public static final String CACHE_KEY_USR_GRP = CACHE_PREFIX + "_USER_GROUP_";

	public static final String CACHE_KEY_USR_GRP_RG = CACHE_PREFIX + "_USER_GROUP_RG_";

	public static final String CACHE_KEY_USR_GRP_BRNCH_ALL = CACHE_PREFIX + "_USER_GROUP_BRANCH_ALL";

	public static final String CACHE_KEY_USR_GRP_BRNCH = CACHE_PREFIX + "_USER_GROUP_BRANCH_";

	public static final String CACHE_KEY_USR_GRP_BRNCH_RG = CACHE_PREFIX + "_USER_GROUP_BRANCH_RG_";

	public static final String CACHE_KEY_USR_GRP_ROLE_ALL = CACHE_PREFIX + "_USER_GROUP_ROLE_ALL";

	public static final String CACHE_KEY_USR_GRP_ROLE = CACHE_PREFIX + "_USER_GROUP_ROLE_";

	public static final String CACHE_KEY_USR_GRP_ROLE_RG = CACHE_PREFIX + "_USER_GROUP_ROLE_RG_";

	public static final String CACHE_KEY_USER_TYPE_ALL = CACHE_PREFIX + "_REF_USER_TYPE_ALL";

	public static final String CACHE_KEY_USER_TYPE = CACHE_PREFIX + "_REF_USER_TYPE_";
}