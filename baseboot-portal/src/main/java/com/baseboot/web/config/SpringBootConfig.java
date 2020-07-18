/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.web.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
@EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class, ThymeleafAutoConfiguration.class })
public class SpringBootConfig extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootConfig.class);

	@Autowired
	MessageSource messageSource;


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootConfig.class, WebMvcConfig.class, SecurityConfig.class,
				ServiceConfig.class);
	}


	public static void main(String[] args) {
		LOG.info("IDM Spring Boot started...");
		SpringApplication app = new SpringApplication(SpringBootConfig.class, WebMvcConfig.class,
				SecurityConfig.class, ServiceConfig.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}


	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setPort(8080), connector -> {
			Object defaultMaxThreads = connector.getAttribute("maxThreads");
			connector.setAttribute("maxThreads", 100);
			LOG.info("Changed Tomcat connector maxThreads from {} to {}", defaultMaxThreads, 100);
		});
		return factory;
	}

}