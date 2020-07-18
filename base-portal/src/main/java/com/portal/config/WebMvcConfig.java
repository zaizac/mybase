/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config;


import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import com.dialect.MessageService;
import com.portal.config.audit.AuditActionInterceptor;
import com.portal.constants.ConfigConstants;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.web.util.helper.WebHelper;
import com.util.conf.ConfigUtil;
import com.util.constants.BaseConfigConstants;


/**
 * Spring MVC Configuration
 *
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
@EnableWebMvc
@ComponentScan({ ConfigConstants.BASE_PACKAGE + ".*" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	public static final String PROPERTY_PATH;

	/** The Constant TEMPLATE_PATH. */
	public static final String TEMPLATE_PATH;

	static {
		PROPERTY_PATH = ConfigUtil.getPropertyPath(ConfigConstants.PATH_PROJ_CONFIG,
				ConfigConstants.FILE_SYS_RESOURCE, ConfigConstants.PROPERTY_FILENAME);
		String propertyHome = WebMvcConfig.PROPERTY_PATH.replace(ConfigConstants.PROPERTY_FILENAME, "");
		TEMPLATE_PATH = propertyHome.concat(ConfigConstants.EXCEL_TEMPLATE_PATH);
	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(PageConstants.PAGE_LOGIN).setViewName("login");
		registry.addViewController(PageConstants.PAGE_ERROR).setViewName(PageTemplate.TEMP_ERROR);
		registry.addViewController(PageConstants.PAGE_ERROR_403).setViewName(PageTemplate.TEMP_ERROR_403);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**", "/scripts/**", "/images/**", "/fonts/**", "/styles/**",
				"/templates/**")
				.addResourceLocations("/resources/", "/scripts/", "/images/", "/fonts/", "/styles/", "/templates/")
				.setCachePeriod(3600).resourceChain(true).addResolver(new GzipResourceResolver())
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
				.addResolver(new PathResourceResolver());
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").setCachePeriod(3600)
				.resourceChain(false);
		// Reports are created dynamically DO NOT set in cache
		registry.addResourceHandler("/report/**").addResourceLocations("/report/").resourceChain(true)
				.addResolver(new GzipResourceResolver())
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
				.addResolver(new PathResourceResolver());
	}


	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}


	@Bean
	public AuditActionInterceptor auditActionInterceptor() {
		return new AuditActionInterceptor();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(new SecurityInterceptor());
		registry.addInterceptor(new UnknownRequestParamInterceptor());
		registry.addInterceptor(auditActionInterceptor());
	}


	@Override
	@Description("HTTP Message Converters")
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new FormHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());

		ByteArrayHttpMessageConverter bam = new ByteArrayHttpMessageConverter();
		List<MediaType> mediaTypes = new LinkedList<>();
		mediaTypes.add(MediaType.IMAGE_JPEG);
		mediaTypes.add(MediaType.IMAGE_PNG);
		mediaTypes.add(MediaType.IMAGE_GIF);
		mediaTypes.add(MediaType.parseMediaType("application/pdf"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-excel"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		mediaTypes.add(MediaType.parseMediaType("application/vnd.ms-fontobject"));
		mediaTypes.add(MediaType.parseMediaType("image/svg+xml"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-ttf"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff"));
		mediaTypes.add(MediaType.parseMediaType("application/x-font-woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/woff"));
		mediaTypes.add(MediaType.parseMediaType("application/woff2"));
		mediaTypes.add(MediaType.parseMediaType("application/octet-stream"));
		bam.setSupportedMediaTypes(mediaTypes);
		converters.add(bam);
	}


	@Bean
	@Description("Multipart Resolver")
	public MultipartResolver filterMultipartResolver() {
		return new CommonsMultipartResolver();
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		FileSystemResource[] resources = new FileSystemResource[] {
				new FileSystemResource(PROPERTY_PATH + BaseConfigConstants.PROPERTIES_EXT) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}


	@Bean
	@Qualifier("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/portal/locale", "classpath:messages/portal/message",
				"classpath:messages/app_url", BaseConfigConstants.FILE_PFX + PROPERTY_PATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


	@Bean
	public MessageService messageService() {
		return new MessageService(messageSource());
	}


	@Bean
	public WebHelper webHelper() {
		return new WebHelper();
	}

}