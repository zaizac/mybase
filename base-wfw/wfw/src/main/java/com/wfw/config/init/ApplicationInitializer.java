/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config.init;


import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.wfw.config.ExceptionConfig;
import com.wfw.config.SecurityConfig;
import com.wfw.config.ServiceConfig;
import com.wfw.config.SpringAopConfig;
import com.wfw.config.WebMvcConfig;
import com.wfw.exception.DbConnectionClosedHandler;
import com.wfw.exception.ErrorHandlerFilter;
import com.wfw.exception.MultiReadServletFilter;


/**
 * Application Initializer
 * 
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebMvcConfig.class, SecurityConfig.class, ServiceConfig.class, ExceptionConfig.class,
				SpringAopConfig.class };
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

		DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");

		return new Filter[] { characterEncodingFilter, securityFilterChain, new HiddenHttpMethodFilter(),
				new ErrorHandlerFilter(), new MultiReadServletFilter() };
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(DbConnectionClosedHandler.class);
		super.onStartup(servletContext);
	}

}