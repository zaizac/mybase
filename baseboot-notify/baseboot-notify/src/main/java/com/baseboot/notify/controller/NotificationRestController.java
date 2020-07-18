/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.notify.model.NotConfig;
import com.baseboot.notify.model.NotEmailTemplate;
import com.baseboot.notify.model.NotPayload;
import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.notify.sdk.constants.MailTypeEnum;
import com.baseboot.notify.sdk.constants.NotConfigConstants;
import com.baseboot.notify.sdk.constants.NotErrorCodeEnum;
import com.baseboot.notify.sdk.constants.NotUrlConstants;
import com.baseboot.notify.sdk.constants.SmsTemplate;
import com.baseboot.notify.sdk.exception.NotificationException;
import com.baseboot.notify.sdk.model.EmailTemplate;
import com.baseboot.notify.sdk.model.MailAttachment;
import com.baseboot.notify.sdk.model.Notification;
import com.baseboot.notify.sdk.model.Sms;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.baseboot.notify.service.NotEmailTemplateService;
import com.baseboot.notify.service.NotPayloadService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class NotificationRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRestController.class);

	@Autowired
	private NotPayloadService notificationSvc;

	@Autowired
	private NotEmailTemplateService notEMailTemplateSvc;


	/**
	 * Get Email Template
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NotUrlConstants.MAIL_TEMPLATE, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<NotEmailTemplate> getAllMailTemplate(HttpServletRequest request) throws Exception {
		List<NotEmailTemplate> notEmailTemplateList = notEMailTemplateSvc.primaryDao().findAll();
		return notEmailTemplateList;
	}


	/**
	 * Update Email Template
	 *
	 * @param emailTemplate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NotUrlConstants.UPDATE_MAIL_TEMPLATE, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public NotEmailTemplate updateEmailTemplate(@Valid @RequestBody EmailTemplate emailTemplate,
			HttpServletRequest request) throws Exception {
		return updateEmailTemplate(emailTemplate, getCurrUserId(request));
	}


	/**
	 * Add Notification
	 *
	 * @param templateCode
	 * @param notifyType
	 * @param notification
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NotUrlConstants.NOTIFY, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public NotPayload add(@RequestParam("templateCode") final String templateCode,
			@RequestParam("notifyType") final String notifyType, @Valid @RequestBody Notification notification,
			HttpServletRequest request) throws Exception {
		LOGGER.info("Add Notification: Template: {} - Notify Type: {}", templateCode, notifyType);
		return addNotification(notification.getSubject(), notification.getNotifyTo(), notification.getNotifyCc(),
				notifyType, notification.getMetaData(), templateCode, getCurrUserId(request),
				notification.getAttachment());
	}


	/**
	 * Send All
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = NotUrlConstants.NOTIFY_SEND_ALL, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<NotPayload> sendAll() {
		return notificationSvc.sendAll();
	}


	@RequestMapping(value = NotUrlConstants.NOTIFY_CONFIG, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> notifyConfig(HttpServletRequest request) throws Exception {
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
		LOGGER.debug("END :: {}", stopwatch.prettyPrint());
		return config;
	}


	@RequestMapping(value = NotUrlConstants.NOTIFY_MAIL_SEND, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean sendMail(@RequestParam(value = "templateCode", required = true) final String templateCode,
			@Valid @RequestBody Notification notification, HttpServletRequest request) throws Exception {
		if (BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400EQM003);
		}

		StopWatch stopwatch = new StopWatch();
		stopwatch.start("Send Email");

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		try {
			map = mapper.readValue(notification.getMetaData(), typeRef);
			emailService.sendSimpleMail(notification.getSubject(), notification.getNotifyTo(),
					notification.getNotifyCc(), map, templateCode, null);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return false;
		}

		stopwatch.stop();
		LOGGER.info(stopwatch.prettyPrint());
		return true;
	}


	@RequestMapping(value = NotUrlConstants.NOTIFY_SMS_SEND, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Sms send(@Valid @RequestBody Sms sms, HttpServletRequest request) throws Exception {
		boolean hasSmsNotif = Boolean.valueOf(getConfigValue(NotConfigConstants.SMS_SWITCH));
		if (!hasSmsNotif) {
			throw new NotificationException(NotErrorCodeEnum.E503NOT004);
		}

		StopWatch stopwatch = new StopWatch();
		stopwatch.start(SmsTemplate.SMS_REALTIME.name());
		try {
			smsManagerService.sendOTPThroughICE(sms);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			addNotification("", sms.getMobileNo(), null, MailTypeEnum.SMS.name(), sms.getContent(),
					SmsTemplate.SMS_REALTIME.name(), getCurrUserId(request), null);
		}
		stopwatch.stop();
		LOGGER.info("END :: {}", stopwatch.prettyPrint());
		return sms;
	}


	public NotPayload addNotification(String subject, String emailTo, String emailCc, String notifyType,
			Object metaData, String templateName, String createUserId, List<MailAttachment> attachments)
			throws Exception {
		NotPayload notification = new NotPayload();
		notification.setStatus("0");
		notification.setSubject(BaseUtil.isObjNull(subject) ? "" : subject);
		notification.setNotifyTo(emailTo);
		notification.setNotifyCc(emailCc);
		notification.setNotifyType(notifyType);
		notification.setTemplateCode(templateName);
		notification.setCreateId(createUserId);
		notification.setUpdateId(createUserId);
		notification.setUpdateDt(getSQLTimestamp());
		notification.setCreateDt(getSQLTimestamp());
		notification.setRtryCount(0);
		LOGGER.debug("Attachment: {}", attachments);

		if (attachments != null) {
			notification.setAttachment(new ObjectMapper().writeValueAsString(attachments));
		}

		notification.setMetaData(BaseUtil.getStr(metaData));

		NotPayload cmnNotification = null;
		if (BaseUtil.isEqualsCaseIgnore(MailTemplate.CMN_OTP.name(), templateName)) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<>();
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			try {
				map = mapper.readValue(notification.getMetaData(), typeRef);
				emailService.sendSimpleMail(notification.getSubject(), notification.getNotifyTo(),
						notification.getNotifyCc(), map, templateName, null);
				return notification;
			} catch (IOException e) {
				LOGGER.error("IOException: {}", e.getMessage());
			}
		} else {
			try {
				cmnNotification = notificationSvc.primaryDao().save(notification);
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
				throw new NotificationException(NotErrorCodeEnum.E404NOT002);
			}
		}

		return cmnNotification;
	}


	/**
	 * Add Email Template
	 *
	 * @param templateType
	 * @param emailSubject
	 * @param emailContent
	 * @param createId
	 * @return
	 * @throws Exception
	 */
	public NotEmailTemplate addEmailTemplate(String templateType, String emailSubject, String emailContent,
			String createId) throws Exception {
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
			LOGGER.info(e.getMessage());
			throw new NotificationException(NotErrorCodeEnum.E404NOT004);
		}
		return cmnNotEmailTemplate;
	}


	public NotEmailTemplate updateEmailTemplate(EmailTemplate emailTemplate, String createId) throws Exception {
		NotEmailTemplate notEmailTemplate = notEMailTemplateSvc.findNotEmailTemplateById(emailTemplate.getId());
		notEmailTemplate.setEmailSubject(emailTemplate.getEmailSubject());
		notEmailTemplate.setEmailContent(emailTemplate.getEmailContent());
		notEmailTemplate.setUpdateDt(getSQLTimestamp());

		NotEmailTemplate cmnNotEmailTemplate;
		try {
			cmnNotEmailTemplate = notEMailTemplateSvc.primaryDao().saveAndFlush(notEmailTemplate);
		} catch (Exception e) {
			LOGGER.info("Exception", e);
			throw new NotificationException(NotErrorCodeEnum.E404NOT004);
		}

		return cmnNotEmailTemplate;
	}


	@RequestMapping(value = NotUrlConstants.GET_CONFIG_VAL_BY_CONFIG_CODE, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getConfiValByConfigCode(@RequestParam(value = "configCode", required = true) final String configCode,
			HttpServletRequest request) throws Exception {
		return getConfigValue(configCode);
	}

}