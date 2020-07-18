/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.config;


import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dm.constants.ConfigConstants;
import com.idm.sdk.client.IdmServiceClient;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;


/**
 * The Class ApplicationConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = {
		ConfigConstants.BASE_PACKAGE }, includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
				ConfigConstants.BASE_PACKAGE_REPO + ".*", ConfigConstants.BASE_PACKAGE_SERVICE + ".*Service.*" }))
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	/** The Constant PROPERTY_PATH. */
	public static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UnknownRequestParamInterceptor());
		super.addInterceptors(registry);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.
	 * WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
	 */
	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new MappingJackson2HttpMessageConverter());
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
		messageSource.setBasenames(BaseConfigConstants.FILE_PFX + PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	/**
	 * Idm service.
	 *
	 * @return the idm service client
	 */
	@Bean
	public IdmServiceClient idmService() {
		String idmURL = messageSource().getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		LOGGER.debug("IDM Service: {}", idmURL);
		return new IdmServiceClient(idmURL);
	}
}