/**
 * 
 */
package com.rmq.listener;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rmq.constants.ConfigConstants;
import com.rmq.controller.AbstractRestController;
import com.rmq.httpclient.EsbServiceClient;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;


/**
 * @author mary.jane
 * @since 19 May 2020
 */
@Component
public class RabbitMQListener extends AbstractRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);
	
	@Value("${" + BaseConfigConstants.SVC_IDM_URL + "}")
	private String idmUrl;
	
	@Value("${" + ConfigConstants.SVC_BE_URL + "}")
	private String beUrl;

	@Value("${" + ConfigConstants.SVC_BE_TIMEOUT + "}")
	private int beTimeout;
	
	@RabbitListener(queues = "jane_queue")
    public Object receive(Message message) {
    	final MessageProperties messageProperties = message.getMessageProperties();
        final String body = new String(message.getBody());
        String correlationId = messageProperties.getCorrelationId();
        String replyTo = messageProperties.getReceivedRoutingKey();
        
		LOGGER.info("*********** AMQP Message **********");
        LOGGER.info(" Id          : {}", messageProperties.getMessageId());
        LOGGER.info(" Routing Key : {}", replyTo);
        LOGGER.info(" CorrelId    : {}", correlationId);
        LOGGER.info(" Timestamp   : {}", messageProperties.getTimestamp());
        LOGGER.info(" Service     : {}", messageProperties.getHeaders().get("service"));
        LOGGER.info(" Content-Type: {}", messageProperties.getContentType());
        LOGGER.info(" Encoding    : {}", messageProperties.getContentEncoding());
        LOGGER.info(" Delivery Tag  : {} ", message.getMessageProperties().getDeliveryTag());
        LOGGER.info(" Message     : {}", body);
        LOGGER.info("*************** End ***************");
        
        Map<String, String> map = getIdmService().findAllConfig();
        return map;
    }

	@RabbitListener(queues = "jane_queue_reply")
    public Object patientList(Message message) {
		final MessageProperties messageProperties = message.getMessageProperties();
        final String body = new String(message.getBody());
        String correlationId = messageProperties.getCorrelationId();
        String replyTo = messageProperties.getReceivedRoutingKey();
        
		LOGGER.info("*********** AMQP Message **********");
        LOGGER.info(" Id          : {}", messageProperties.getMessageId());
        LOGGER.info(" Routing Key : {}", replyTo);
        LOGGER.info(" CorrelId    : {}", correlationId);
        LOGGER.info(" Timestamp   : {}", messageProperties.getTimestamp());
        LOGGER.info(" Service     : {}", messageProperties.getHeaders().get("service"));
        LOGGER.info(" Content-Type: {}", messageProperties.getContentType());
        LOGGER.info(" Encoding    : {}", messageProperties.getContentEncoding());
        LOGGER.info(" Delivery Tag  : {} ", message.getMessageProperties().getDeliveryTag());
        LOGGER.info(" Message     : {}", body);
        LOGGER.info("*************** End ***************");
        
        EsbServiceClient esbClient = new EsbServiceClient(beUrl, beTimeout);
        esbClient.setMessageId(correlationId);
        esbClient.setToken(messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
        esbClient.setClientId(messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		
        try {
        	String httpMethod = messageProperties.getHeader("X-Redirect-Method");
        	if(BaseUtil.isEqualsCaseIgnore("GET", httpMethod)) {
        		return esbClient.getForObject(messageProperties.getHeader("X-Redirect-Url"));
        	} else if(BaseUtil.isEqualsCaseIgnore("POST", httpMethod)) {
        		return esbClient.postForObject(messageProperties.getHeader("X-Redirect-Url"), body);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return null;
	}
	
}