/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.mail;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.baseboot.idm.sdk.util.CryptoUtil;
import com.baseboot.report.util.ConfigConstants;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Component
public class MailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Autowired
	MessageSource messageSource;

	@Value("${" + ConfigConstants.MAIL_CONF_HOST + "}")
	private String host;

	@Value("${" + ConfigConstants.MAIL_CONF_PORT + "}")
	private String port;

	@Value("${" + ConfigConstants.MAIL_CONF_UNAME + "}")
	private String username;

	@Value("${" + ConfigConstants.MAIL_CONF_PWORD + "}")
	private String password;

	@Value("${" + ConfigConstants.MAIL_CONF_SENDER + "}")
	private String emailFrom;

	@Value("${" + ConfigConstants.SVC_IDM_SKEY + "}")
	private String skey;

	private static VelocityEngine velocityEngine;


	public MailService() throws VelocityException, IOException {
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		factory.setVelocityProperties(props);
		velocityEngine = factory.createVelocityEngine();
	}


	public void sendMailUsingVelocity(final String subject, final String emailTO, final Map<String, Object> emailParam,
			final String templateLocation) {
		sendMailUsingVelocity(subject, emailTO, null, emailParam, templateLocation);
	}


	public void sendMailUsingVelocity(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateLocation) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.putAll(emailParam);
		String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "utf-8",
				model);
		sendMail(subject, emailTO, emailCC, content, true, null);
	}


	public void sendMailPlainText(final String subject, final String emailTO, final String emailCC,
			final Object content) {
		sendMail(subject, emailTO, emailCC, content, false, null);
	}


	public void sendMailUsingVelocityWithAttach(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateLocation, List<MailAttachment> attachFiles) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.putAll(emailParam);
		LOGGER.debug("sendMailUsingVelocityWithAttach: {}", new ObjectMapper().valueToTree(model));
		String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "utf-8",
				model);
		sendMail(subject, emailTO, emailCC, content, true, attachFiles);
	}


	private void sendMail(final String subject, final String emailTO, final String emailCC, final Object content,
			final boolean isHTML, List<MailAttachment> attachFiles) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, CryptoUtil.decrypt(password, skey));
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTO));
			if (emailCC != null && "".equals(emailCC))
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailCC));
			message.setSubject(subject);
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, isHTML ? "text/html; charset=utf-8" : "text/plain; charset=utf-8");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			if (attachFiles != null && attachFiles.size() > 0) {
				for (MailAttachment src : attachFiles) {
					DataSource dataSource = new ByteArrayDataSource(src.getFile(), "application/pdf");
					MimeBodyPart attachPart = new MimeBodyPart();
					attachPart.setDataHandler(new DataHandler(dataSource));
					attachPart.setFileName(src.getFileName() + ".pdf");
					multipart.addBodyPart(attachPart);
				}
			}

			// sets the multi-part as e-mail's content
			message.setContent(multipart);

			Transport.send(message);
			LOGGER.info("E-mail was send successfully.");
		} catch (MessagingException e) {
			LOGGER.error("MessagingException", e);
			throw new RuntimeException(e);
		}
	}


	public String getHost() {
		return host;
	}


	public String getPort() {
		return port;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public String getEmailFrom() {
		return emailFrom;
	}


	@Override
	public String toString() {
		return " Host: " + host + " - Port: " + port + " - Username: " + username + "- Password: " + password
				+ " - Sender: " + emailFrom;
	}

}