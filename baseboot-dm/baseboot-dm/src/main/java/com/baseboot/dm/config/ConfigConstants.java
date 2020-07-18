/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.config;


import java.io.File;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface ConfigConstants {

	public static final String BASE_PACKAGE = "com.baseboot.dm";

	public static final String BASE_PACKAGE_REPO = "com.baseboot.dm.dao";

	public static final String BASE_PACKAGE_MODEL = "com.baseboot.dm.model";

	public static final String BASE_PACKAGE_SERVICE = "com.baseboot.dm.svc";

	public static final String BASE_PACKAGE_CONTROLLER = "com.baseboot.dm.controller";

	public static final String PATH_PROJ_CONFIG = "baseboot.config.path";

	public static final String PATH_CATALINA_HOME = "catalina.home";

	public static final String PATH_CATALINA_BASE = "catalina.base";

	public static final String PROJ_JBOSS_HOME = "jboss.server.config.dir";

	public static final String PROPERTY_FILENAME = "baseboot-dm";

	public static final String PROPERTIES_EXT = ".properties";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME + PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = "classpath:" + PROPERTY_FILENAME;

	public static final String FILE_PFX = "file:";

	public static final String MONGO_CONF_DB = "mongo.db.name";

	public static final String MONGO_CONF_HOST = "mongo.db.host";

	public static final String MONGO_CONF_PORT = "mongo.db.port";

	public static final String MONGO_CONF_UNAME = "mongo.db.uname";

	public static final String MONGO_CONF_PWORD = "mongo.db.pword";

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

	public static final String SVC_DM_URL = "dm.service.url";

	public static final String SVC_IDM_URL = "idm.service.url";

	public static final String SVC_IDM_SKEY = "idm.service.skey";

	public static final String SVC_IDM_CLIENT = "idm.service.client";

}
