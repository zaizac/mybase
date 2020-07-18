/**
 * Copyright 2019
 */
package com.be.constants;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	public static final String BASE_PACKAGE = "com.be";

	public static final String BASE_PACKAGE_REPO = BASE_PACKAGE + ".dao";

	public static final String BASE_PACKAGE_MODEL = BASE_PACKAGE + ".model";

	public static final String BASE_PACKAGE_CONTROLLER = BASE_PACKAGE + ".controller";

	public static final String CACHE_JAVA_FILE = "T(" + BASE_PACKAGE + ".sdk.constants.BeCacheConstants)";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-be";

	public static final String SVC_BE_URL = "be.service.url";

	public static final String SVC_BE_TIMEOUT = "be.service.timeout";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String SYSTEM = "system";

}