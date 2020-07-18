/**
 * Copyright 2019. Universal Recruitment Platform
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
	public static final String PROPERTY_FILENAME = "base-dm8";

	/** The Constant FILE_SYS_RESOURCE. */
	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	/** The Constant PROPERTY_CLASSPATH. */
	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	/** The Constant MONGO_CONF_DB. */
	public static final String MONGO_CONF_DB = "mongo.db.name";

	/** The Constant MONGO_CONF_HOST. */
	public static final String MONGO_CONF_HOST = "mongo.db.host";

	/** The Constant MONGO_CONF_PORT. */
	public static final String MONGO_CONF_PORT = "mongo.db.port";

	/** The Constant MONGO_CONF_SET_NAME. */
	public static final String MONGO_CONF_SET_NAME = "mongo.db.setname";

	/** The Constant MONGO_CONF_UNAME. */
	public static final String MONGO_CONF_UNAME = "mongo.db.uname";

	/** The Constant MONGO_CONF_PWORD. */
	public static final String MONGO_CONF_PWORD = "mongo.db.pword";

}
