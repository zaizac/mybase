/**
 * 
 */
package com.rmq.config;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.CorrelationDataPostProcessor;

/**
 * @author mary.jane
 *
 */
public class CorrelationDataProcessor implements CorrelationDataPostProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationDataProcessor.class);

	@Override
	public CorrelationData postProcess(Message message, CorrelationData correlationData) {
		if(correlationData == null) {
			correlationData = new CorrelationData(UUID.randomUUID().toString());
		}
		LOGGER.info("Generated Correlation ID: {}", correlationData.getId());
		message.getMessageProperties().setCorrelationId(correlationData.getId());
		return correlationData;
	}

}
