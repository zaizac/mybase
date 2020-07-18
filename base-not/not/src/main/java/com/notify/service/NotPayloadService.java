/**
 * Copyright 2019. 
 */
package com.notify.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.constants.QualifierConstants;
import com.notify.core.AbstractService;
import com.notify.core.GenericRepository;
import com.notify.dao.NotPayloadQf;
import com.notify.dao.NotPayloadRepository;
import com.notify.model.NotPayload;
import com.notify.sdk.model.Attachment;
import com.notify.sdk.model.Notification;
import com.notify.sdk.model.Sms;
import com.util.BaseUtil;
import com.util.JsonUtil;



/**
 * The Class NotPayloadService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.NOTIFICATION_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_SVC)
public class NotPayloadService extends AbstractService<NotPayload> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotPayloadService.class);

	/** The sms manager service. */
	@Autowired
	private SmsManagerService smsManagerService;

	/** The email service. */
	@Autowired
	protected EmailService emailService;

	/** The notification dao. */
	@Autowired
	private NotPayloadRepository notificationDao;
	
	@Autowired
	private NotPayloadQf notificationQf;

	/** The fcm noti service. */
	@Autowired
	protected FcmService fcmNotiService;

	/** The not email template svc. */
	@Autowired
	NotEmailTemplateService notEmailTemplateSvc;

	/** The dozer mapper. */
	@Autowired
	protected Mapper dozerMapper;


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractService#primaryDao()
	 */
	@Override
	public GenericRepository<NotPayload> primaryDao() {
		return notificationDao;
	}


	/**
	 * Find notification by id.
	 *
	 * @param id the id
	 * @return the not payload
	 */
	public NotPayload findNotificationById(Integer id) {
		return notificationDao.findNotificationById(id);
	}


	/**
	 * Find notification by status.
	 *
	 * @param status the status
	 * @return the list
	 */
	public List<NotPayload> findNotificationByStatus(String status) {
		return notificationDao.findNotificationByStatus(status);
	}


	/**
	 * Send all.
	 *
	 * @return the list
	 */
	public List<NotPayload> sendAll() {
		List<NotPayload> list = findNotificationByStatus("0");
		List<NotPayload> notificationList = new ArrayList<>();

		if (!BaseUtil.isListNull(list)) {
			StopWatch stopwatch = new StopWatch();
			stopwatch.start("Sending all notification");

			// UPDATE STATUS
			sendAllUpdateStatus(list);

			for (NotPayload notification : list) {
				LOGGER.info("Sending {} ({}) to {}", notification.getNotifyType(), notification.getTemplateCode(),
						notification.getNotifyTo());
				if (BaseUtil.isEqualsCaseIgnore(notification.getNotifyType(), "SMS")) {
					sendSms(notification);
				} else if (BaseUtil.isEqualsCaseIgnore(notification.getNotifyType(), "fcm")) {
					sendFcm(notification);
				} else {
					sendMail(notification, notification.getSystemType());
				}
				notification.setUpdateId("scheduler");
				notification.setRtryCount(notification.getRtryCount() + 1);
				notification = primaryDao().saveAndFlush(notification);
				notificationList.add(notification);
			}
			stopwatch.stop();
			long millis = stopwatch.getLastTaskTimeMillis();
			String timeWatched = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
			LOGGER.info("{} completes within {}", stopwatch.getLastTaskName(), timeWatched);
		}
		return notificationList;
	}


	/**
	 * Send all update status.
	 *
	 * @param list the list
	 */
	private void sendAllUpdateStatus(List<NotPayload> list) {
		for (NotPayload notification : list) {
			notification.setStatus("2");
			notification.setUpdateId("scheduler");
			primaryDao().saveAndFlush(notification);
		}
	}


	/**
	 * Send all sms.
	 *
	 * @param notification the notification
	 */
	private void sendSms(NotPayload notification) {
		Sms sms = new Sms(notification.getNotifyTo(), notification.getMetaData(), notification.getTemplateCode());
		try {
			smsManagerService.sendOTPThroughICE(sms);
			notification.setStatus("1");
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			notification.setStatus("0");
		}
	}


	/**
	 * Send all fcm.
	 *
	 * @param notification the notification
	 */
	private void sendFcm(NotPayload notification) {
		Map<String, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		try {
			map = mapper.readValue(notification.getMetaData(), typeRef);
			String content = fcmNotiService.sendNotiFcm(notification.getSubject(), notification.getNotifyTo(),
					notification.getNotifyCc(), map, notification.getTemplateCode(), notification.getId());
			notification.setContent(content);
			notification.setStatus("1");
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			notification.setStatus("0");
		}
	}


	/**
	 * Send all mail.
	 *
	 * @param notification the notification
	 */
	private void sendMail(NotPayload notification, String systemType) {
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		try {
			Map<String, Object> map = JsonUtil.objectMapper().readValue(notification.getMetaData(), typeRef);
			List<Attachment> attachments = new ArrayList<>();
			if (notification.getAttachment() != null) {
				Attachment[] attachmentLst = JsonUtil.objectMapper().readValue(notification.getAttachment(),
						Attachment[].class);
				attachments = Arrays.asList(attachmentLst);
			}

			emailService.sendSimpleMail(notification.getSubject(), notification.getNotifyTo(),
					notification.getNotifyCc(), map, notification.getTemplateCode(), attachments, systemType);
			if (BaseUtil.isObjNull(notification.getAttachment()) && !BaseUtil.isListZero(attachments)) {
				notification.setAttachment(JsonUtil.objectMapper().writeValueAsString(attachments));
			}
			notification.setStatus("1");

		} catch (MessagingException e) {
			LOGGER.error("MessagingException:", e);
			notification.setStatus("0");
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException", e);
		} catch (JsonMappingException e) {
			LOGGER.error("JsonMappingException", e);
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<NotPayload> search(Notification notification) throws IOException {
		NotPayload notPayload = JsonUtil.transferToObject(notification, NotPayload.class);
		return notificationDao.findAll(notificationQf.searchByProperty(notPayload));
	}
}
