/**
 * Copyright 2019. 
 */
package com.notify.config;


import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import com.notify.constants.ConfigConstants;
import com.util.CryptoUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;



/**
 * The Class ThymeleafMailConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
@ComponentScan(ConfigConstants.BASE_PACKAGE)
@EnableWebMvc
public class ThymeleafMailConfig extends WebMvcConfigurerAdapter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafMailConfig.class);

	/** The Constant TEMPLATE_PATH. */
	public static final String TEMPLATE_PATH;

	static {
		String propertyHome = PersistenceConfig.PROPERTY_PATH.replace(ConfigConstants.PROPERTY_FILENAME, "");
		TEMPLATE_PATH = propertyHome.concat(ConfigConstants.MAIL_TEMPLATE_PATH);
	}


	/**
	 * Mail sender.
	 *
	 * @param messageSource the message source
	 * @return the java mail sender
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Bean
	public JavaMailSender mailSender(MessageSource messageSource) throws IOException {
		String skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
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
		LOGGER.info("{}", sb);

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


	/**
	 * Html template engine.
	 *
	 * @return the template engine
	 */
	@Bean
	public TemplateEngine htmlTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}


	/**
	 * String template engine.
	 *
	 * @return the template engine
	 */
	@Bean
	public TemplateEngine stringTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(stringTemplateResolver());
		return templateEngine;
	}


	/**
	 * Html template resolver.
	 *
	 * @return the i template resolver
	 */
	@Description("Template Resolver for HTML email templates")
	private ITemplateResolver htmlTemplateResolver() {
		LOGGER.info("Template Path: {}", TEMPLATE_PATH);

		FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
		fileTemplateResolver.setPrefix(TEMPLATE_PATH);
		fileTemplateResolver.setSuffix(".html");
		fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
		fileTemplateResolver.setCharacterEncoding(ConfigConstants.ENCODING);
		fileTemplateResolver.setCacheable(false);
		return fileTemplateResolver;
	}


	/**
	 * String template resolver.
	 *
	 * @return the i template resolver
	 */
	@Description("Template Resolver for String templates. (template will be a passed String -- for editable templates)")
	private ITemplateResolver stringTemplateResolver() {
		StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

}