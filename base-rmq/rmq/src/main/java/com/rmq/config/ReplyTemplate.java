/**
 * 
 */
package com.rmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author mary.jane
 *
 */
public class ReplyTemplate extends RabbitTemplate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyTemplate.class);

	public ReplyTemplate(ConnectionFactory cf) {
        super(cf);
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Response received (before conversion): " + message);
        final MessageProperties messageProperties = message.getMessageProperties();
        final String body = new String(message.getBody());
        String correlationId = messageProperties.getCorrelationId();
        String replyTo = messageProperties.getReceivedRoutingKey();
        LOGGER.info("Processing message with Correlation ID : " 
        		+ correlationId + " Replying To: " + replyTo + " and Message: \n"
        		+ body);
        LOGGER.info("*********** AMQP Message **********");
        LOGGER.info(" Id          : " + messageProperties.getMessageId());
        LOGGER.info(" Routing Key : {}", replyTo);
        LOGGER.info(" CorrelId    : " + messageProperties.getCorrelationId());
        LOGGER.info(" Timestamp   : " + messageProperties.getTimestamp());
        LOGGER.info(" Service     : " + messageProperties.getHeaders().get("service"));
        LOGGER.info(" Content-Type: " + messageProperties.getContentType());
        LOGGER.info(" Encoding    : " + messageProperties.getContentEncoding());
        LOGGER.info(" Delivery Tag    : " + message.getMessageProperties().getDeliveryTag());
        LOGGER.info(" Message     : " + body);
        LOGGER.info("*************** End ***************");
        super.onMessage(message);
    }

}
