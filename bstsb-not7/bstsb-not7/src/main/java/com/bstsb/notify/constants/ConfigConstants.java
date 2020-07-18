/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.constants;


import java.io.File;

import com.bstsb.util.constants.BaseConfigConstants;



/**
 * The Class ConfigConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ConfigConstants {

	/**
	 * Instantiates a new config constants.
	 */
	private ConfigConstants() {
		throw new IllegalStateException("ConfigConstants Utility class");
	}


	/** The Constant ENCODING. */
	public static final String ENCODING = "UTF-8";

	/** The Constant BASE_PACKAGE. */
	public static final String BASE_PACKAGE = "com.bstsb.notify";

	/** The Constant BASE_PACKAGE_REPO. */
	public static final String BASE_PACKAGE_REPO = "com.bstsb.notify.dao";

	/** The Constant BASE_PACKAGE_MODEL. */
	public static final String BASE_PACKAGE_MODEL = "com.bstsb.notify.model";

	/** The Constant BASE_PACKAGE_SERVICE. */
	public static final String BASE_PACKAGE_SERVICE = "com.bstsb.notify.service";

	/** The Constant BASE_PACKAGE_CONTROLLER. */
	public static final String BASE_PACKAGE_CONTROLLER = "com.bstsb.notify.controller";

	/** The Constant PATH_PROJ_CONFIG. */
	public static final String PATH_PROJ_CONFIG = "bstsb.config.path";

	/** The Constant PROPERTY_FILENAME. */
	public static final String PROPERTY_FILENAME = "bstsb-not7";

	/** The Constant FILE_SYS_RESOURCE. */
	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	/** The Constant PROPERTY_CLASSPATH. */
	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	/** The Constant MAIL_CONF_HOST. */
	public static final String MAIL_CONF_HOST = "mail.host";

	/** The Constant MAIL_CONF_PORT. */
	public static final String MAIL_CONF_PORT = "mail.port";

	/** The Constant MAIL_CONF_PROTOCOL. */
	public static final String MAIL_CONF_PROTOCOL = "mail.protocol";

	/** The Constant MAIL_CONF_SENDER. */
	public static final String MAIL_CONF_SENDER = "mail.from";

	/** The Constant MAIL_CONF_LOCALHOST. */
	public static final String MAIL_CONF_LOCALHOST = "mail.localhost";

	/** The Constant MAIL_CONF_UNAME. */
	public static final String MAIL_CONF_UNAME = "mail.username";

	/** The Constant MAIL_CONF_PWORD. */
	public static final String MAIL_CONF_PWORD = "mail.password";

	/** The Constant MAIL_TEMPLATE_PATH. */
	public static final String MAIL_TEMPLATE_PATH = File.separator + "template" + File.separator + "mail"
			+ File.separator;

	/** The Constant FCM_CONF_URL. */
	public static final String FCM_CONF_URL = "fcm.googleapis.url";

	/** The Constant FCM_CONF_KEY. */
	public static final String FCM_CONF_KEY = "fcm.googleapis.key";

	/** The Constant FCM_CONF_TIMEOUT. */
	public static final String FCM_CONF_TIMEOUT = "fcm.service.timeout";

	/** The Constant SVC_RPT_URL. */
	public static final String SVC_RPT_URL = "rpt.service.url";

	/** The Constant SVC_RPT_TIMEOUT. */
	public static final String SVC_RPT_TIMEOUT = "rpt.service.timeout";

}