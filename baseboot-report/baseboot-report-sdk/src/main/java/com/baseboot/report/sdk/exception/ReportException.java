/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.exception;


import java.text.MessageFormat;

import com.baseboot.report.sdk.constants.ReportErrorCodeEnum;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
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