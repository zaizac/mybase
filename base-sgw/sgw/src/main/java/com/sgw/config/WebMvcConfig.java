/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.config;


import java.util.LinkedList;
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
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sgw.constants.ConfigConstants;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

	private static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		converters.add(new FormHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
		
		List<MediaType> mediaTypes = new LinkedList<>();
		mediaTypes.add(MediaType.IMAGE_JPEG);
		mediaTypes.add(MediaType.IMAGE_PNG);
		mediaTypes.add(MediaType.IMAGE_GIF);
		mediaTypes.add(MediaType.parseMediaType("application/pdf"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-excel"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-fontobject"));
		mediaTypes.add(MediaType.parseMediaType("image/svg+xml"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-ttf"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/woff"));
		mediaTypes.add(MediaType.parseMediaType("application/woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/octet-stream"));

		ByteArrayHttpMessageConverter bam = new ByteArrayHttpMessageConverter();
		bam.setSupportedMediaTypes(mediaTypes);
		converters.add(bam);
		super.configureMessageConverters(converters);
	}


	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
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

}