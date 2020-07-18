/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.constant;


/**
 * @author Mary Jane Buenaventura
 * @since 02/10/2014
 */
public class ErrorConstants {

	private ErrorConstants() {
		throw new IllegalStateException("ErrorConstants Utility class");
	}


	public static final String INVALID_LENGTH = "ERR01001"; // Invalid Data
													// Length

	public static final String INVALID_TYPE = "ERR01002"; // Invalid Data Type

	public static final String REC_NOT_FOUND = "ERR02001"; // Record does not
												// exist

	public static final String REC_CREATE_SUCCESS = "ERR02002"; // Record
													// creation
													// successful

	public static final String REC_CREATE_FAILED = "ERR02003"; // Record
													// creation
													// failed

	public static final String REC_UPDATE_SUCCESS = "ERR02004"; // Record
													// update
													// successful

	public static final String REC_UPDATE_FAILED = "ERR02005"; // Record update
													// failed

	public static final String REC_DELETE_SUCCESS = "ERR02006"; // Record
													// deletion
													// successful

	public static final String REC_DELETE_FAILED = "ERR02007"; // Record
													// deletion
													// failed

	public static final String MAX_USER_LIMIT = "ERR03001"; // Reached maximum
													// user
													// creation
													// limit

}
