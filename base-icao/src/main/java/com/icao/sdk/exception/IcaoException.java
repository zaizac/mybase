/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.exception;


import java.text.MessageFormat;

import com.icao.sdk.constants.IcaoErrorCodeEnum;


/**
 * @author mary.jane
 * @since Nov 22, 2018
 */
public class IcaoException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private final String internalErrorCode;


	public IcaoException(String internalErrorCode) {
		super(internalErrorCode);
		this.internalErrorCode = internalErrorCode;
	}


	public IcaoException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public IcaoException(IcaoErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public IcaoException(IcaoErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}