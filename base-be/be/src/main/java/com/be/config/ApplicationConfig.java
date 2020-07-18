/**
 * Copyright 2019
 */
package com.be.config;


import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.be.constants.ConfigConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.JsonUtil;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = { ConfigConstants.BASE_PACKAGE + ".*" })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new Resource[] { new ClassPathResource("app" + BaseConfigConstants.PROPERTIES_EXT),
				new FileSystemResource(PersistenceConfig.PROPERTY_PATH + BaseConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreResourceNotFound(false);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(BaseConfigConstants.CLASSPATH_PFX + "app",
				BaseConfigConstants.FILE_PFX + PersistenceConfig.PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}


	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter(JsonUtil.objectMapper());
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}


	@Bean
	public ObjectMapper objectMapper() {
		return JsonUtil.objectMapper();
	}

}