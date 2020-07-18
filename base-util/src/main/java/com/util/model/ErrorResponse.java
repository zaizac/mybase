/**
 * Copyright 2019. 
 */
package com.util.model;


import java.io.Serializable;


/**
 * The Class ErrorResponse.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ErrorResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -423519805431834764L;

	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/** The error exception. */
	private String errorException;


	/**
	 * Instantiates a new error response.
	 */
	public ErrorResponse() {
	}


	/**
	 * Instantiates a new error response.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 */
	public ErrorResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}


	/**
	 * Instantiates a new error response.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 * @param errorException the error exception
	 */
	public ErrorResponse(String errorCode, String errorMessage, String errorException) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorException = errorException;
	}


	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}


	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * Gets the error exception.
	 *
	 * @return the error exception
	 */
	public String getErrorException() {
		return errorException;
	}


	/**
	 * Sets the error exception.
	 *
	 * @param errorException the new error exception
	 */
	public void setErrorException(String errorException) {
		this.errorException = errorException;
	}

}
