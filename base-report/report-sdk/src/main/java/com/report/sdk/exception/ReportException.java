/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.report.sdk.exception;


import java.text.MessageFormat;

import com.report.sdk.constants.ReportErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 10, 2015
 */
public class ReportException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;

	private String internalErrorCode;


	public ReportException() {
		super();
	}


	public ReportException(String s) {
		super(s);
	}


	public ReportException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	public ReportException(ReportErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	public ReportException(ReportErrorCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}