/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.baseboot.web.helper.WebHelper;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;


/**
 * Spring MVC Configuration
 *
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan({ ConfigConstants.BASE_PACKAGE + ".*" })
public class WebMvcConfig implements WebMvcConfigurer, ApplicationContextAware {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

	private static String propertyPath;

	@Autowired
	private ApplicationContext applicationContext;


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new FormHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());

		WebMvcConfigurer.super.configureMessageConverters(converters);
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		FileSystemResource[] resources = new FileSystemResource[] {
				new FileSystemResource(getPropertyPath() + ConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(ConfigConstants.FILE_PFX + getPropertyPath());
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	private static String getPropertyPath() {
		if (!BaseUtil.isObjNull(propertyPath)) {
			return propertyPath;
		}

		// Get from PROJECT CONFIGURATION server
		String propertyHome = System.getProperty(ConfigConstants.PATH_PROJ_CONFIG) != null
				? System.getProperty(ConfigConstants.PATH_PROJ_CONFIG)
				: System.getenv(ConfigConstants.PATH_PROJ_CONFIG);
		LOGGER.debug("PROJECT CONFIGURATION HOME >> {}", propertyHome);
		File file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
		if (!file.exists()) {
			propertyHome = null;
		}

		// Get from TOMCAT server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = System.getProperty(ConfigConstants.PATH_CATALINA_HOME) != null
					? System.getProperty(ConfigConstants.PATH_CATALINA_HOME)
					: System.getProperty(ConfigConstants.PATH_CATALINA_BASE);
			if (!BaseUtil.isObjNull(propertyHome)) {
				propertyHome = propertyHome + File.separator + "conf";
			}
			LOGGER.debug("CATALINA HOME >> {}", propertyHome);
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (!file.exists()) {
				propertyHome = null;
			}
		}

		// Get from JBOSS server
		if (BaseUtil.isObjNull(propertyHome)) {
			propertyHome = System.getProperty(ConfigConstants.PROJ_JBOSS_HOME);
			if (!BaseUtil.isObjNull(propertyHome)) {
				propertyHome = propertyHome + File.separator + "configuration";
			}
			LOGGER.debug("JBOSS HOME >> {}", propertyHome);
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (!file.exists()) {
				propertyHome = null;
			}
		}

		if (!BaseUtil.isObjNull(propertyHome)) {
			file = new File(propertyHome + ConfigConstants.FILE_SYS_RESOURCE);
			if (file.exists() && !file.isDirectory()) {
				propertyPath = propertyHome + File.separator + ConfigConstants.PROPERTY_FILENAME;
			} else {
				LOGGER.error("Missing properties file >> {} {} ", propertyHome, ConfigConstants.FILE_SYS_RESOURCE);
			}
		}

		// Get from Application CLASSPATH
		propertyPath = propertyPath != null ? propertyPath : ConfigConstants.PROPERTY_CLASSPATH;

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Application Properties");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Path: " + propertyPath);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());

		return propertyPath;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


	@Bean
	public ViewResolver htmlViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
		resolver.setContentType("text/html");
		resolver.setCharacterEncoding("UTF-8");
		// resolver.setViewNames(array("*.html"));
		return resolver;
	}


	@Bean
	public ViewResolver javascriptViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
		resolver.setContentType("application/javascript");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(array("*.js"));
		return resolver;
	}


	@Bean
	public ViewResolver plainViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(plainTemplateResolver()));
		resolver.setContentType("text/plain");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(array("*.txt"));
		return resolver;
	}


	private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new SpringSecurityDialect());
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new Java8TimeDialect());
		engine.setTemplateResolver(templateResolver);
		engine.setTemplateEngineMessageSource(messageSource());
		return engine;
	}


	private ITemplateResolver htmlTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}


	private ITemplateResolver javascriptTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/js/");
		resolver.setSuffix(".js");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
		return resolver;
	}


	private ITemplateResolver plainTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/txt/");
		resolver.setSuffix(".txt");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.TEXT);
		return resolver;
	}


	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}


	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**", "/scripts/**", "/images/**", "/fonts/**", "/styles/**",
				"/templates/**")
				.addResourceLocations("/WEB-INF/resources/", "/WEB-INF/scripts/", "/WEB-INF/images/",
						"/WEB-INF/fonts/", "/styles/", "/templates/")
				.setCachePeriod(3600).resourceChain(true).addResolver(new GzipResourceResolver())
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
				.addResolver(new PathResourceResolver());
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").setCachePeriod(3600)
				.resourceChain(false);
	}


	@Override
	@Description("Custom Conversion Service")
	public void addFormatters(FormatterRegistry registry) {
		// registry.addFormatter(new NameFormatter());
	}


	public static String[] array(String... args) {
		return args;
	}


	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}


	@Bean
	public WebHelper webHelper() {
		return new WebHelper();
	}

}