/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.config;


import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.report.util.ConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = { ConfigConstants.BASE_PACKAGE })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);


	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
	}


	@Bean
	public StaticData staticData() {
		return new StaticData();
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}

}