/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.util;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}
	

	public static final String BASE_PACKAGE = "com.report";

	public static final String BASE_PACKAGE_REPO = "com.report.dao";

	public static final String BASE_PACKAGE_MODEL = "com.report.model";

	public static final String BASE_PACKAGE_SERVICE = "com.report.service";

	public static final String BASE_PACKAGE_CONTROLLER = "com.report.controller";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-report";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	public static final String MAIL_CONF_HOST = "mail.host";

	public static final String MAIL_CONF_PORT = "mail.port";

	public static final String MAIL_CONF_PROTOCOL = "mail.protocol";

	public static final String MAIL_CONF_SENDER = "mail.from";

	public static final String MAIL_CONF_LOCALHOST = "mail.localhost";

	public static final String MAIL_CONF_UNAME = "mail.username";

	public static final String MAIL_CONF_PWORD = "mail.password";

	public static final String SVC_BE_URL = "be.service.url";

	public static final String SVC_BE_TIMEOUT = "be.service.timeout";

}
