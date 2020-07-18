/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config.init;


import java.util.Locale;

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
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.portal.config.ExceptionConfig;
import com.portal.config.MDCFilter;
import com.portal.config.SecurityConfig;
import com.portal.config.ServiceConfig;
import com.portal.config.SessionListener;
import com.portal.config.ThymeleafConfig;
import com.portal.config.WebMvcConfig;
import com.portal.config.gzip.GzipFilter;
import com.portal.core.exception.ErrorHandlerFilter;
import com.util.BaseUtil;
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
		return new Class[] { WebMvcConfig.class, ThymeleafConfig.class, SecurityConfig.class, ExceptionConfig.class,
				ServiceConfig.class };
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
				new ShallowEtagHeaderFilter(), new ErrorHandlerFilter(), new GzipFilter(), new MDCFilter() };
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.info("Starting Internal Portal...");
		servletContext.addListener(new HttpSessionEventPublisher());
		servletContext.addListener(new SessionListener());
		servletContext.addListener(new RequestContextListener());
		String secure = messageSource().getMessage("session.secure.cookie", null, Locale.getDefault());
		if (!BaseUtil.isObjNull(secure)) {
			servletContext.getSessionCookieConfig().setSecure(Boolean.getBoolean(secure));
		}
		super.onStartup(servletContext);
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/portal/locale", "classpath:messages/portal/message",
				"classpath:messages/app_url", BaseConfigConstants.FILE_PFX + WebMvcConfig.PROPERTY_PATH);
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