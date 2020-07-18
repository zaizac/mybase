/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.constant;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public final class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	public static final String BASE_PACKAGE = "com.wfw";

	public static final String BASE_PACKAGE_REPO = "com.wfw.dao";

	public static final String BASE_PACKAGE_MODEL = "com.wfw.model";

	public static final String BASE_PACKAGE_SERVICE = "com.wfw.dto";

	public static final String BASE_PACKAGE_CONTROLLER = "com.wfw.controller";

	public static final String BASE_PACKAGE_REST_CONTROLLER = "com.wfw.rest.controller";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-wfw8";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

}
