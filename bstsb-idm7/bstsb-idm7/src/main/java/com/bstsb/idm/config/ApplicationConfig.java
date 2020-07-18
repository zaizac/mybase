/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.config;


import java.util.List;
import java.util.Locale;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bstsb.idm.util.UidGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = {
		ConfigConstants.BASE_PACKAGE }, includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
				ConfigConstants.BASE_PACKAGE_REPO + ".*", ConfigConstants.BASE_PACKAGE_SERVICE + ".*",
				ConfigConstants.BASE_PACKAGE_LDAP + ".*Service" }))
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new MappingJackson2HttpMessageConverter());
	}


	@Bean
	public UidGenerator uidGenerator(MessageSource messageSource) {
		Integer uidLength = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.IDM_UID_LEN, null, Locale.getDefault()));
		Integer txnidLength = Integer
				.valueOf(messageSource.getMessage(ConfigConstants.IDM_TXNID_LEN, null, Locale.getDefault()));
		return new UidGenerator(uidLength, txnidLength);
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}

}