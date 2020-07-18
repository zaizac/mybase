/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.baseboot.notify.core.AbstractService;
import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.dao.NotPayloadRepository;
import com.baseboot.notify.model.NotPayload;
import com.baseboot.notify.sdk.model.MailAttachment;
import com.baseboot.notify.sdk.model.Sms;
import com.baseboot.notify.sdk.util.BaseUtil;
import com.baseboot.notify.util.QualifierConstants;
// FIXME: import com.baseboot.report.sdk.model.Report;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.NOTIFICATION_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_SVC)
public class NotPayloadService extends AbstractService<NotPayload, Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotPayloadService.class);

	@Autowired
	private SmsManagerService smsManagerService;

	@Autowired
	protected EmailService emailService;

	@Autowired
	private NotPayloadRepository notificationDao;


	@Override
	public GenericRepository<NotPayload> primaryDao() {
		return notificationDao;
	}


	public NotPayload findNotificationById(Integer id) {
		return notificationDao.findNotificationById(id);
	}


	public List<NotPayload> findNotificationByStatus(String status) {
		return notificationDao.findNotificationByStatus(status);
	}


	public List<NotPayload> sendAll() {
		List<NotPayload> list = findNotificationByStatus("0");
		List<NotPayload> notificationList = new ArrayList<>();

		if (!BaseUtil.isListNull(list)) {
			StopWatch stopwatch = new StopWatch();
			stopwatch.start("Sending all notification");

			// UPDATE STATUS
			for (NotPayload notification : list) {
				notification.setStatus("2");
				notification.setUpdateId("scheduler");
				primaryDao().saveAndFlush(notification);
			}

			for (NotPayload notification : list) {
				LOGGER.info("Sending {} ({}) to {}", notification.getNotifyType(), notification.getTemplateCode(),
						notification.getNotifyTo());
				ObjectMapper mapper = new ObjectMapper();
				if (BaseUtil.isEqualsCaseIgnore(notification.getNotifyType(), "SMS")) {
					Sms sms = new Sms(notification.getNotifyTo(), notification.getMetaData());
					try {
						smsManagerService.sendOTPThroughICE(sms);
						notification.setStatus("1");
					} catch (Exception e) {
						LOGGER.error("Exception", e);
						notification.setStatus("0");
					}
				} else {
					Map<String, Object> map = new HashMap<>();
					TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
					};

					try {
						map = mapper.readValue(notification.getMetaData(), typeRef);
						List<MailAttachment> attachments = new ArrayList<>();
						if (notification.getAttachment() != null) {
							try {
								MailAttachment[] attachmentLst = new ObjectMapper()
										.readValue(notification.getAttachment(), MailAttachment[].class);
								for (MailAttachment mailAttachment : Arrays.asList(attachmentLst)) {
									attachments.add(mailAttachment);
								}
							} catch (JsonParseException e) {
								LOGGER.error("JsonParseException", e);
							} catch (JsonMappingException e) {
								LOGGER.error("JsonMappingException", e);
							} catch (IOException e) {
								LOGGER.error("IOException", e);
							}
						}

						try {
							/*
							 * FIXME: if
							 * (!BaseUtil.isListZero(attachments)) {
							 * Report rpt =
							 * getAttachmentByTemplateCode(map,
							 * notification.getTemplateCode()); if
							 * (!BaseUtil.isObjNull(rpt)) {
							 * attachments.add(new
							 * MailAttachment(rpt.getFileName(),
							 * rpt.getReportBytes(), rpt.getMimeType()));
							 * } }
							 */
							emailService.sendSimpleMail(notification.getSubject(), notification.getNotifyTo(),
									notification.getNotifyCc(), map, notification.getTemplateCode(),
									attachments);
							if (BaseUtil.isObjNull(notification.getAttachment())
									&& !BaseUtil.isListZero(attachments)) {
								notification.setAttachment(new ObjectMapper().writeValueAsString(attachments));
							}
							notification.setStatus("1");
						} catch (MessagingException e) {
							LOGGER.error("MessagingException:", e);
							notification.setStatus("0");
						} catch (Exception e) {
							LOGGER.error("Exception:", e);
							notification.setStatus("0");
						}
					} catch (IOException e) {
						LOGGER.error("IOException", e);
					}

				}
				notification.setUpdateId("scheduler");
				notification.setRtryCount(notification.getRtryCount() + 1);
				notification = primaryDao().saveAndFlush(notification);
				notificationList.add(notification);
			}
			stopwatch.stop();
			long millis = stopwatch.getLastTaskTimeMillis();
			LOGGER.info("{} completes within {}", stopwatch.getLastTaskName(),
					String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
							TimeUnit.MILLISECONDS.toMinutes(millis)
									- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
							TimeUnit.MILLISECONDS.toSeconds(millis)
									- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
		}
		return notificationList;
	}

	/*
	 * FIXME: private Report getAttachmentByTemplateCode(final Map<String,
	 * Object> emailParam, final String templateCode) throws Exception {
	 * LOGGER.debug("getAttachmentByTemplateCode: {}", templateCode); if
	 * (!BaseUtil.isObjNull(emailParam)) { // TODO: Define the Attachment }
	 * return null; }
	 */
}
