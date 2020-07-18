/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = -423519805431834764L;

	private String errorCode;

	private String errorMessage;

	private String errorException;


	public ErrorResponse() {
	}


	public ErrorResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}


	public ErrorResponse(String errorCode, String errorMessage, String errorException) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorException = errorException;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getErrorException() {
		return errorException;
	}


	public void setErrorException(String errorException) {
		this.errorException = errorException;
	}

}
