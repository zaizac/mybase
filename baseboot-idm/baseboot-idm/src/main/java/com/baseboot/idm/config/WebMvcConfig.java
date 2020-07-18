/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.config;


import java.io.File;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.util.UidGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

	private static String propertyPath;


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}


	@Bean
	public UidGenerator uidGenerator() {
		MessageSource messageSource = messageSource();
		Integer uidLength = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.IDM_UID_LEN, null, Locale.getDefault()));
		Integer txnidLength = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.IDM_TXNID_LEN, null, Locale.getDefault()));
		return new UidGenerator(uidLength, txnidLength);
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		FileSystemResource[] resources = new FileSystemResource[] {
				new FileSystemResource(getPropertyPath() + ConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(ConfigConstants.FILE_PFX + getPropertyPath());
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	private static String getPropertyPath() {
		if (!BaseUtil.isObjNull(propertyPath)) {
			return propertyPath;
		}

		// Get from PROJECT CONFIGURATION server
		String propertyHome = System.getProperty(ConfigConstants.PATH_PROJ_CONFIG) != null
				? System.getProperty(ConfigConstants.PATH_PROJ_CONFIG)
				: System.getenv(ConfigConstants.PATH_PROJ_CONFIG);
		LOGGER.debug("PROJECT CONFIGURATION HOME >> {}", propertyHome);
		File file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
		if (!file.exists()) {
			propertyHome = null;
		}

		// Get from TOMCAT server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = System.getProperty(ConfigConstants.PATH_CATALINA_HOME) != null
					? System.getProperty(ConfigConstants.PATH_CATALINA_HOME)
					: System.getProperty(ConfigConstants.PATH_CATALINA_BASE);
			if (!BaseUtil.isObjNull(propertyHome)) {
				propertyHome = propertyHome + File.separator + "conf";
			}
			LOGGER.debug("CATALINA HOME >> {}", propertyHome);
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (!file.exists()) {
				propertyHome = null;
			}
		}

		// Get from JBOSS server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = System.getProperty(ConfigConstants.PROJ_JBOSS_HOME);
			if (!BaseUtil.isObjNull(propertyHome)) {
				propertyHome = propertyHome + File.separator + "configuration";
			}
			LOGGER.debug("JBOSS HOME >> {}", propertyHome);
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (!file.exists()) {
				propertyHome = null;
			}
		}

		if (!BaseUtil.isObjNull(propertyHome)) {
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (file.exists() && !file.isDirectory()) {
				propertyPath = propertyHome + File.separator + ConfigConstants.PROPERTY_FILENAME;
			} else {
				LOGGER.error("Missing properties file >> {} {} ", propertyHome, ConfigConstants.FILE_SYS_RESOURCE);
			}
		}

		// Get from Application CLASSPATH
		propertyPath = propertyPath != null ? propertyPath : ConfigConstants.PROPERTY_CLASSPATH;

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Application Properties");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Path: " + propertyPath);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());

		return propertyPath;
	}

}