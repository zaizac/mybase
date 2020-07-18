/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@JsonInclude(Include.NON_NULL)
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = -423519805431834764L;

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S)
	private Timestamp timestamp;

	private int status;

	private String code;

	private String message;

	private String path;


	public MessageResponse() {
	}


	public MessageResponse(IdmErrorCodeEnum sgwEnum, String path) {
		timestamp = new Timestamp(new Date().getTime());
		status = sgwEnum.getCode();
		code = sgwEnum.name();
		message = sgwEnum.getMessage();
		this.path = path;
	}


	public MessageResponse(Timestamp timestamp, int status, String code, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

}
