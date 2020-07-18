/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.FlowAjaxThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfw.constant.ConfigConstants;
import com.wfw.constant.PageConstants;


/**
 * Spring MVC Configuration
 *
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan(useDefaultFilters = true, basePackages = {
		ConfigConstants.BASE_PACKAGE }, includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
				ConfigConstants.BASE_PACKAGE_REPO + ".*", ConfigConstants.BASE_PACKAGE_SERVICE + ".*DtoImp" }))
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(PageConstants.PAGE_LOGIN).setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}


	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/cripts/**").addResourceLocations("/scripts/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/report/**").addResourceLocations("/report/");
		registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
		registry.addResourceHandler("/templates/**").addResourceLocations("/templates/");
	}


	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}


	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new FormHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
	}


	@Bean
	@Description("Thymeleaf Template Resolver using HTML 5")
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);
		return resolver;
	}


	@Bean
	@Description("Thymeleaf Template Engine with Spring Integration")
	public SpringTemplateEngine templateEngine(MessageSource messageSource) {
		Set<IDialect> dialects = new HashSet<>();
		dialects.add(new TilesDialect());
		dialects.add(new SpringSecurityDialect());
		dialects.add(new ConditionalCommentsDialect());

		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.setAdditionalDialects(dialects);
		engine.setMessageSource(messageSource);
		return engine;
	}


	@Bean
	@Description("Ajax Thymeleaf View Resolver")
	public AjaxThymeleafViewResolver tilesViewResolver(MessageSource messageSource) {
		AjaxThymeleafViewResolver resolver = new AjaxThymeleafViewResolver();
		resolver.setViewClass(FlowAjaxThymeleafTilesView.class);
		resolver.setTemplateEngine(templateEngine(messageSource));
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(-1);
		return resolver;
	}


	@Bean
	@Description("Spring Resource Template")
	public SpringResourceTemplateResolver thymeleafSpringResource() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setTemplateMode("HTML5");
		return resolver;
	}


	@SuppressWarnings("deprecation")
	@Bean
	@Description("Tiles Configurer")
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
		configurer.setDefinitions("/templates/tiles/*.xml");
		return configurer;
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}


	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}