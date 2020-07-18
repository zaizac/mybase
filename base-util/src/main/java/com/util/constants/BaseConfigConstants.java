/**
 * Copyright 2019. 
 */
package com.util.constants;


/**
 * The Class BaseConfigConstants.
 *
 * @author mary.jane
 * @since Jan 5, 2019
 */
public class BaseConfigConstants {

	/**
	 * Instantiates a new base config constants.
	 */
	private BaseConfigConstants() {
		throw new IllegalStateException(BaseConfigConstants.class.getName());
	}


	/** The Constant CLASSPATH_PFX. */
	public static final String CLASSPATH_PFX = "classpath:";

	/** The Constant FILE_PFX. */
	public static final String FILE_PFX = "file:";

	/** The Constant PROPERTIES_EXT. */
	public static final String PROPERTIES_EXT = ".properties";

	/** The Constant SERVICE_TAG_PFX. */
	public static final String SERVICE_TAG_PFX = "app.pfx";

	/** The Constant PORTAL_TYPE. */
	public static final String PORTAL_TYPE = "app.portal.type";

	/** The Constant PORTAL_ACCESS. */
	public static final String PORTAL_ACCESS = "app.portal.access";

	/** The Constant PORTAL_ACCESS_FROM. */
	public static final String PORTAL_ACCESS_FROM = "app.portal.access.from";

	/** The Constant PORTAL_ACCESS_TO. */
	public static final String PORTAL_ACCESS_TO = "app.portal.access.to";

	/** The Constant PORTAL_UI_LAYOUT. */
	public static final String PORTAL_UI_LAYOUT = "app.portal.ui.layout";

	/** The Constant PORTAL_UI_THEME. */
	public static final String PORTAL_UI_THEME = "app.portal.ui.theme";

	/** The Constant PORTAL_UI_MODE. */
	public static final String PORTAL_UI_MODE = "app.portal.ui.mode";

	/** The Constant PORTAL_UI_CONTENT. */
	public static final String PORTAL_UI_CONTENT = "app.portal.ui.content";

	/** The Constant PORTAL_UI_PAGE. */
	public static final String PORTAL_UI_PAGE = "app.portal.ui.page";

	/** The Constant PORTAL_UI_TEXTURE. */
	public static final String PORTAL_UI_TEXTURE = "app.portal.ui.texture";

	/** The Constant BUILD_VERSION. */
	public static final String BUILD_VERSION = "build.version";

	/** The Constant PATH_CATALINA_HOME. */
	public static final String PATH_CATALINA_HOME = "catalina.home";

	/** The Constant PATH_CATALINA_BASE. */
	public static final String PATH_CATALINA_BASE = "catalina.base";

	/** The Constant PROJ_JBOSS_HOME. */
	public static final String PROJ_JBOSS_HOME = "jboss.server.config.dir";

	/** The Constant DB_CONF_DRIVER. */
	public static final String DB_CONF_DRIVER = "mysql.db.driver";

	/** The Constant DB_CONF_URL. */
	public static final String DB_CONF_URL = "mysql.db.url";

	/** The Constant DB_CONF_UNAME. */
	public static final String DB_CONF_UNAME = "mysql.db.uname";

	/** The Constant DB_CONF_PWORD. */
	public static final String DB_CONF_PWORD = "mysql.db.pword";

	/** The Constant DB_CONF_HIKARI_CONN_TIMEOUT. */
	public static final String DB_CONF_HIKARI_CONN_TIMEOUT = "mysql.db.pool.hikaricp.connectionTimeout";

	/** The Constant DB_CONF_HIKARI_IDLE_TIMEOUT. */
	public static final String DB_CONF_HIKARI_IDLE_TIMEOUT = "mysql.db.pool.hikaricp.idleTimeout";

	/** The Constant DB_CONF_HIKARI_MAX_LIFETIME. */
	public static final String DB_CONF_HIKARI_MAX_LIFETIME = "mysql.db.pool.hikaricp.maxLifetime";

	/** The Constant DB_CONF_HIKARI_CONN_QUERY. */
	public static final String DB_CONF_HIKARI_CONN_QUERY = "mysql.db.pool.hikaricp.connectionTestQuery";

	/** The Constant DB_CONF_HIKARI_MIN_IDLE. */
	public static final String DB_CONF_HIKARI_MIN_IDLE = "mysql.db.pool.hikaricp.minimumIdle";

	/** The Constant DB_CONF_HIKARI_MAX_POOL_SIZE. */
	public static final String DB_CONF_HIKARI_MAX_POOL_SIZE = "mysql.db.pool.hikaricp.maximumPoolSize";

	/** The Constant DB_CONF_HIKARI_INIT_SQL. */
	public static final String DB_CONF_HIKARI_INIT_SQL = "mysql.db.pool.hikaricp.connectionInitSql";

