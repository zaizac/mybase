/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * The Class NotServiceException.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class NotServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -914767063887832909L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotServiceException.class);


	/**
	 * Instantiates a new not service exception.
	 */
	public NotServiceException() {
		super();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	@Override
	public synchronized Throwable getCause() {
		LOGGER.error("Comes to Throwable getCause");
		return super.getCause();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#initCause(java.lang.Throwable)
	 */
	@Override
	public synchronized Throwable initCause(Throwable cause) {
		LOGGER.error("Comes to Throwable initCause cause.getMessage() : {}", cause.getMessage());
		return super.initCause(cause);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
