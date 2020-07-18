/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.notify.config;


import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
@EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class })
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan(basePackages = ConfigConstants.BASE_PACKAGE_MODEL)
@EnableJpaRepositories(basePackages = ConfigConstants.BASE_PACKAGE_REPO, transactionManagerRef = "transactionManager")
@ComponentScan(basePackages = { ConfigConstants.BASE_PACKAGE + ".*" })
public class SpringBootConfig extends SpringBootServletInitializer {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SpringBootConfig.class);

	@Autowired
	MessageSource messageSource;


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootConfig.class, ThymeleafMailConfig.class, ServiceConfig.class);
	}


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootConfig.class, ThymeleafMailConfig.class,
				ServiceConfig.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}


	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> {
			connector.setPort(8080);
		}, connector -> {
			Object defaultMaxThreads = connector.getAttribute("maxThreads");
			connector.setAttribute("maxThreads", 100);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Changed Tomcat connector maxThreads from {} to {}", defaultMaxThreads, 100);
			}
		});
		return factory;
	}


	@Bean
	public DataSource dataSource() {
		messageSource.getMessage(ConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String mysqlDriver = messageSource.getMessage(ConfigConstants.DB_CONF_DRIVER, null, Locale.getDefault());
		String mysqlUrl = messageSource.getMessage(ConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		String mysqlUname = messageSource.getMessage(ConfigConstants.DB_CONF_UNAME, null, Locale.getDefault());
		String mysqlPword = messageSource.getMessage(ConfigConstants.DB_CONF_PWORD, null, Locale.getDefault());

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
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(sb.toString());
		}

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(mysqlDriver);
		config.setJdbcUrl(mysqlUrl);
		config.setUsername(mysqlUname);
		config.setPassword(mysqlPword);
		config.addDataSourceProperty("connectionTimeout", BaseUtil.getInt(
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_CONN_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("idleTimeout", BaseUtil.getInt(
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_IDLE_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("maxLifetime", BaseUtil.getInt(
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_MAX_LIFETIME, null, Locale.getDefault())));
		config.addDataSourceProperty("connectionTestQuery",
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_CONN_QUERY, null, Locale.getDefault()));
		config.addDataSourceProperty("maximumPoolSize",
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_MIN_IDLE, null, Locale.getDefault()));
		config.addDataSourceProperty("connectionInitSql",
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_MAX_POOL_SIZE, null, Locale.getDefault()));
		config.addDataSourceProperty("connectionInitSql",
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_INIT_SQL, null, Locale.getDefault()));
		config.addDataSourceProperty("validationTimeout", BaseUtil.getInt(
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_VALID_TIMEOUT, null, Locale.getDefault())));
		config.addDataSourceProperty("leakDetectionThreshold", BaseUtil.getInt(
				messageSource.getMessage(ConfigConstants.DB_CONF_HIKARI_LEAK_DETECT, null, Locale.getDefault())));
		return new HikariDataSource(config);
	}

}