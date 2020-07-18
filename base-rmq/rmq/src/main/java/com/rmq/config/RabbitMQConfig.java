/**
 * 
 */
package com.rmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.util.constants.BaseConstants;

/**
 * @author mary.jane
 *
 */
@Configuration
public class RabbitMQConfig {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);
	
	@Value("${rabbitmq.username}")
	String username;

	@Value("${rabbitmq.password}")
	private String password;
	
	@Value("${rabbitmq.url}")
	String url;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("RabbitMQ Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("URL: " + url + BaseConstants.NEW_LINE);
		sb.append("Username: " + username + BaseConstants.NEW_LINE);
		sb.append("Password: " + password);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
		
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setUri(url);
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setUsername(password);
		return cachingConnectionFactory;
	}
	
    @Bean
    public ReplyTemplate template(ConnectionFactory cf) {
    	ReplyTemplate rabbitTemplate = new ReplyTemplate(cf);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setCorrelationDataPostProcessor(new CorrelationDataProcessor());
        rabbitTemplate.setEncoding("UTF-8");
        return rabbitTemplate;
    }

	@Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setCorrelationDataPostProcessor(new CorrelationDataProcessor());
        rabbitTemplate.setEncoding("UTF-8");
        return rabbitTemplate;
    }
	
	@Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

	@Bean
	public RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
}