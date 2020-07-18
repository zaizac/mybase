/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.bstsb.notify.config;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.bstsb.notify.service.SchedulerService;



/**
 * The Class ScheduleConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 30, 2018
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);


	/**
	 * Scheduler.
	 *
	 * @return the scheduler service
	 */
	@Bean
	public SchedulerService scheduler() {
		LOGGER.info("Creating Scheduler Config...");
		return new SchedulerService();
	}


	/* (non-Javadoc)
	 * @see org.springframework.scheduling.annotation.SchedulingConfigurer#configureTasks(org.springframework.scheduling.config.ScheduledTaskRegistrar)
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}


	/**
	 * Task executor.
	 *
	 * @return the executor
	 */
	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(10);
	}

}
