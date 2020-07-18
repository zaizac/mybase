/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.exception;


import java.io.PrintStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class NotServiceException extends Throwable {

	private static final long serialVersionUID = -914767063887832909L;

	private static final Logger LOGGER = LoggerFactory.getLogger(NotServiceException.class);


	public NotServiceException() {
		super();
	}


	@Override
	public String getMessage() {
		return super.getMessage();
	}


	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}


	@Override
	public synchronized Throwable getCause() {
		LOGGER.error("Comes to Throwable getCause");
		// TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		return super.getCause();
	}


	@Override
	public synchronized Throwable initCause(Throwable cause) {
		LOGGER.error("Comes to Throwable initCause cause.getMessage() :" + cause.getMessage());
		// TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		return super.initCause(cause);
	}


	@Override
	public String toString() {
		return super.toString();
	}


	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}


	@Override
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
	}


	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}


	@Override
	public synchronized Throwable fillInStackTrace() {
		LOGGER.error("Comes to Throwable fillInStackTrace");
		// TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		return super.fillInStackTrace();
	}


	@Override
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}


	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}

}
