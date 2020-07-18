/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.config;


import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.planetj.servlet.filter.compression.CompressingFilter;
import com.report.exception.DbConnectionClosedHandler;
import com.report.exception.ErrorHandlerFilter;
import com.report.exception.MultiReadServletFilter;


/**
 * WEB Dispatcher Servlet Initializer
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
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

		return new Filter[] { characterEncodingFilter, new HiddenHttpMethodFilter(), new ShallowEtagHeaderFilter(),
				new ErrorHandlerFilter(), new CompressingFilter(), new MultiReadServletFilter() };
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.info("Starting Report Service...");
		servletContext.addListener(DbConnectionClosedHandler.class);
		super.onStartup(servletContext);
	}


	@Override
	public void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
}