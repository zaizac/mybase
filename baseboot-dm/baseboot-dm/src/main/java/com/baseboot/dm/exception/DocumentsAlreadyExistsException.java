/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.exception;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DocumentsAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -7171973856759474948L;


	public DocumentsAlreadyExistsException(String msg) {
		super(msg);
	}


	public DocumentsAlreadyExistsException(String msg, Throwable t) {
		super(msg, t);
	}

}