/**
 * Copyright 2019. 
 */
package com.util.conf;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * The Class ConfigUtil.
 *
 * @author mary.jane
 * @since Dec 29, 2018
 */
public class ConfigUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);


	/**
	 * Instantiates a new config util.
	 */
	private ConfigUtil() {
		// DO NOTHING
	}


	/**
	 * Gets the property path.
	 *
	 * @param pathProjConfig the path proj config
	 * @param fileSysResource the file sys resource
	 * @param propFilename the prop filename
	 * @return the property path
	 */
	public static String getPropertyPath(String pathProjConfig, String fileSysResource, String propFilename) {
		String propertyPath = null;

		// Get from PROJECT CONFIGURATION server
		String propertyHome = System.getProperty(pathProjConfig) != null ? System.getProperty(pathProjConfig)
				: System.getenv(pathProjConfig);
		LOGGER.debug("PROJECT CONFIGURATION HOME >> {}", propertyHome);
		File file = new File(propertyHome + fileSysResource);
		if (!file.exists()) {
			propertyHome = null;
		}

		// Get from TOMCAT server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = getPropertiesFromTomcat(fileSysResource);
		}

		// Get from JBOSS server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = getPropertiesFromJboss(fileSysResource);
		}

		if (!BaseUtil.isObjNull(propertyHome)) {
			file = new File(propertyHome + fileSysResource);
			if (file.exists() && !file.isDirectory()) {
				propertyPath = propertyHome + File.separator + propFilename;
			} else {
				LOGGER.debug("Missing properties file >> {}{}", propertyHome, fileSysResource);
			}
		}

		String propClasspath = "classpath:" + propFilename;

		// Get from Application CLASSPATH
		propertyPath = propertyPath != null ? propertyPath : propClasspath;

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Application Properties");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Path: " + propertyPath);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);

		return propertyPath;

	}


	/**
	 * Gets the properties from tomcat.
	 *
	 * @param fileSysResource the file sys resource
	 * @return the properties from tomcat
	 */
	private static String getPropertiesFromTomcat(String fileSysResource) {
		String propertyHome = System.getProperty(BaseConfigConstants.PATH_CATALINA_HOME) != null
				? System.getProperty(BaseConfigConstants.PATH_CATALINA_HOME)
				: System.getProperty(BaseConfigConstants.PATH_CATALINA_BASE);
		if (!BaseUtil.isObjNull(propertyHome)) {
			propertyHome = propertyHome + File.separator + "conf";
		}
		LOGGER.debug("CATALINA HOME: {}", propertyHome);
		File file = new File(propertyHome + fileSysResource);
		if (!file.exists()) {
			propertyHome = null;
		}

		return propertyHome;
	}


	/**
	 * Gets the properties from jboss.
	 *
	 * @param fileSysResource the file sys resource
	 * @return the properties from jboss
	 */
	private static String getPropertiesFromJboss(String fileSysResource) {
		String propertyHome = System.getProperty(BaseConfigConstants.PROJ_JBOSS_HOME);
		if (!BaseUtil.isObjNull(propertyHome)) {
			propertyHome = propertyHome + File.separator + "configuration";
		}
		LOGGER.debug("JBOSS HOME: {}", propertyHome);
		File file = new File(propertyHome + fileSysResource);
		if (!file.exists()) {
			propertyHome = null;
		}
		return propertyHome;
	}
}
