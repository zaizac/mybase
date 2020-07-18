/**
 * Copyright 2019
 */
package com.dm.config;


import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dm.constants.ConfigConstants;
import com.dm.constants.QualifierConstants;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 16, 2018
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ConfigConstants.BASE_PACKAGE_REPO, entityManagerFactoryRef = QualifierConstants.ENTITY_MANAGER, transactionManagerRef = QualifierConstants.TRANS_MANAGER)
public class PersistenceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

	@Autowired
	MessageSource messageSource;

	public static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
	}


	@Bean
	@PersistenceContext(name = QualifierConstants.ENTITY_MANAGER)
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(ConfigConstants.BASE_PACKAGE_MODEL);
		entityManagerFactory.setPersistenceUnitName(QualifierConstants.ENTITY_MANAGER_UNIT);

		entityManagerFactory.setJpaVendorAdapter(vendorAdapter());
		entityManagerFactory.afterPropertiesSet();
		entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
		entityManagerFactory.setJpaProperties(additionalProperties());
		entityManagerFactory.setPersistenceUnitPostProcessors(new JtaPuPostProcessor());
		entityManagerFactory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}


	@Bean
	@PersistenceContext(name = QualifierConstants.TRANS_MANAGER)
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		transactionManager.afterPropertiesSet();
		return transactionManager;
	}


	@Bean
	public DataSource dataSource() {
		String mysqlUrl = messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		String mysqlUname = messageSource.getMessage(BaseConfigConstants.DB_CONF_UNAME, null, Locale.getDefault());
		String mysqlPword = messageSource.getMessage(BaseConfigConstants.DB_CONF_PWORD, null, Locale.getDefault());
		String mysqlDriver = messageSource.getMessage(BaseConfigConstants.DB_CONF_DRIVER, null, Locale.getDefault());

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("MySQL Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Driver: " + mysqlDriver + BaseConstants.NEW_LINE);
		sb.append("URL: " + mysqlUrl + BaseConstants.NEW_LINE);
		sb.append("Username: " + mysqlUname + BaseConstants.NEW_LINE);
		sb.append("Password: " + mysqlPword);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);

		return new HikariDataSource(hikariConfig(mysqlUrl, mysqlUname, mysqlPword, mysqlDriver));
	}


	private HikariConfig hikariConfig(String mysqlUrl, String mysqlUname, String mysqlPword, String mysqlDriver) {
		String skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(mysqlDriver);
		config.setJdbcUrl(mysqlUrl);
		config.setUsername(mysqlUname);
		config.setPassword(CryptoUtil.decrypt(mysqlPword, skey));
		config.setPoolName("BE_POOL");
		config.setConnectionTimeout(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_TIMEOUT, null, Locale.getDefault())));
		config.setIdleTimeout(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_IDLE_TIMEOUT, null, Locale.getDefault())));
		config.setMaxLifetime(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_LIFETIME, null, Locale.getDefault())));
		config.setConnectionTestQuery(
				messageSource.getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_QUERY, null, Locale.getDefault()));
		config.setMinimumIdle(BaseUtil.getInt(
				messageSource.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MIN_IDLE, null, Locale.getDefault())));
		config.setMaximumPoolSize(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_POOL_SIZE, null, Locale.getDefault())));
		config.setConnectionInitSql(
				messageSource.getMessage(BaseConfigConstants.DB_CONF_HIKARI_INIT_SQL, null, Locale.getDefault()));
		config.setValidationTimeout(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_VALID_TIMEOUT, null, Locale.getDefault())));
		config.setLeakDetectionThreshold(BaseUtil.getInt(messageSource
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_LEAK_DETECT, null, Locale.getDefault())));
		return config;
	}


	HibernateJpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		vendorAdapter.setDatabase(Database.MYSQL);
		return vendorAdapter;
	}


	final Properties additionalProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.setProperty("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.use_query_cache", "true");
		jpaProperties.setProperty("hibernate.connection.autocommit", "false");
		jpaProperties.setProperty("hibernate.cache.provider_class",
				"net.sf.ehcache.hibernate.SingletonEhCacheProvider");
		jpaProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.setProperty("hibernate.show_sql", "false");
		jpaProperties.setProperty("hibernate.generate_statistics", "false");
		return jpaProperties;
	}
}
