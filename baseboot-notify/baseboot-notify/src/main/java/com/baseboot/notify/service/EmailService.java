/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.baseboot.notify.controller.GenericAbstract;
import com.baseboot.notify.model.NotEmailTemplate;
import com.baseboot.notify.sdk.constants.BaseConstants;
import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.notify.sdk.constants.NotErrorCodeEnum;
import com.baseboot.notify.sdk.exception.NotificationException;
import com.baseboot.notify.sdk.model.MailAttachment;
import com.baseboot.notify.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service
public class EmailService extends GenericAbstract {

	private static final String MAIL_HEADER = "images/logo.png";

	private static final String JPG_MIME = "image/jpg";

	@Value("${mail.from}")
	private String emailFrom;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine htmlTemplateEngine;

	@Autowired
	private TemplateEngine stringTemplateEngine;

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	NotEmailTemplateService notEmailTemplateSvc;


	// added asif start for mail test
	public void sendSimpleMailTest(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateCode, final List<MailAttachment> attachments)
			throws MessagingException {
		// Prepare the evaluation context

		final Context ctx = new Context(Locale.getDefault());
		ctx.setVariables(getDefaultSettings());
		ctx.setVariables(emailParam);
		NotEmailTemplate temp = notEmailTemplateSvc.findByTemplateType(templateCode);
		if (BaseUtil.isObjNull(temp)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}
		ctx.setVariable("content", stringTemplateEngine.process(temp.getEmailContent(), ctx));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject(temp.getEmailSubject());
		message.setFrom(emailFrom);
		message.setTo(InternetAddress.parse(emailTO));
		if (!BaseUtil.isObjNull(emailCC)) {
			message.setCc(InternetAddress.parse(emailCC));
		}
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("mail-layout", ctx);
		message.setText(htmlContent, true /* isHtml */);

		message.addInline("MAIL_HEADER", new ClassPathResource(MAIL_HEADER), JPG_MIME);

		// Add the attachment
		if (!BaseUtil.isListNull(attachments)) {
			for (MailAttachment file : attachments) {
				if (!BaseUtil.isObjNull(file)) {
					final InputStreamSource attachmentSource = new ByteArrayResource(file.getFile());
					message.addAttachment(file.getFileName(), attachmentSource, file.getMimeType());
				}
			}
		}

		// Send email
		this.mailSender.send(mimeMessage);
	}


	public void sendSimpleMail(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateCode, final List<MailAttachment> attachments)
			throws MessagingException {
		// Prepare the evaluation context
		final Context ctx = new Context(Locale.getDefault());
		ctx.setVariables(getDefaultSettings());
		ctx.setVariables(emailParam);
		NotEmailTemplate temp = notEmailTemplateSvc.findByTemplateType(templateCode);
		if (BaseUtil.isObjNull(temp)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}
		ctx.setVariable("content", stringTemplateEngine.process(temp.getEmailContent(), ctx));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject(subject);
		message.setFrom(emailFrom);
		message.setTo(InternetAddress.parse(emailTO));
		if (!BaseUtil.isObjNull(emailCC))
			message.setCc(InternetAddress.parse(emailCC));

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("mail-layout", ctx);
		message.setText(htmlContent, true /* isHtml */);

		message.addInline("MAIL_HEADER", new ClassPathResource(MAIL_HEADER), JPG_MIME);

		// Add the attachment
		if (!BaseUtil.isListNull(attachments)) {
			for (MailAttachment file : attachments) {
				if (!BaseUtil.isObjNull(file)) {
					final InputStreamSource attachmentSource = new ByteArrayResource(file.getFile());
					message.addAttachment(file.getFileName(), attachmentSource, file.getMimeType());
				}
			}
		}

		// Send email
		this.mailSender.send(mimeMessage);
	}


	private Map<String, Object> getDefaultSettings() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("GEN_DATE", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_Slash_TIME_a).format(new Date()));
		map.put("CUST_CARE_CONTACT", getConfigValue("CUST_CARE_CONTACT"));
		map.put("CUST_CARE_EMAIL", getConfigValue("CUST_CARE_EMAIL"));
		map.put("BG_URL", getConfigValue("BG_URL"));
		map.put("PORTAL_EMP_URL", getConfigValue("PORTAL_EMP_URL"));
		map.put("PORTAL_SCO_URL", getConfigValue("PORTAL_SCO_URL"));
		map.put("PORTAL_FWCMS_URL", getConfigValue("PORTAL_FWCMS_URL"));
		map.put("PORTAL_MIGRAMS_URL", getConfigValue("PORTAL_MIGRAMS_URL"));
		map.put("PORTAL_EMP_URL_ACTIVATE", getConfigValue("PORTAL_EMP_URL_ACTIVATE"));
		map.put("MI_ADDRESS", getConfigValue("MI_ADDRESS"));
		map.put("MI_PORTAL", getConfigValue("MI_PORTAL"));
		map.put("MI_FAX", getConfigValue("MI_FAX"));
		map.put("PHONE_NUM", getConfigValue("PHONE_NUM"));
		return map;
	}


	public void sendSimpleMail(final MailTemplate mailTemp, Map<String, Object> body, final String recipientName,
			final String recipientEmail, final Locale locale) throws MessagingException {
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariables(getDefaultSettings());
		ctx.setVariables(body);
		ctx.setVariable("content", mailTemp.getLocation());

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject(mailTemp.getSubject());
		message.setFrom(emailFrom);
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("mail-layout", ctx);
		message.setText(htmlContent, true /* isHtml */);

		message.addInline("MAIL_HEADER", new ClassPathResource(MAIL_HEADER), JPG_MIME);

		// Send email
		this.mailSender.send(mimeMessage);
	}

}