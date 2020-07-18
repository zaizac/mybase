/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.exception;


import java.text.MessageFormat;

import com.bstsb.notify.sdk.constants.NotErrorCodeEnum;


/**
 * The Class NotificationException.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotificationException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5876380106101907503L;

	/** The internal error code. */
	private final String internalErrorCode;


	/**
	 * Instantiates a new notification exception.
	 */
	public NotificationException() {
		super();
		internalErrorCode = "";
	}


	/**
	 * Instantiates a new notification exception.
	 *
	 * @param s
	 *             the s
	 */
	public NotificationException(String s) {
		super(s);
		internalErrorCode = "";
	}


	/**
	 * Instantiates a new notification exception.
	 *
	 * @param internalCode
	 *             the internal code
	 * @param reason
	 *             the reason
	 */
	public NotificationException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	/**
	 * Instantiates a new notification exception.
	 *
	 * @param rce
	 *             the rce
	 */
	public NotificationException(NotErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	/**
	 * Instantiates a new notification exception.
	 *
	 * @param rce
	 *             the rce
	 * @param args
	 *             the args
	 */
	public NotificationException(NotErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	/**
	 * Gets the internal error code.
	 *
	 * @return the internal error code
	 */
	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}