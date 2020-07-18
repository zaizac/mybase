/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.controller;


import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.notify.model.NotEmailTemplate;
import com.bstsb.notify.model.NotPayload;
import com.bstsb.notify.sdk.constants.MailTypeEnum;
import com.bstsb.notify.sdk.constants.NotConfigConstants;
import com.bstsb.notify.sdk.constants.NotErrorCodeEnum;
import com.bstsb.notify.sdk.constants.NotUrlConstants;
import com.bstsb.notify.sdk.constants.SmsTemplate;
import com.bstsb.notify.sdk.exception.NotificationException;
import com.bstsb.notify.sdk.model.Attachment;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.notify.sdk.model.Sms;
import com.bstsb.notify.service.NotEmailTemplateService;
import com.bstsb.notify.service.NotPayloadService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class NotificationRestController.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class NotificationRestController extends AbstractRestController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRestController.class);

	/** The notification svc. */
	@Autowired
	private NotPayloadService notificationSvc;

	/** The not E mail template svc. */
	@Autowired
	private NotEmailTemplateService notEMailTemplateSvc;


	/**
	 * Gets the confi val by config code.
	 *
	 * @param configCode
	 *             the config code
	 * @param request
	 *             the request
	 * @return the confi val by config code
	 */
	@GetMapping(value = NotUrlConstants.GET_CONFIG_VAL_BY_CONFIG_CODE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getConfiValByConfigCode(@RequestParam(value = "configCode", required = true) final String configCode,
			HttpServletRequest request) {
		return getConfigValue(configCode);
	}


	/**
	 * Send All.
	 *
	 * @param request
	 *             the request
	 * @return the list
	 */
	@GetMapping(value = NotUrlConstants.NOTIFY_SEND_ALL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<NotPayload> sendAll(HttpServletRequest request) {
		return notificationSvc.sendAll();
	}


	/**
	 * Send notification in real time.
	 *
	 * @param templateCode
	 *             the template code
	 * @param notification
	 *             the notification
	 * @param request
	 *             the request
	 * @return true, if successful
	 */
	@PostMapping(value = NotUrlConstants.NOTIFY_MAIL_SEND, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean sendMail(@RequestParam(value = "templateCode", required = true) final String templateCode,
			@Valid @RequestBody Notification notification, HttpServletRequest request) {
		if (BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}

		StopWatch stopwatch = new StopWatch();
		stopwatch.start("Send Email");

		// Retrieve Email Template
		NotEmailTemplate emailTemplate = notEMailTemplateSvc.findByTemplateCode(templateCode);

		// Throw error if template does not exists
		if (BaseUtil.isObjNull(emailTemplate)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}

		try {
			String subject = BaseUtil.getStr(emailTemplate.getEmailSubject());

			// Substitute subject arguments with the proper value
			if (!notification.getSubjectParam().isEmpty()) {
				subject = MessageFormat.format(subject, notification.getSubjectParam().toArray());
			}

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			Map<String, Object> map = mapper.readValue(notification.getMetaData(), typeRef);
			emailService.sendSimpleMail(subject, notification.getNotifyTo(), notification.getNotifyCc(), map,
					templateCode, null);
		} catch (Exception e) {
			LOGGER.error("Exception:sendMail: ", e);
			return false;
		}

		stopwatch.stop();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("sendMail: {}", stopwatch.prettyPrint());
		}
		return true;
	}


	/**
	 * Send.
	 *
	 * @param sms
	 *             the sms
	 * @param request
	 *             the request
	 * @return the sms
	 */
	@PostMapping(value = NotUrlConstants.NOTIFY_SMS_SEND, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Sms send(@Valid @RequestBody Sms sms, HttpServletRequest request) {
		boolean hasSmsNotif = Boolean.parseBoolean(getConfigValue(NotConfigConstants.SMS_SWITCH));
		if (!hasSmsNotif) {
			throw new NotificationException(NotErrorCodeEnum.E503NOT004);
		}

		StopWatch stopwatch = new StopWatch();
		stopwatch.start(SmsTemplate.SMS_REALTIME.name());
		try {
			smsManagerService.sendOTPThroughICE(sms);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			addSmsNotification(sms.getMobileNo(), MailTypeEnum.SMS.name(), sms.getContent(),
					SmsTemplate.SMS_REALTIME.name(), getCurrUserId(request));
		}
		stopwatch.stop();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("END :: {}", stopwatch.prettyPrint());
		}
		return sms;
	}


	/**
	 * Add Notification.
	 *
	 * @param templateCode
	 *             the template code
	 * @param notification
	 *             the notification
	 * @param request
	 *             the request
	 * @return the not payload
	 */
	@PostMapping(value = NotUrlConstants.NOTIFY_NEW, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public NotPayload add(@RequestParam("templateCode") final String templateCode,
			@Valid @RequestBody Notification notification, HttpServletRequest request) {
		LOGGER.info("Add Notification: Template: {} - Notify Type: {}", templateCode,
				new ObjectMapper().valueToTree(notification));
		if (BaseUtil.isObjNull(templateCode) || BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}

		return addNotification(templateCode, notification, getCurrUserId(request));
	}


	/**
	 * Adds the notification.
	 *
	 * @param templateCode
	 *             the template code
	 * @param notification
	 *             the notification
	 * @param currUserId
	 *             the curr user id
	 * @return the not payload
	 */
	public NotPayload addNotification(String templateCode, Notification notification, String currUserId) {
		if (BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}
		// Retrieve Email Template
		NotEmailTemplate emailTemplate = notEMailTemplateSvc.findByTemplateCode(templateCode);

		// Throw error if template does not exists
		if (BaseUtil.isObjNull(emailTemplate)) {
			throw new NotificationException(NotErrorCodeEnum.E404NOT003);
		}

		String subject = BaseUtil.getStr(emailTemplate.getEmailSubject());

		// Substitute subject arguments with the proper value
		if (!BaseUtil.isEqualsCaseIgnore(emailTemplate.getTemplateType(), MailTypeEnum.SMS.getType())
				&& !BaseUtil.isObjNull(notification.getSubjectParam())
				&& !notification.getSubjectParam().isEmpty()) {
			subject = MessageFormat.format(subject, notification.getSubjectParam().toArray());
		}

		NotPayload notPayload = dozerMapper.map(notification, NotPayload.class);
		notPayload.setSubject(subject);
		notPayload.setStatus("0");
		notPayload.setNotifyType(emailTemplate.getTemplateType());
		notPayload.setTemplateCode(emailTemplate.getTemplateCode());
		notPayload.setRtryCount(0);
		notPayload.setCreateId(currUserId);
		notPayload.setCreateDt(getSQLTimestamp());
		notPayload.setUpdateId(currUserId);
		notPayload.setUpdateDt(getSQLTimestamp());

		if (notification.getAttachments() != null) {
			try {
				notPayload.setAttachment(JsonUtil.objectMapper().writerFor(new TypeReference<List<Attachment>>() {
				}).writeValueAsString(notification.getAttachments()));
			} catch (JsonProcessingException e) {
				LOGGER.error("{}", e);
			}
		}

		if (BaseUtil.isEqualsCaseIgnore("CMN_OTP", templateCode)) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(notification.getMetaData(),
						new TypeReference<HashMap<String, Object>>() {
						});
				emailService.sendSimpleMail(subject, notification.getNotifyTo(), notification.getNotifyCc(), map,
						templateCode, null);
			} catch (IOException | MessagingException e) {
				LOGGER.error("IOException: {}", e.getMessage());
			}
		} else {
			try {
				notPayload = notificationSvc.primaryDao().save(notPayload);
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
				throw new NotificationException(NotErrorCodeEnum.E404NOT002);
			}
		}

		return notPayload;
	}


	/**
	 * Adds the sms notification.
	 *
	 * @param recipient
	 *             the recipient
	 * @param notifyType
	 *             the notify type
	 * @param metaData
	 *             the meta data
	 * @param templateCode
	 *             the template code
	 * @param createUserId
	 *             the create user id
	 * @return the not payload
	 */
	private NotPayload addSmsNotification(String recipient, String notifyType, Object metaData, String templateCode,
			String createUserId) {
		NotPayload notification = new NotPayload();
		notification.setNotifyTo(recipient);
		notification.setNotifyType(notifyType);
		notification.setTemplateCode(templateCode);
		notification.setMetaData(BaseUtil.getStr(metaData));
		notification.setRtryCount(0);
		notification.setCreateId(createUserId);
		notification.setCreateDt(getSQLTimestamp());
		notification.setUpdateId(createUserId);
		notification.setUpdateDt(getSQLTimestamp());

		NotPayload notPayload = null;
		try {
			notPayload = notificationSvc.primaryDao().save(notification);
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new NotificationException(NotErrorCodeEnum.E404NOT002);
		}

		return notPayload;
	}


	@PostMapping(value = NotUrlConstants.NOTIFICATIONS + "/search", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<NotPayload> searchMailTemplate(@Valid @RequestBody Notification notification,
			HttpServletRequest request) {

		if (BaseUtil.isObjNull(notification)) {
			throw new NotificationException(NotErrorCodeEnum.E400NOT003);
		}

		return notificationSvc.searchNotPayload(notification);
	}


	@GetMapping(value = NotUrlConstants.MAIL_NOTIFICATIONS, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Notification> findWrkrEmailNotification(
			@RequestParam(value = "notifyTo", required = true) final String notifyTo, HttpServletRequest request) {
		return notificationSvc.findWrkrEmailNotification(notifyTo);

	}

}