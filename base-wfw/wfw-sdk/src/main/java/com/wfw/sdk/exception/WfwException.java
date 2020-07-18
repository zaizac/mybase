/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.exception;


import java.text.MessageFormat;

import com.wfw.sdk.constants.WfwErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 10, 2015
 */
public class WfwException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private final String internalErrorCode;


	public WfwException() {
		super();
		internalErrorCode = "";
	}


	public WfwException(String s) {
		super(s);
		internalErrorCode = "";
	}


	public WfwException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public WfwException(WfwErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public WfwException(WfwErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}