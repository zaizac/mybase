/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.exception;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class UrlExpireException extends RuntimeException {

	private static final long serialVersionUID = -7171973856759474948L;


	public UrlExpireException(String msg) {
		super(msg);
	}


	public UrlExpireException(String msg, Throwable t) {
		super(msg, t);
	}
}