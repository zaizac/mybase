/**
 * Copyright 2019. 
 */
package com.idm.sdk.exception;


import java.text.MessageFormat;

import com.idm.sdk.constants.IdmErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private final String internalErrorCode;


	public IdmException(String internalErrorCode) {
		super(internalErrorCode);
		this.internalErrorCode = internalErrorCode;
	}


	public IdmException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public IdmException(IdmErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public IdmException(IdmErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}