/**
 * Copyright 2019. 
 */
package com.notify.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.notify.model.NotConfig;
import com.notify.model.NotEmailTemplate;
import com.notify.sdk.constants.NotErrorCodeEnum;
import com.notify.sdk.constants.NotUrlConstants;
import com.notify.sdk.exception.NotificationException;
import com.notify.sdk.model.EmailTemplate;
import com.notify.service.NotEmailTemplateService;
import com.util.BaseUtil;
import com.util.JsonUtil;



/**
 * The Class MailTemplateRestController.
 *
 * @author mary.jane
 * @since Dec 12, 2018
 */
@RestController
public class MailTemplateRestController extends AbstractRestController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailTemplateRestController.class);

	/** The not E mail template svc. */
	@Autowired
	private NotEmailTemplateService notEMailTemplateSvc;


	/**
	 * Notify config.
	 *
	 * @param request the request
	 * @return the map
	 */
	@GetMapping(value = NotUrlConstants.NOTIFY_CONFIG, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> notifyConfig(HttpServletRequest request) {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start("Send Email");
		List<NotConfig> confLst = notConfigSvc.primaryDao().findAll();
		Map<String, String> config = new HashMap<>();
		if (!confLst.isEmpty()) {
			for (NotConfig conf : confLst) {
				config.put(conf.getConfigCode(), conf.getConfigVal());
			}
		}
		stopwatch.stop();
		String stopwatchText = stopwatch.prettyPrint();
		LOGGER.debug("END :: {}", stopwatchText);
		return config;
	}


	/**
	 * Get Email Template.
	 *
	 * @param request the request
	 * @return the all mail template
	 */
	@GetMapping(value = NotUrlConstants.MAIL_TEMPLATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<NotEmailTemplate> getAllMailTemplate(HttpServletRequest request) {
		return notEMailTemplateSvc.primaryDao().findAll();
	}


	/**
	 * Update Email Template.
	 *
	 * @param emailTemplate the email template
	 * @param request the request
	 * @return the not email template
	 */
	@PostMapping(value = NotUrlConstants.MAIL_TEMPLATE_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public NotEmailTemplate updateEmailTemplate(@Valid @RequestBody EmailTemplate emailTemplate,
			HttpServletRequest request) {
		return updateEmailTemplate(emailTemplate, getCurrUserId(request));
	}


	/**
	 * Add Email Template.
	 *
	 * @param templateType the template type
	 * @param emailSubject the email subject
	 * @param emailContent the email content
	 * @param createId the create id
	 * @return the not email template
	 */
	public NotEmailTemplate addEmailTemplate(String templateType, String emailSubject, String emailContent,
			String createId) {
		LOGGER.info("Add Email Template. ");
		NotEmailTemplate notEmailTemplate = new NotEmailTemplate();
		notEmailTemplate.setTemplateType(templateType);
		notEmailTemplate.setEmailSubject(emailSubject);
		notEmailTemplate.setEmailContent(emailContent);
		notEmailTemplate.setCreateId(createId);
		notEmailTemplate.setCreateDt(getSQLTimestamp());
		notEmailTemplate.setUpdateId(createId);
		notEmailTemplate.setUpdateDt(getSQLTimestamp());

		NotEmailTemplate cmnNotEmailTemplate;
		try {
			cmnNotEmailTemplate = notEMailTemplateSvc.primaryDao().save(notEmailTemplate);
		} catch (Exception e) {
			LOGGER.error("Exception:addEmailTemplate: ", e);
			throw new NotificationException(NotErrorCodeEnum.E404NOT004);
		}
		return cmnNotEmailTemplate;
	}


	/**
	 * Update email template.
	 *
	 * @param emailTemplate the email template
	 * @param createId the create id
	 * @return the not email template
	 */
	public NotEmailTemplate updateEmailTemplate(EmailTemplate emailTemplate, String createId) {
		NotEmailTemplate notEmailTemplate = notEMailTemplateSvc.findNotEmailTemplateById(emailTemplate.getId());
		notEmailTemplate.setEmailSubject(emailTemplate.getEmailSubject());
		notEmailTemplate.setEmailContent(emailTemplate.getEmailContent());
		notEmailTemplate.setUpdateDt(getSQLTimestamp());
		notEmailTemplate.setCreateId(createId);

		NotEmailTemplate cmnNotEmailTemplate;
		try {
			cmnNotEmailTemplate = notEMailTemplateSvc.primaryDao().saveAndFlush(notEmailTemplate);
		} catch (Exception e) {
			LOGGER.error("Exception:updateEmailTemplate: ", e);
			throw new NotificationException(NotErrorCodeEnum.E404NOT004);
		}

		return cmnNotEmailTemplate;
	}

	
	/**
	 * Search Email Template
	 * 
	 * @param emailTemplate
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = NotUrlConstants.MAIL_TEMPLATE_SEARCH, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<EmailTemplate> search ( @Valid @RequestBody EmailTemplate emailTemplate, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(emailTemplate)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}
		
		// Retrieve Not Email Template
		List<NotEmailTemplate> notEmailTemplates = notEMailTemplateSvc.search(emailTemplate);
				
		if (Boolean.TRUE.equals(BaseUtil.isListNull(notEmailTemplates))) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}

		try {
			return JsonUtil.transferToList(notEmailTemplates, EmailTemplate.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}
}
