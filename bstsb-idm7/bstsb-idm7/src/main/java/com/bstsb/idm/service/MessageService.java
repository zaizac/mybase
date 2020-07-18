/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.bstsb.idm.constants.QualifierConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Component(QualifierConstants.MESSAGE_SVC)
@Qualifier(QualifierConstants.MESSAGE_SVC)
public class MessageService {

	static MessageService messageService;

	@Autowired
	private MessageSource messageSource;


	public static MessageService getInstance() {
		if (messageService == null) {
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