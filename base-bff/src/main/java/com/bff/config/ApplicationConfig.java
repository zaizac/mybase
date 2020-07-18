/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bff.core.MessageService;
import com.bff.util.constants.ConfigConstants;
import com.bff.util.helper.WebHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.JsonUtil;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;


/**
 * Spring MVC Configuration
 *
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = { ConfigConstants.BASE_PACKAGE + ".*" })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	public static final String PROPERTY_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
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
		messageSource.setBasenames("classpath:messages/portal/locale", "classpath:messages/portal/message",
				"classpath:messages/app_url", BaseConfigConstants.FILE_PFX + PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());

		ByteArrayHttpMessageConverter bam = new ByteArrayHttpMessageConverter();
		List<MediaType> mediaTypes = new LinkedList<>();
		mediaTypes.add(MediaType.IMAGE_JPEG);
		mediaTypes.add(MediaType.IMAGE_PNG);
		mediaTypes.add(MediaType.IMAGE_GIF);
		mediaTypes.add(MediaType.parseMediaType("application/pdf"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-excel"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-fontobject"));
		mediaTypes.add(MediaType.parseMediaType("image/svg+xml"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-ttf"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/woff"));
		mediaTypes.add(MediaType.parseMediaType("application/woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/octet-stream"));
		bam.setSupportedMediaTypes(mediaTypes);
		converters.add(bam);
		super.configureMessageConverters(converters);
	}


	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper mapper = JsonUtil.objectMapper();
		return new MappingJackson2HttpMessageConverter(mapper);
	}


	@Bean
	public MessageService messageService() {
		return new MessageService(messageSource());
	}


	@Bean
	public WebHelper webHelper() {
		return new WebHelper();
	}

}