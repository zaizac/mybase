/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.dialect;


import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * @author Mary Jane Buenaventura
 * @since 05/11/2014
 */
public class MessageService {

	static MessageService messageService;

	private MessageSource messageSource;


	/**
	 * @param messageSource
	 *             Message Source
	 */
	public MessageService(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	/**
	 * @param msg
	 *             message variable
	 * @return translated message
	 */
	public String getMessage(String msg) {
		return messageSource.getMessage(msg, null, getLocale());
	}


	/**
	 * @param msg
	 *             message variable
	 * @param obj
	 *             Array of objects
	 * @return translated message
	 */
	public String getMessage(String msg, Object[] obj) {
		return messageSource.getMessage(msg, obj, getLocale());
	}


	/**
	 * @return current locale language ex. en
	 */
	public String getLocalLanguage() {
		return getLocale().getLanguage();
	}


	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

}