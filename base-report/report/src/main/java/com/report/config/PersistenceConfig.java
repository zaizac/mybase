/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.config;


import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.report.core.PersistenceUnitProcessor;
import com.report.util.ConfigConstants;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableJpaRepositories(basePackages = ConfigConstants.BASE_PACKAGE_REPO, transactionManagerRef = "transactionManager")
public class PersistenceConfig {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

	public static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
	}


	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		PersistenceAnnotationBeanPostProcessor pa = new PersistenceAnnotationBeanPostProcessor();
		pa.setDefaultPersistenceUnitName("Authorization");
		return pa;
	}


	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}


	@Bean
	@PersistenceContext(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(ConfigConstants.BASE_PACKAGE_MODEL);

		entityManagerFactory.setJpaVendorAdapter(vendorAdapter());
		entityManagerFactory.afterPropertiesSet();
		entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
		entityManagerFactory.setJpaProperties(additionalProperties());
		entityManagerFactory.setPersistenceUnitPostProcessors(new PersistenceUnitProcessor());
		entityManagerFactory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
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

		return jpaProperties;
	}


	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		transactionManager.afterPropertiesSet();
		return transactionManager;
	}


	@Bean
	public DataSource dataSource() {
		String skey = messageSource().getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String mysqlDriver = messageSource().getMessage(BaseConfigConstants.DB_CONF_DRIVER, null,
				Locale.getDefault());
		String mysqlUrl = messageSource().getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		String mysqlUname = messageSource().getMessage(BaseConfigConstants.DB_CONF_UNAME, null, Locale.getDefault());
		String mysqlPword = messageSource().getMessage(BaseConfigConstants.DB_CONF_PWORD, null, Locale.getDefault());

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
		LOGGER.debug("{}", sb);

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(mysqlDriver);
		config.setJdbcUrl(mysqlUrl);
		config.setUsername(mysqlUname);
		config.setPassword(CryptoUtil.decrypt(mysqlPword, skey));
		config.addDataSourceProperty("connectionTimeout", BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("idleTimeout", BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_IDLE_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("maxLifetime", BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_LIFETIME, null, Locale.getDefault())));
		config.addDataSourceProperty("connectionTestQuery", messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_QUERY, null, Locale.getDefault()));
		config.addDataSourceProperty("maximumPoolSize",
				messageSource().getMessage(BaseConfigConstants.DB_CONF_HIKARI_MIN_IDLE, null, Locale.getDefault()));
		config.addDataSourceProperty("connectionInitSql", messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_POOL_SIZE, null, Locale.getDefault()));
		config.addDataSourceProperty("connectionInitSql",
				messageSource().getMessage(BaseConfigConstants.DB_CONF_HIKARI_INIT_SQL, null, Locale.getDefault()));
		config.addDataSourceProperty("validationTimeout", BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_VALID_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("leakDetectionThreshold", BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_LEAK_DETECT, null, Locale.getDefault())));
		return new HikariDataSource(config);
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		FileSystemResource[] resources = new FileSystemResource[] {
				new FileSystemResource(PROPERTY_PATH + BaseConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:report/messages/jasper", BaseConfigConstants.FILE_PFX + PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

}