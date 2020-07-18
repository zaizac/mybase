/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.bff.core.exception.ErrorHandlerFilter;
import com.bff.core.exception.MultiReadServletFilter;
import com.util.constants.BaseConfigConstants;


/**
 * Application Initializer
 *
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);


	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class, ServiceConfig.class, SpringAopConfig.class };
	}


	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}


	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}


	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");

		return new Filter[] { characterEncodingFilter, new MDCFilter(), new ErrorHandlerFilter(),
				new MultiReadServletFilter() };
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.info("Starting BFF Service...");
		super.onStartup(servletContext);
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/portal/locale", "classpath:messages/portal/message",
				"classpath:messages/app_url", BaseConfigConstants.FILE_PFX + ApplicationConfig.PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	@Override
	public void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}

}