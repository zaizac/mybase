/**
 * Copyright 2019. 
 */
package com.dm.constants;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * The Class ConfigConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public final class ConfigConstants {

	/**
	 * Instantiates a new config constants.
	 */
	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	/** The Constant BASE_PACKAGE. */
	public static final String BASE_PACKAGE = "com.dm";

	/** The Constant BASE_PACKAGE_REPO. */
	public static final String BASE_PACKAGE_REPO = "com.dm.dao";

	/** The Constant BASE_PACKAGE_MODEL. */
	public static final String BASE_PACKAGE_MODEL = "com.dm.model";

	/** The Constant BASE_PACKAGE_SERVICE. */
	public static final String BASE_PACKAGE_SERVICE = "com.dm.svc";

	/** The Constant BASE_PACKAGE_CONTROLLER. */
	public static final String BASE_PACKAGE_CONTROLLER = "com.dm.controller";

	/** The Constant PATH_PROJ_CONFIG. */
	public static final String PATH_PROJ_CONFIG = "base.config.path";

	/** The Constant PROPERTY_FILENAME. */
	public static final String PROPERTY_FILENAME = "base-dms8";

	/** The Constant FILE_SYS_RESOURCE. */
	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	/** The Constant PROPERTY_CLASSPATH. */
	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;
	
	public static final String SYSTEM_TYPE = "system.type";

}
