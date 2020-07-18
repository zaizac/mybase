/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.exception;


import java.text.MessageFormat;

import com.baseboot.dm.sdk.client.constants.DmErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DmException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private String internalErrorCode;


	public DmException() {
		super();
	}


	public DmException(String s) {
		super(s);
	}


	public DmException(String string, Throwable e) {
		super(string, e);
	}


	public DmException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}
	
	public DmException(String internalCode, String reason, Throwable e) {
		super(reason, e);
		internalErrorCode = internalCode;
	}


	public DmException(DmErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}

	public DmException(DmErrorCodeEnum rce, Throwable e) {
		super(rce.getMessage(), e);
		internalErrorCode = rce.name();
	}

	public DmException(DmErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}

	public DmException(DmErrorCodeEnum rce, Object[] args, Throwable e) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()), e);
		internalErrorCode = rce.name();
	}

	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}