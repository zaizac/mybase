/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.rmq.httpclient;

import java.text.MessageFormat;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class EsbException extends RuntimeException {

	private static final long serialVersionUID = -5876380106101907503L;
	
	private String internalErrorCode;
	
	public EsbException() {
		super();
	}
	
	public EsbException(String s) {
		super(s);
	}
	 
    public EsbException(String internalCode, String reason) {
    	super(reason);
    	internalErrorCode = internalCode;
    }
	
	public EsbException(EsbErrorCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}
	 
	public EsbException(EsbErrorCodeEnum rce, Object[] args) {
		super((args!= null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}
	
	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}
