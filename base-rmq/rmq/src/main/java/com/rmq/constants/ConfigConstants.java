/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.constants;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String BASE_PACKAGE = "com.rmq";

	public static final String BASE_PACKAGE_REPO = BASE_PACKAGE + ".dao";

	public static final String BASE_PACKAGE_MODEL = BASE_PACKAGE + ".model";

	public static final String BASE_PACKAGE_CONTROLLER = BASE_PACKAGE + ".controller";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-rmq";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	public static final String SVC_BE_URL = "be.service.url";

	public static final String SVC_BE_TIMEOUT = "be.service.timeout";

}