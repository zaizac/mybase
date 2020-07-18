/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.notify.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.baseboot.notify.model.NotPayload;


/**
 * @author Mary Jane Buenaventura
 * @since May 30, 2018
 */
public class SchedulerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
	
	@Autowired
	private NotPayloadService notificationSvc;
	
	@Scheduled(fixedRateString = "${not.service.interval}")
	private void sendAll() {
		LOGGER.debug("Schedule Sending Email Notification...");
		List<NotPayload> notify = notificationSvc.sendAll();
		LOGGER.debug("{} notification(s) sent.", notify.size());
	}
	
}
