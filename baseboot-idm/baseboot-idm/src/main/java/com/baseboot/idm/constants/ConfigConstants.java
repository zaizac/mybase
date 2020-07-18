/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.constants;


import java.io.File;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String BASE_PACKAGE = "com.baseboot.idm";

	public static final String BASE_PACKAGE_REPO = "com.baseboot.idm.dao";

	public static final String BASE_PACKAGE_MODEL = "com.baseboot.idm.model";

	public static final String BASE_PACKAGE_CONTROLLER = "com.baseboot.idm.controller";

	public static final String BASE_PACKAGE_SERVICE = "com.baseboot.idm.service";

	public static final String BASE_PACKAGE_LDAP = "com.baseboot.idm.ldap";

	public static final String CACHE_JAVA_FILE = "T(com.baseboot.idm.sdk.constants.IdmCacheConstants)";

	public static final String PATH_PROJ_CONFIG = "baseboot.config.path";

	public static final String PATH_CATALINA_HOME = "catalina.home";

	public static final String PATH_CATALINA_BASE = "catalina.base";

	public static final String PROJ_JBOSS_HOME = "jboss.server.config.dir";

	public static final String PROPERTY_FILENAME = "baseboot-idm";

	public static final String PROPERTIES_EXT = ".properties";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME + PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = "classpath:" + PROPERTY_FILENAME;

	public static final String FILE_PFX = "file:";

	public static final String DB_CONF_DRIVER = "mysql.db.driver";

	public static final String DB_CONF_URL = "mysql.db.url";

	public static final String DB_CONF_UNAME = "mysql.db.uname";

	public static final String DB_CONF_PWORD = "mysql.db.pword";

	public static final String DB_CONF_HIKARI_CONN_TIMEOUT = "mysql.db.pool.hikaricp.connectionTimeout";

	public static final String DB_CONF_HIKARI_IDLE_TIMEOUT = "mysql.db.pool.hikaricp.idleTimeout";

	public static final String DB_CONF_HIKARI_MAX_LIFETIME = "mysql.db.pool.hikaricp.maxLifetime";

	public static final String DB_CONF_HIKARI_CONN_QUERY = "mysql.db.pool.hikaricp.connectionTestQuery";

	public static final String DB_CONF_HIKARI_MIN_IDLE = "mysql.db.pool.hikaricp.minimumIdle";

	public static final String DB_CONF_HIKARI_MAX_POOL_SIZE = "mysql.db.pool.hikaricp.maximumPoolSize";

	public static final String DB_CONF_HIKARI_INIT_SQL = "mysql.db.pool.hikaricp.connectionInitSql";

	public static final String DB_CONF_HIKARI_VALID_TIMEOUT = "mysql.db.pool.hikaricp.validationTimeout";

	public static final String DB_CONF_HIKARI_LEAK_DETECT = "mysql.db.pool.hikaricp.leakDetectionThreshold";

	public static final String LDAP_CONF_DRIVER = "idm.ldap.driver";

	public static final String LDAP_CONF_URL = "idm.ldap.url";

	public static final String LDAP_CONF_UNAME = "idm.ldap.uname";

	public static final String LDAP_CONF_PWORD = "idm.ldap.pwd";

	public static final String LDAP_CONF_PWORD_SALT = "idm.ldap.pwdsalt";

	public static final String LDAP_CONF_BASEDN = "idm.ldap.basedn";

	public static final String LDAP_CONF_BASEDN_GROUP = "idm.ldap.group.basedn";

	public static final String REDIS_CONF_DEFAULT = "redis.cache.default";

	public static final String REDIS_CONF_UNAME = "redis.cache.uname";

	public static final String REDIS_CONF_PWORD = "redis.cache.pword";

	public static final String REDIS_CONF_HOST = "redis.cache.host";

	public static final String REDIS_CONF_PORT = "redis.cache.port";

	public static final String REDIS_CONF_SENTINEL_MASTER = "redis.cache.sentinel.master";

	public static final String REDIS_CONF_SENTINEL_HOST = "redis.cache.sentinel.host";

	public static final String REDIS_CONF_SENTINEL_PORT = "redis.cache.sentinel.port";

	public static final String IDM_UID_LEN = "idm.uid.length";

	public static final String IDM_TXNID_LEN = "idm.txnid.length";

	public static final String SVC_IDM_URL = "idm.service.url";

	public static final String SVC_IDM_TIMEOUT = "idm.service.timeout";

	public static final String SVC_IDM_SKEY = "idm.service.skey";

	public static final String SVC_IDM_CLIENT = "idm.service.client";

	public static final String SVC_NOT_URL = "not.service.url";

	public static final String SVC_NOT_TIMEOUT = "not.service.timeout";

}