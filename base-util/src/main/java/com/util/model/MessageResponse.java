/**
 * Copyright 2019. 
 */
package com.util.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateTimeSerializer;


/**
 * The Class MessageResponse.
 *
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@JsonInclude(Include.NON_NULL)
public class MessageResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -423519805431834764L;

	/** The timestamp. */
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S)
	private Timestamp timestamp;

	/** The status. */
	private int status;

	/** The code. */
	private String code;

	/** The message. */
	private String message;

	/** The path. */
	private String path;


	/**
	 * Instantiates a new message response.
	 */
	public MessageResponse() {
		// DO NOTHING
	}


	/**
	 * Instantiates a new message response.
	 *
	 * @param timestamp the timestamp
	 * @param status the status
	 * @param code the code
	 * @param message the message
	 * @param path the path
	 */
	public MessageResponse(Timestamp timestamp, int status, String code, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}


	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}


	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}


	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
