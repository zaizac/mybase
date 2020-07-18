/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.constants;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	public static final String BASE_PACKAGE = "com.portal";

	public static final String CACHE_JAVA_FILE = "T(" + BASE_PACKAGE + ".web.util.constants.CacheConstants)";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-portal";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	public static final String SVC_BE_URL = "be.service.url";

	public static final String SVC_BE_TIMEOUT = "be.service.timeout";

	public static final String EXCEL_TEMPLATE_PATH = File.separator + "template" + File.separator + "portal"
			+ File.separator;

}
