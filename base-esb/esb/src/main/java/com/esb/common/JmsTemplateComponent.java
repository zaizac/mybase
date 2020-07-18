/*
 * @JmsTemplateComponent.java	@Apr 23, 2012
 *
 * Copyright (c) 2013 Bestinet. 
 * All rights reserved. 
 * 
 * No part of this document may be reproduced or transmitted in any form or by 
 * any means, electronic or mechanical, whether now known or later invented, 
 * for any purpose without the prior and express written consent of Bestinet 
 * 
 */
package com.esb.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.ProcessorEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;


public class JmsTemplateComponent extends DefaultComponent {

	/**
	 * Logger for the class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JmsTemplateComponent.class);
	
	private JmsTemplate jmsTemplate;

	/**
	 * 
	 * <p>
	 * Sets the JMSTemplate Component for the sending the message
	 * </p>
	 * 
	 * @param tpl
	 */
	public void setJmsTemplate(JmsTemplate tpl) {
		jmsTemplate = tpl;
		logger.debug("JMS Template injected");
	}
	
	/**
	 * <p>
	 * creates end point for communication with the configured URI and Destination. 
	 * </p>
	 * 
	 *(non-Javadoc)
	 * @see org.apache.camel.impl.DefaultComponent#createEndpoint(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	protected Endpoint createEndpoint(String uriEndPoint, String destination,
			Map<String, Object> params) throws Exception {
		
		logger.info("Creating the end point for the JMS Template with URI  "+uriEndPoint+" and destination "+ destination);
				
		Endpoint endpoint = new ProcessorEndpoint(uriEndPoint, getCamelContext(),
				new JmsTemplateProcessor(destination));
		
		return endpoint;
	}

	/** 
	 * <p>
	 * Inner class which handles message processing; Acts as end point for sending in 
	 * all the messages. 
	 * </p>
	 *
	 * @author Manjunath
	 * @version 1.0
	 *
	 * Nov 14, 2012
	 * JmsTemplateComponent.java
	 */
	private class JmsTemplateProcessor implements Processor{

		private String destination;
		
		public JmsTemplateProcessor(String destination){
			this.destination=destination;
		}
		
		/**
		 * <p>
		 * Used to send messages
		 * </p>
		 * 
		 *(non-Javadoc)
		 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
		 */
		public void process(Exchange exchange) throws Exception {
			logger.info("Processing messages");
			Object payload = null;
			if(exchange == null) {
				logger.info("payload is null. Bailing out.");
				return;
			}

			if(exchange.getPattern().isOutCapable()) {
				payload = exchange.getOut().getBody();
				logger.debug("payload received from exchange out");
			} else {
				payload = exchange.getIn().getBody();
				logger.debug("payload received from exchange in");
			}
			
			if(payload == null) {
				logger.debug("payload is null. Bailing out.");
				return;
			}
			
			logger.info("Destination is : "+destination);
			
			if(payload instanceof Collection) {
				Iterator<?> iter = ((Collection<?>) payload).iterator();
				if(!iter.hasNext()) {
					logger.debug("payload is empty collection");
				} else {
					while(iter.hasNext()) {
						Object obj = iter.next();
						logger.info("Obj is : "+obj);
						jmsTemplate.convertAndSend(destination, obj);
					}
					logger.info(((Collection<?>) payload).size()+" message(s) sent");
				}
			} else {
				logger.info("payLoad is : "+payload.toString());
				jmsTemplate.convertAndSend(destination, payload);
				logger.info("1 message sent");
			}
			
		}
		
	}
	
}