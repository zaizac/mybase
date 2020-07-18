/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.config;


import java.util.Collections;
import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rmq.constants.ConfigConstants;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
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

	@Value("${sgw.server.port}")
	private static String serverPort;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootConfig.class, WebMvcConfig.class, ServiceConfig.class, RabbitMQConfig.class );
	}


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootConfig.class, WebMvcConfig.class,
				ServiceConfig.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", serverPort));
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}


	@Bean
	public DataSource dataSource(MessageSource messageSource) {
		String skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String mysqlDriver = messageSource.getMessage(BaseConfigConstants.DB_CONF_DRIVER, null, Locale.getDefault());
		String mysqlUrl = messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		String mysqlUname = messageSource.getMessage(BaseConfigConstants.DB_CONF_UNAME, null, Locale.getDefault());
		String mysqlPword = messageSource.getMessage(BaseConfigConstants.DB_CONF_PWORD, null, Locale.getDefault());

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

		return new HikariDataSource(hikariConfig(messageSource, mysqlUrl, mysqlUname, mysqlPword, mysqlDriver));
	}
	
	private HikariConfig hikariConfig(MessageSource messageSource, String mysqlUrl, String mysqlUname, String mysqlPword, String mysqlDriver) {
		String skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(mysqlDriver);
		config.setJdbcUrl(mysqlUrl);
		config.setUsername(mysqlUname);
		config.setPassword(CryptoUtil.decrypt(mysqlPword, skey));
		config.setPoolName("RMQ_POOL");
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

}