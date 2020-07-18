/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.notify.config;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.baseboot.notify.service.SchedulerService;


/**
 * @author Mary Jane Buenaventura
 * @since May 30, 2018
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);


	@Bean
	public SchedulerService scheduler() {
		LOGGER.info("Creating Scheduler Config...");
		return new SchedulerService();
	}


	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}


	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(10);
	}

}
