/**
 * Copyright 2019. 
 */
package com.dm.exception;


/**
 * The Class DocumentsAlreadyExistsException.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DocumentsAlreadyExistsException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7171973856759474948L;


	/**
	 * Instantiates a new documents already exists exception.
	 *
	 * @param msg the msg
	 */
	public DocumentsAlreadyExistsException(String msg) {
		super(msg);
	}


	/**
	 * Instantiates a new documents already exists exception.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public DocumentsAlreadyExistsException(String msg, Throwable t) {
		super(msg, t);
	}

}