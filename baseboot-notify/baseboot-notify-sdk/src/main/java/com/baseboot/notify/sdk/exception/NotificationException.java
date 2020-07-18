/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.exception;


import java.text.MessageFormat;

import com.baseboot.notify.sdk.constants.NotErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotificationException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private String internalErrorCode;


	public NotificationException() {
		super();
	}


	public NotificationException(String s) {
		super(s);
	}


	public NotificationException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public NotificationException(NotErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public NotificationException(NotErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}