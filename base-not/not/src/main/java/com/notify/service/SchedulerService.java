/**
 * Copyright 2019. 
 */
package com.notify.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.notify.model.NotPayload;



/**
 * The Class SchedulerService.
 *
 * @author Mary Jane Buenaventura
 * @since May 30, 2018
 */
public class SchedulerService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
	
	/** The notification svc. */
	@Autowired
	private NotPayloadService notificationSvc;
	
	/**
	 * Send all.
	 */
	@Scheduled(fixedRateString = "${not.service.interval}")
	private void sendAll() {
		LOGGER.info("Schedule Sending Email Notification...");
		List<NotPayload> notify = notificationSvc.sendAll();
		LOGGER.info("{} notification(s) sent.", notify.size());
	}
	
}
