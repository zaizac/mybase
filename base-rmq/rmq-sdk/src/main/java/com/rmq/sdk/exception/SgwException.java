/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.sdk.exception;


import java.text.MessageFormat;

import com.rmq.sdk.client.constants.SgwErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private String internalErrorCode;


	public SgwException() {
		super();
	}


	public SgwException(String s) {
		super(s);
	}


	public SgwException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public SgwException(SgwErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public SgwException(SgwErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}