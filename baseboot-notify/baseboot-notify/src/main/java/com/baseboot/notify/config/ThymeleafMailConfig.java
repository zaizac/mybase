/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.config;


import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.util.CryptoUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
// @ComponentScan(ConfigConstants.BASE_PACKAGE)
@EnableWebMvc
public class ThymeleafMailConfig implements WebMvcConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafMailConfig.class);

	@Autowired
	MessageSource messageSource;


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}


	@Bean
	public JavaMailSender mailSender() throws IOException {
		String skey = messageSource.getMessage(ConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String mailHost = messageSource.getMessage(ConfigConstants.MAIL_CONF_HOST, null, Locale.getDefault());
		String mailPort = messageSource.getMessage(ConfigConstants.MAIL_CONF_PORT, null, Locale.getDefault());
		String mailProtocol = messageSource.getMessage(ConfigConstants.MAIL_CONF_PROTOCOL, null, Locale.getDefault());
		String mailUname = messageSource.getMessage(ConfigConstants.MAIL_CONF_UNAME, null, Locale.getDefault());
		String mailPword = messageSource.getMessage(ConfigConstants.MAIL_CONF_PWORD, null, Locale.getDefault());

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Mail Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Host: " + mailHost + BaseConstants.NEW_LINE);
		sb.append("Port: " + mailPort + BaseConstants.NEW_LINE);
		sb.append("Protocol: " + mailProtocol + BaseConstants.NEW_LINE);
		sb.append("Username: " + mailUname + BaseConstants.NEW_LINE);
		sb.append("Password: " + mailPword);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(sb.toString());
		}

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(Integer.parseInt(mailPort));
		mailSender.setProtocol(mailProtocol);
		mailSender.setUsername(mailUname);
		mailSender.setPassword(CryptoUtil.decrypt(mailPword, skey));

		Properties properties = new Properties();
		properties.load(new ClassPathResource("javamail.properties").getInputStream());
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}


	@Bean
	public TemplateEngine htmlTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}


	@Bean
	public TemplateEngine stringTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(stringTemplateResolver());
		return templateEngine;
	}


	@Description("Template Resolver for HTML email templates")
	private ITemplateResolver htmlTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("./mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(ConfigConstants.ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}


	@Description("Template Resolver for String templates. (template will be a passed String -- for editable templates)")
	private ITemplateResolver stringTemplateResolver() {
		StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(false);
		return templateResolver;
	}


	@Bean
	public Mapper dozerMapper() {
		return new DozerBeanMapper();
	}

}