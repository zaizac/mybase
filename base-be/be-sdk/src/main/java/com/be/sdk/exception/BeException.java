/**
 * Copyright 2019
 */
package com.be.sdk.exception;


import java.text.MessageFormat;

import com.be.sdk.constants.BeErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class BeException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private final String internalErrorCode;


	public BeException(String internalCode) {
		super(internalCode);
		internalErrorCode = internalCode;
	}


	public BeException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public BeException(BeErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public BeException(BeErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}