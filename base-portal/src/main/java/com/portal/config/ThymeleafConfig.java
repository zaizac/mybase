/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.portal.config;


import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.FlowAjaxThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.dialect.CustomBstDialect;
import com.util.constants.BaseConfigConstants;


/**
 * @author mary.jane
 * @since Jan 30, 2019
 */
@Configuration
public class ThymeleafConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafConfig.class);

	@Autowired
	MessageSource messageSource;


	@Bean
	@Description("Thymeleaf Template Resolver using HTML 5")
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix(BaseConfigConstants.THYMELEAF_TEMPLATE_PROJECT_LOCATION);
		resolver.setSuffix(BaseConfigConstants.HTML_SFX);
		resolver.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
		LOGGER.debug("thymleaf.template.cacheable: {}", messageSource
				.getMessage(BaseConfigConstants.THYMELEAF_TEMPLATE_CACHEABLE, null, Locale.getDefault()));
		resolver.setCacheable(Boolean.valueOf(messageSource
				.getMessage(BaseConfigConstants.THYMELEAF_TEMPLATE_CACHEABLE, null, Locale.getDefault())));
		return resolver;
	}


	@Bean
	@Description("Thymeleaf Template Resolver using HTML 5")
	public ServletContextTemplateResolver defaultTemplateResolver() {
		final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix(BaseConfigConstants.THYMELEAF_TEMPLATE_DEFAULT_LOCATION);
		resolver.setSuffix(BaseConfigConstants.HTML_SFX);
		resolver.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
		resolver.setOrder(0);
		LOGGER.debug("thymleaf.template.cacheable: {}", messageSource
				.getMessage(BaseConfigConstants.THYMELEAF_TEMPLATE_CACHEABLE, null, Locale.getDefault()));
		resolver.setCacheable(Boolean.valueOf(messageSource
				.getMessage(BaseConfigConstants.THYMELEAF_TEMPLATE_CACHEABLE, null, Locale.getDefault())));
		return resolver;
	}


	@Bean
	@Description("Thymeleaf Template Engine with Spring Integration")
	public SpringTemplateEngine templateEngine() {
		Set<IDialect> dialects = new HashSet<>();
		dialects.add(new TilesDialect());
		dialects.add(new SpringSecurityDialect());
		dialects.add(new ConditionalCommentsDialect());
		dialects.add(new CustomBstDialect());

		Set<ITemplateResolver> templates = new HashSet<>();
		templates.add(defaultTemplateResolver());
		templates.add(templateResolver());

		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolvers(templates);
		engine.setAdditionalDialects(dialects);
		engine.setMessageSource(messageSource);
		return engine;
	}


	@Bean
	@Description("Ajax Thymeleaf View Resolver")
	public AjaxThymeleafViewResolver tilesViewResolver() {
		AjaxThymeleafViewResolver resolver = new AjaxThymeleafViewResolver();
		resolver.setViewClass(FlowAjaxThymeleafTilesView.class);
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(-1);
		return resolver;
	}


	@Bean
	@Description("Spring Resource Template")
	public SpringResourceTemplateResolver thymeleafSpringResource() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
		return resolver;
	}


	@SuppressWarnings("deprecation")
	@Bean
	@Description("Tiles Configurer")
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
//		configurer.setDefinitions(BaseConfigConstants.TILES_DEFAULT_LOCATION,
//				BaseConfigConstants.TILES_PROJECT_LOCATION);
		return configurer;
	}

}