	/** The Constant DB_CONF_HIKARI_VALID_TIMEOUT. */
	public static final String DB_CONF_HIKARI_VALID_TIMEOUT = "mysql.db.pool.hikaricp.validationTimeout";

	/** The Constant DB_CONF_HIKARI_LEAK_DETECT. */
	public static final String DB_CONF_HIKARI_LEAK_DETECT = "mysql.db.pool.hikaricp.leakDetectionThreshold";

	/** The Constant REDIS_CONF_DEFAULT. */
	public static final String REDIS_CONF_DEFAULT = "redis.cache.default";

	/** The Constant REDIS_CONF_UNAME. */
	public static final String REDIS_CONF_UNAME = "redis.cache.uname";

	/** The Constant REDIS_CONF_PWORD. */
	public static final String REDIS_CONF_PWORD = "redis.cache.pword";

	/** The Constant REDIS_CONF_HOST. */
	public static final String REDIS_CONF_HOST = "redis.cache.host";

	/** The Constant REDIS_CONF_PORT. */
	public static final String REDIS_CONF_PORT = "redis.cache.port";

	/** The Constant REDIS_CONF_DURATION. */
	public static final String REDIS_CONF_DURATION = "redis.cache.duration";

	/** The Constant REDIS_CONF_MAX_POOL. */
	public static final String REDIS_CONF_MAX_POOL = "redis.cache.max.pool";

	/** The Constant REDIS_CONF_BUCKET. */
	public static final String REDIS_CONF_BUCKET = "redis.cache.bucket";

	/** The Constant SVC_DM_URL. */
	public static final String SVC_DM_URL = "dm.service.url";

	/** The Constant SVC_DM_TIMEOUT. */
	public static final String SVC_DM_TIMEOUT = "dm.service.timeout";

	/** The Constant SVC_IDM_URL. */
	public static final String SVC_IDM_URL = "idm.service.url";

	/** The Constant SVC_IDM_TIMEOUT. */
	public static final String SVC_IDM_TIMEOUT = "idm.service.timeout";

	/** The Constant SVC_IDM_SKEY. */
	public static final String SVC_IDM_SKEY = "idm.service.skey";

	/** The Constant SVC_IDM_EKEY. */
	public static final String SVC_IDM_EKEY = "idm.service.ekey";

	/** The Constant SVC_IDM_CLIENT. */
	public static final String SVC_IDM_CLIENT = "idm.service.client";

	/** The Constant SVC_NOT_URL. */
	public static final String SVC_NOT_URL = "not.service.url";

	/** The Constant SVC_NOT_TIMEOUT. */
	public static final String SVC_NOT_TIMEOUT = "not.service.timeout";
	
	/** The Constant SVC_RPT_URL. */
	public static final String SVC_RPT_URL = "rpt.service.url";

	/** The Constant SVC_RPT_TIMEOUT. */
	public static final String SVC_RPT_TIMEOUT = "rpt.service.timeout";	

	/** The Constant SVC_WFW_URL. */
	public static final String SVC_WFW_URL = "wfw.service.url";

	/** The Constant SVC_WFW_TIMEOUT. */
	public static final String SVC_WFW_TIMEOUT = "wfw.service.timeout";

	/** The Constant SVC_ICAO_URL. */
	public static final String SVC_ICAO_URL = "icao.service.url";

	/** The Constant SVC_ICAO_KEY. */
	public static final String SVC_ICAO_KEY = "icao.service.key";

	/** The Constant SVC_ICAO_TIMEOUT. */
	public static final String SVC_ICAO_TIMEOUT = "icao.service.timeout";

	/** The Constant SESSION_SECURE_COOKIE. */
	public static final String SESSION_SECURE_COOKIE = "session.secure.cookie";

	/** The Constant THYMELEAF_TEMPLATE_CACHEABLE. */
	public static final String THYMELEAF_TEMPLATE_CACHEABLE = "thymleaf.template.cacheable";

	/** The Constant THYMELEAF_TEMPLATE_DEFAULT_LOCATION. */
	public static final String THYMELEAF_TEMPLATE_DEFAULT_LOCATION = "/WEB-INF/default/";

	/** The Constant THYMELEAF_TEMPLATE_PROJECT_LOCATION. */
	public static final String THYMELEAF_TEMPLATE_PROJECT_LOCATION = "/WEB-INF/views/";

	/** The Constant TILES_DEFAULT_LOCATION. */
	public static final String TILES_DEFAULT_LOCATION = "/WEB-INF/default/tiles/*.xml";

	/** The Constant TILES_PROJECT_LOCATION. */
	public static final String TILES_PROJECT_LOCATION = "/templates/tiles/*.xml";

	/** The Constant HTML_SFX. */
	public static final String HTML_SFX = ".html";

	/** The Constant CSS_SFX. */
	public static final String CSS_SFX = ".css";

	/** The Constant MONSTER_UI_URL. */
	public static final String MONSTER_UI_URL = "/webjars/monster-ui/";

}