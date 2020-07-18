/**
 * Copyright 2019. 
 */
package com.util.http.exception;


import java.text.MessageFormat;

import com.util.constants.ResponseCodeEnum;


/**
 * The Class HttpBaseException.
 *
 * @author mary.jane
 * @since Jan 15, 2019
 */
public class HttpBaseException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5737060323598459537L;

	/** The internal error code. */
	private final String internalErrorCode;


	/**
	 * Instantiates a new http base exception.
	 *
	 * @param internalErrorCode the internal error code
	 */
	public HttpBaseException(String internalErrorCode) {
		super(internalErrorCode);
		this.internalErrorCode = internalErrorCode;
	}


	/**
	 * Instantiates a new http base exception.
	 *
	 * @param internalCode the internal code
	 * @param reason the reason
	 */
	public HttpBaseException(String internalCode, String reason) {
		super(reason);
		internalErrorCode = internalCode;
	}


	/**
	 * Instantiates a new http base exception.
	 *
	 * @param rce the rce
	 */
	public HttpBaseException(ResponseCodeEnum rce) {
		super(rce.getMessage());
		internalErrorCode = rce.name();
	}


	/**
	 * Instantiates a new http base exception.
	 *
	 * @param rce the rce
	 * @param args the args
	 */
	public HttpBaseException(ResponseCodeEnum rce, Object[] args) {
		super((args != null ? MessageFormat.format(rce.getMessage(), args) : rce.getMessage()));
		internalErrorCode = rce.name();
	}


	/**
	 * Gets the internal error code.
	 *
	 * @return the internal error code
	 */
	public String getInternalErrorCode() {
		return internalErrorCode;
	}

}