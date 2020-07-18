/**
 * Copyright 2019. 
 */
/**
 * Copyright 2019. 
 */
package com.dm.sdk.exception;


import java.text.MessageFormat;

import com.dm.sdk.client.constants.DmErrorCodeEnum;


/**
 * The Class DmException.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DmException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5876380106101907503L;

	/** The internal error code. */
	private final String internalErrorCode;


	/**
	 * Instantiates a new dm exception.
	 */
	public DmException() {
		super();
		internalErrorCode = "";
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param s the s
	 */
	public DmException(String s) {
		super(s);
		internalErrorCode = "";
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param string the string
	 * @param e the e
	 */
	public DmException(String string, Throwable e) {
		super(string, e);
		internalErrorCode = "";
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param internalCode the internal code
	 * @param reason the reason
	 */
	public DmException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param internalCode the internal code
	 * @param reason the reason
	 * @param e the e
	 */
	public DmException(String internalCode, String reason, Throwable e) {
		super(reason, e);
		internalErrorCode = internalCode;
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param rce the rce
	 */
	public DmException(DmErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param rce the rce
	 * @param e the e
	 */
	public DmException(DmErrorCodeEnum rce, Throwable e) {
		super(rce.getMessage(), e);
		internalErrorCode = rce.name();
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param rce the rce
	 * @param args the args
	 */
	public DmException(DmErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	/**
	 * Instantiates a new dm exception.
	 *
	 * @param rce the rce
	 * @param args the args
	 * @param e the e
	 */
	public DmException(DmErrorCodeEnum rce, Object[] args, Throwable e) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()), e);
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