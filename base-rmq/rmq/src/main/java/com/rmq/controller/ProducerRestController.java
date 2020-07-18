package com.rmq.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmq.config.ReplyTemplate;
import com.rmq.model.RmqEndpointConfig;
import com.rmq.service.RmqEndpointConfigService;
import com.util.BaseUtil;
import com.util.JsonUtil;

@RestController
@RequestMapping("/service")
public class ProducerRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerRestController.class);

	@Autowired
	ReplyTemplate template;
	
	@Autowired
	RmqEndpointConfigService endpointConfigSvc;

	@RequestMapping(value = "/{clientId}/{module}/{action}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Object add(@PathVariable String clientId, @PathVariable String module, @PathVariable String action
			, @RequestBody Map<String, Object> message) throws Exception {
			//, @RequestBody Message message) throws Exception {
		LOGGER.info("Client ID: {} - Module: {} - Action: {}", clientId, module, action);
		Object result = "Received message: " + message.get(0) + "::" + message.get(0);
		
		RmqEndpointConfig config = endpointConfigSvc.findByModuleAndAction(module, action);
		
		String queueName = "jane_queue_reply"; // Default / Fallback Queue
		String exchange = "";
		final String redirectUrl = !BaseUtil.isObjNull(config) ? config.getRedirectUrl() : null;
		final String redirectMethod = !BaseUtil.isObjNull(config) ? config.getHttpMethod(): null;
		
		if(!BaseUtil.isObjNull(config)) {
			LOGGER.info(config.getQueueName());
			queueName = config.getQueueName();
			if(!BaseUtil.isObjNull(config.getQueueExchange())) {
				exchange = config.getQueueExchange();
			}
		}
		
		try {
			String correlationId = UUID.randomUUID().toString();
			LOGGER.info("Correlation ID: {}", correlationId);
			
			/**
			 * TODO: Take the configuration (queue details) from DB
			 */
			result = template.convertSendAndReceive(exchange, queueName, 
					message,
					new MessagePostProcessor() {            
			        	@Override
			            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
			                message.getMessageProperties().setAppId(correlationId);
			                message.getMessageProperties().setTimestamp(new Date());
			                message.getMessageProperties().setCorrelationId(correlationId);
			                message.getMessageProperties().setHeader("X-Client-Id", clientId);
			                message.getMessageProperties().setHeader("X-Redirect-Url", redirectUrl);
			                message.getMessageProperties().setHeader("X-Redirect-Method", redirectMethod);
			                message.getMessageProperties().setHeader("ServiceMethodName", "TestJane");
			                message.getMessageProperties().setHeader("ServiceName", "ServiceName");
			                return message;
			            }
			        }, 
					new CorrelationData(correlationId));
			LOGGER.info("-------------------> {}", JsonUtil.objectMapper().writeValueAsString(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}
	
}
