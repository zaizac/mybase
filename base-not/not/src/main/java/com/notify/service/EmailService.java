/**
 * Copyright 2019. 
 */
package com.notify.service;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.dm.sdk.model.Documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.config.ThymeleafMailConfig;
import com.notify.constants.ApplicationConstants;
import com.notify.constants.ConfigConstants;
import com.notify.controller.GenericAbstract;
import com.notify.model.NotConfig;
import com.notify.model.NotEmailTemplate;
import com.notify.sdk.client.NotRestTemplate;
import com.notify.sdk.constants.NotConfigConstants;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.exception.NotificationException;
import com.notify.sdk.model.Attachment;
import com.notify.sdk.model.ByteAttachment;
import com.notify.sdk.model.DmAttachment;
import com.notify.sdk.model.ReportAttachment;
import com.util.BaseUtil;
import com.util.HttpRestUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.http.HttpAuthClient;
import com.util.model.Report;



/**
 * The Class EmailService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service
public class EmailService extends GenericAbstract {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	/** The email from. */
	@Value("${" + ConfigConstants.MAIL_CONF_SENDER + "}")
	private String emailFrom;

	/** The rpt url. */
	@Value("${" + ConfigConstants.SVC_RPT_URL + "}")
	private String rptUrl;

	/** The rpt timeout. */
	@Value("${" + ConfigConstants.SVC_RPT_TIMEOUT + "}")
	private int rptTimeout;

	/** The client id. */
	@Value("${" + BaseConfigConstants.SVC_IDM_CLIENT + "}")
	private String clientId;

	/** The token. */
	@Value("${" + BaseConfigConstants.SVC_IDM_SKEY + "}")
	private String token;

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;

	/** The html template engine. */
	@Autowired
	private TemplateEngine htmlTemplateEngine;

	/** The string template engine. */
	@Autowired
	private TemplateEngine stringTemplateEngine;

	/** The not email template svc. */
	@Autowired
	NotEmailTemplateService notEmailTemplateSvc;


	/**
	 * Send simple mail.
	 *
	 * @param subject the subject
	 * @param emailTO the email TO
	 * @param emailCC the email CC
	 * @param emailParam the email param
	 * @param templateCode the template code
	 * @param attachments the attachments
	 * @throws MessagingException the messaging exception
	 */
	public void sendSimpleMail(final String subject, final String emailTO, final String emailCC,
			final Map<String, Object> emailParam, final String templateCode, final List<Attachment> attachments, String systemType)
			throws MessagingException {
		if (!BaseUtil.isObjNull(systemType)) {
			systemType = BaseUtil.getStrLower(systemType);
			emailParam.put("systemType", systemType);
		} else {
			emailParam.put("systemType", "");
		}
		
		// Prepare the evaluation context
		final Context ctx = new Context(Locale.getDefault());
		ctx.setVariables(getDefaultSettings());
		ctx.setVariables(emailParam);

		NotEmailTemplate temp = notEmailTemplateSvc.findByTemplateCode(templateCode);
		if (BaseUtil.isObjNull(temp)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}

		LOGGER.info("NotEmailTemplate: {}", temp);
		ctx.setVariable(ApplicationConstants.CONTENT, stringTemplateEngine.process(temp.getEmailContent(), ctx));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, ConfigConstants.ENCODING);
		message.setSubject(subject);
		message.setFrom(emailFrom);
		message.setTo(InternetAddress.parse(emailTO));
		if (!BaseUtil.isObjNull(emailCC)) {
			message.setCc(InternetAddress.parse(emailCC));
		}

		String mailLayout = getConfigValue(NotConfigConstants.MAIL_LAYOUT);
		if (!BaseUtil.isObjNull(systemType)) {
			message.setFrom(messageSource.getMessage("mail.from." + systemType, null, Locale.getDefault()));
			mailLayout = systemType + "-mail-layout";
		}
		LOGGER.info("Mail layout: {}", mailLayout);

		// Create the HTML body using Thymeleaf
		final String htmlContent = htmlTemplateEngine
				.process(!BaseUtil.isObjNull(mailLayout) ? mailLayout : ApplicationConstants.MAIL_LAYOUT, ctx);
		message.setText(htmlContent, true /* isHtml */);

		String mailHeaderPath = getConfigValue(NotConfigConstants.MAIL_HEADER_PATH);
		if (!BaseUtil.isObjNull(systemType)) {
			mailHeaderPath = "images/" + systemType + "/logo.png";
		}
		LOGGER.info("mailHeaderPath: {}", mailHeaderPath);
		File file = new File(ThymeleafMailConfig.TEMPLATE_PATH.concat(mailHeaderPath));
		message.addInline(ApplicationConstants.MAIL_HEADER, file);

		// Add the attachment
		if (!BaseUtil.isListNull(attachments)) {
			getAttachments(attachments, message);
		}

		// Send email
		mailSender.send(mimeMessage);
	}


	/**
	 * Gets the attachments.
	 *
	 * @param attachments the attachments
	 * @param message the message
	 * @return the attachments
	 */
	private void getAttachments(List<Attachment> attachments, final MimeMessageHelper message) {
		for (Attachment file : attachments) {
			// File Byte Attachment
			if (file instanceof ByteAttachment) {
				byteAttachment((ByteAttachment) file, message);
			}

			// DM Attachment
			if (file instanceof DmAttachment) {
				docMgtAttachment((DmAttachment) file, message);
			}

			// Rest Service Call Attachment
			if (file instanceof ReportAttachment) {
				reportAttachment((ReportAttachment) file, message);
			}
		}
	}


	/**
	 * Byte attachment.
	 *
	 * @param file the file
	 * @param message the message
	 */
	private void byteAttachment(ByteAttachment file, final MimeMessageHelper message) {
		if (!BaseUtil.isObjNull(file) && !BaseUtil.isObjNull(file.getFile())) {
			final InputStreamSource attachmentSource = new ByteArrayResource(file.getFile());
			try {
				message.addAttachment(file.getFileName(), attachmentSource, file.getMimeType());
			} catch (MessagingException e) {
				LOGGER.error("Byte Attachment: {}", e);
			}
		}
	}


	/**
	 * Doc mgt attachment.
	 *
	 * @param file the file
	 * @param message the message
	 */
	private void docMgtAttachment(DmAttachment file, final MimeMessageHelper message) {
		if (!BaseUtil.isObjNull(file) && !BaseUtil.isObjNull(file.getDocCollection())
				&& !BaseUtil.isObjNull(file.getDocMgtId())) {
			Documents doc = getDmService().download(file.getDocCollection(), file.getDocMgtId());
			final InputStreamSource attachmentSource = new ByteArrayResource(doc.getContent());
			try {
				LOGGER.info("Filename: {} - Content Type: {}", doc.getFilename(), doc.getContentType());
				message.addAttachment(doc.getFilename(), attachmentSource, doc.getContentType());
			} catch (MessagingException e) {
				LOGGER.error("DM Attachment: {}", e);
			}
		}
	}


	/**
	 * Report attachment.
	 *
	 * @param restCall the rest call
	 * @param message the message
	 */
	private void reportAttachment(ReportAttachment restCall, final MimeMessageHelper message) {
		// Rest Service Call Attachment - This is specifically for Report
		// Service only
		if (!BaseUtil.isObjNull(restCall)) {
			String url = rptUrl.concat(restCall.getUrl());
			LOGGER.info("Getting Report from: {}", url);
			LOGGER.debug("Getting Report from: {}", new ObjectMapper().valueToTree(restCall.getRequestParams()));
			Report result = null;
			// Retrieve report
			if (BaseUtil.isEqualsCaseIgnore(HttpMethod.GET.name(), restCall.getHttpMethod())) {
				result = getRestTemplate()
						.getForObject(HttpRestUtil.getServiceURI(url, restCall.getRequestParams()), Report.class);
			} else if (BaseUtil.isEqualsCaseIgnore(HttpMethod.POST.name(), restCall.getHttpMethod())) {
				result = getRestTemplate().postForObject(
						HttpRestUtil.getServiceURI(url, restCall.getRequestParams()), restCall.getRequestBody(),
						Report.class);
			}

			// Attach the report document
			if (result != null) {
				final InputStreamSource attachmentSource = new ByteArrayResource(result.getReportBytes());
				try {
					LOGGER.info("Filename: {} - Content Type: {}", result.getFileName(), result.getReportType());
					message.addAttachment(result.getFileName(), attachmentSource,
							MediaType.APPLICATION_JSON_VALUE);
				} catch (MessagingException e) {
					LOGGER.error("Report Attachment: {}", e);
				}
			}
		}
	}


	/**
	 * Gets the rest template.
	 *
	 * @return the rest template
	 */
	private NotRestTemplate getRestTemplate() {
		String messageId = String.valueOf(UUID.randomUUID());
		CloseableHttpClient httpClient = new HttpAuthClient(clientId, token, messageId, rptTimeout).getHttpClient();
		NotRestTemplate restTemplate = new NotRestTemplate("RPT");
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	/**
	 * Gets the default settings.
	 *
	 * @return the default settings
	 */
	private Map<String, Object> getDefaultSettings() {
		Map<String, Object> map = new HashMap<>();
		map.put("GEN_DATE", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A).format(new Date()));

		List<NotConfig> configs = notConfigSvc.findAll();

		if (!BaseUtil.isListNullZero(configs)) {
			for (NotConfig config : configs) {
				map.put(config.getConfigCode(), config.getConfigVal());
			}
		}

		return map;
	}

}