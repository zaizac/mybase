/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.web.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Mary Jane Buenaventura
 * @since 05/11/2014
 */
@Component("messageService")
@Qualifier("messageService")
public class MessageService {

	static MessageService messageService;
	
	@Autowired
	private MessageSource messageSource;
	
	public static MessageService getInstance() {
		if(messageService == null) {
			messageService = new MessageService();
		}
		return messageService;
	}
	
	public String getMessage(String msg) {
		return messageSource.getMessage(msg, null, getLocale());
	}
	
	public String getMessage(String msg, Object[] obj) {
		return messageSource.getMessage(msg, obj, getLocale());
	}
	
	public String getLocalLanguage() {
		return getLocale().getLanguage();
	}
	
	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}