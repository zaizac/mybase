/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.config;


import java.io.File;

import com.bstsb.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	public static final String BASE_PACKAGE = "com.bstsb.idm";

	public static final String BASE_PACKAGE_REPO = "com.bstsb.idm.dao";

	public static final String BASE_PACKAGE_MODEL = "com.bstsb.idm.model";

	public static final String BASE_PACKAGE_SERVICE = "com.bstsb.idm.service";

	public static final String BASE_PACKAGE_LDAP = "com.bstsb.idm.ldap";

	public static final String BASE_PACKAGE_CONTROLLER = "com.bstsb.idm.controller";

	public static final String CACHE_JAVA_FILE = "T(com.bstsb.idm.sdk.constants.IdmCacheConstants)";

	public static final String PATH_PROJ_CONFIG = "bstsb.config.path";

	public static final String PROPERTY_FILENAME = "bstsb-idm7";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	public static final String LDAP_CONF_DRIVER = "idm.ldap.driver";

	public static final String LDAP_CONF_URL = "idm.ldap.url";

	public static final String LDAP_CONF_UNAME = "idm.ldap.uname";

	public static final String LDAP_CONF_PWORD = "idm.ldap.pwd";

	public static final String LDAP_CONF_PWORD_SALT = "idm.ldap.pwdsalt";

	public static final String LDAP_CONF_BASEDN = "idm.ldap.basedn";

	public static final String LDAP_CONF_BASEDN_GROUP = "idm.ldap.group.basedn";
	
	public static final String IDM_UID_LEN = "idm.uid.length";

	public static final String IDM_TXNID_LEN = "idm.txnid.length";

}