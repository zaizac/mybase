/*
 * @CustomMessageConverter.java	@Apr 23, 2012
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

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.esb.util.BaseUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * Message converter class which is used for converting the message which is been 
 * sent to and from the JMS Queue; This particular class can be used for JMS Template 
 * and MessageListener.
 * </p>
 *
 * @author manjunatha.n
 * @version 1.0
 *
 */
public class CustomMessageConverter implements MessageConverter {

	private static final Logger logger = Logger.getLogger(MessageConverter.class);
	
	/**
	 * Class Name
	 */
	public static final String KEY_CLASS_NAME = "json-className";
	
	/** (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
	 *
	 * <p>
	 * Converts the JMS Specific message to application specific messages; 
	 * </p>
	 * 
	 * @param message
	 * @return : Application specific Message
	 * @throws JMSException
	 * @throws MessageConversionException
	 */
	
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		//Message must be of type text message.
		if(!(message instanceof TextMessage)) {
			return null;
		}

		//Message header must provide the fully qualified class name.
		String fqcn = message.getStringProperty(KEY_CLASS_NAME);
		if(fqcn == null) {
			return null;
		}

		//Retrieve the classloader
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if(cl == null) {
			cl = getClass().getClassLoader();
		}

		Class<?> outCls = null;
		try {
			outCls = cl.loadClass(fqcn);
		} catch(ClassNotFoundException exep) {
			return null;
		}

		TextMessage txtMsg = (TextMessage) message;
		String jsonBody = txtMsg.getText();
		if(StringUtils.isBlank(jsonBody)) {
			return null;
		}
		logger.info("the json retrieve is..." + jsonBody);
		return jsonBody;
	}

	/** (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 *
	 * <p>
	 * Converts the Object message to JMS Message; Used 
	 * while sending the message to JMS Queue (Will convert the Objec to Json format)
	 * </p>
	 * 
	 * @param object : the object message which needs to be converted to 
	 * 				   JMS Message Component
	 * @param session : Refers to JMS Session
	 * 
	 * @return
	 * @throws JMSException
	 * @throws MessageConversionException
	 */
	
	public Message toMessage(Object obj, Session session) throws JMSException,
			MessageConversionException {
		if(obj == null) {
			return null;
		}
		logger.info("the json created is..."+BaseUtil.getStr(obj));
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText(BaseUtil.getStr(obj));
		txtMsg.setStringProperty(KEY_CLASS_NAME, obj.getClass().getName());
		return txtMsg;
	}

}
