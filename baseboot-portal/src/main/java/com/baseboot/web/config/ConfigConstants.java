/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.io.File;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public interface ConfigConstants {

	public static final String BASE_PACKAGE = "com.baseboot.web";

	public static final String CACHE_JAVA_FILE = "T(com.baseboot.web.constants.CacheConstants)";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PATH_CATALINA_HOME = "catalina.home";

	public static final String PATH_CATALINA_BASE = "catalina.base";

	public static final String PROJ_JBOSS_HOME = "jboss.server.config.dir";

	public static final String PROPERTY_FILENAME = "base-portal";

	public static final String PROPERTIES_EXT = ".properties";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME + PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = "classpath:" + PROPERTY_FILENAME;

	public static final String FILE_PFX = "file:";

	public static final String DB_CONF_DRIVER = "mysql.db.driver";

	public static final String DB_CONF_URL = "mysql.db.url";

	public static final String DB_CONF_UNAME = "mysql.db.uname";

	public static final String DB_CONF_PWORD = "mysql.db.pword";

	public static final String LDAP_CONF_DRIVER = "idm.ldap.driver";

	public static final String LDAP_CONF_URL = "idm.ldap.url";

	public static final String LDAP_CONF_UNAME = "idm.ldap.uname";

	public static final String LDAP_CONF_PWORD = "idm.ldap.pword";

	public static final String LDAP_CONF_PWORD_SALT = "idm.ldap.pword.salt";

	public static final String LDAP_CONF_BASEDN = "idm.ldap.basedn";

	public static final String LDAP_CONF_BASEDN_GROUP = "idm.ldap.basedn.group";

	public static final String REDIS_CONF_DEFAULT = "redis.cache.default";

	public static final String REDIS_CONF_UNAME = "redis.cache.uname";

	public static final String REDIS_CONF_PWORD = "redis.cache.pword";

	public static final String REDIS_CONF_HOST = "redis.cache.host";

	public static final String REDIS_CONF_PORT = "redis.cache.port";

	public static final String REDIS_CONF_SENTINEL_MASTER = "redis.cache.sentinel.master";

	public static final String REDIS_CONF_SENTINEL_HOST = "redis.cache.sentinel.host";

	public static final String REDIS_CONF_SENTINEL_PORT = "redis.cache.sentinel.port";

	public static final String SVC_CACHE_URL = "svc.cache.url";

	public static final String SVC_BE_URL = "svc.service.url";

	public static final String SVC_IDM_URL = "idm.service.url";

	public static final String SVC_IDM_TIMEOUT = "idm.service.timeout";

	public static final String SVC_IDM_SKEY = "idm.service.skey";

	public static final String SVC_IDM_EKEY = "idm.service.ekey";

	public static final String SVC_IDM_CLIENT = "idm.service.client";

	public static final String SVC_NOT_URL = "not.service.url";

	public static final String SVC_NOT_TIMEOUT = "not.service.timeout";

	public static final String SVC_RPT_URL = "rpt.service.url";

	public static final String SVC_RPT_TIMEOUT = "rpt.service.timeout";

	public static final String SVC_APJ_URL = "svc.service.url";

	public static final String SVC_APJ_TIMEOUT = "svc.service.timeout";

	public static final String SVC_DM_URL = "dm.service.url";

	public static final String SVC_DM_TIMEOUT = "dm.service.timeout";

	public static final String SVC_WFW_URL = "wfw.service.url";

	public static final String SVC_WFW_TIMEOUT = "wfw.service.timeout";

	public static final String SVC_DEVICE_PRINTER_URL = "device.printer.service.url";

	public static final String XPAT_ADMIN = "https://tr.egov.mv";

	public static final String IS_USING_HTTPS = "is.using.https";

	public static final String SVC_SGW_URL = "sgw.service.url";

	public static final String SVC_SGW_TIMEOUT = "sgw.service.timeout";

}
