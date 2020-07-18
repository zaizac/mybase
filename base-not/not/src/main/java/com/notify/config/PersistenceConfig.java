/**
 * Copyright 2019. 
 */
package com.notify.config;


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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.notify.constants.ConfigConstants;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * The Class PersistenceConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
@EnableJpaRepositories(basePackages = ConfigConstants.BASE_PACKAGE_REPO, transactionManagerRef = "transactionManager")
public class PersistenceConfig {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class.getName());

	/** The Constant PROPERTY_PATH. */
	public static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
	}


	/**
	 * Persistence annotation bean post processor.
	 *
	 * @return the persistence annotation bean post processor
	 */
	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		PersistenceAnnotationBeanPostProcessor pa = new PersistenceAnnotationBeanPostProcessor();
		pa.setDefaultPersistenceUnitName("Authorization");
		return pa;
	}


	/**
	 * Persistence exception translation post processor.
	 *
	 * @return the persistence exception translation post processor
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}


	/**
	 * Entity manager factory.
	 *
	 * @return the entity manager factory
	 */
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


	/**
	 * Vendor adapter.
	 *
	 * @return the hibernate jpa vendor adapter
	 */
	HibernateJpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		vendorAdapter.setDatabase(Database.MYSQL);
		return vendorAdapter;
	}


	/**
	 * Additional properties.
	 *
	 * @return the properties
	 */
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
		jpaProperties.setProperty("hibernate.order_inserts", "true");
		jpaProperties.setProperty("hibernate.order_updates", "true");
		jpaProperties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
		jpaProperties.setProperty("hibernate.jdbc.batch_size", "50000");
		jpaProperties.setProperty("hibernate.jdbc.fetch_size", "50000");
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		return jpaProperties;
	}


	/**
	 * Transaction manager.
	 *
	 * @return the platform transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		transactionManager.afterPropertiesSet();
		return transactionManager;
	}


	/**
	 * Data source.
	 *
	 * @return the data source
	 */
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
		LOGGER.info("{}", sb);

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(mysqlDriver);
		config.setJdbcUrl(mysqlUrl);
		config.setUsername(mysqlUname);
		config.setPassword(CryptoUtil.decrypt(mysqlPword, skey));
		config.setConnectionTimeout(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_TIMEOUT, null, Locale.getDefault())));
		config.setIdleTimeout(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_IDLE_TIMEOUT, null, Locale.getDefault())));
		config.setMaxLifetime(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_LIFETIME, null, Locale.getDefault())));
		config.setConnectionTestQuery(messageSource().getMessage(BaseConfigConstants.DB_CONF_HIKARI_CONN_QUERY, null,
				Locale.getDefault()));
		config.setMinimumIdle(BaseUtil.getInt(messageSource().getMessage(BaseConfigConstants.DB_CONF_HIKARI_MIN_IDLE,
				null, Locale.getDefault())));
		config.setMaximumPoolSize(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_MAX_POOL_SIZE, null, Locale.getDefault())));
		config.setConnectionInitSql(
				messageSource().getMessage(BaseConfigConstants.DB_CONF_HIKARI_INIT_SQL, null, Locale.getDefault()));
		config.setValidationTimeout(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_VALID_TIMEOUT, null, Locale.getDefault())));
		config.setLeakDetectionThreshold(BaseUtil.getInt(messageSource()
				.getMessage(BaseConfigConstants.DB_CONF_HIKARI_LEAK_DETECT, null, Locale.getDefault())));
		return new HikariDataSource(config);
	}


	/**
	 * Property config in dev.
	 *
	 * @return the property sources placeholder configurer
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		FileSystemResource[] resources = new FileSystemResource[] {
				new FileSystemResource(PROPERTY_PATH + BaseConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:app", "classpath:report/messages/syncfussion",
				BaseConfigConstants.FILE_PFX + PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

}