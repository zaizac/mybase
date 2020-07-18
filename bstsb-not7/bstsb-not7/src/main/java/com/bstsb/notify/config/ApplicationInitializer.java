/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.config;


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

import com.bstsb.notify.exception.DbConnectionClosedHandler;
import com.bstsb.notify.exception.ErrorHandlerFilter;
import com.bstsb.notify.exception.MultiReadServletFilter;
import com.planetj.servlet.filter.compression.CompressingFilter;



/**
 * Notification Dispatcher Servlet Initializer.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractAnnotationConfigDispatcherServletInitializer#
	 * getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class, ServiceConfig.class, SpringAopConfig.class };
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractAnnotationConfigDispatcherServletInitializer#
	 * getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractDispatcherServletInitializer#getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractDispatcherServletInitializer#getServletFilters()
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");

		return new Filter[] { characterEncodingFilter, new HiddenHttpMethodFilter(), new ShallowEtagHeaderFilter(),
				new ErrorHandlerFilter(), new CompressingFilter(), new MultiReadServletFilter() };
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractDispatcherServletInitializer#onStartup(javax.servlet.
	 * ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.info("Starting Notification Service...");
		servletContext.addListener(DbConnectionClosedHandler.class);
		super.onStartup(servletContext);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractDispatcherServletInitializer#customizeRegistration(javax.servlet
	 * .ServletRegistration.Dynamic)
	 */
	@Override
	public void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
}