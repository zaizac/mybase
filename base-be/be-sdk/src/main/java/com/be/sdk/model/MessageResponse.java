/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.DateUtil;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateTimeSerializer;


/**
 * @author nurul.naimma
 *
 * @since 20 Mar 2019
 */

@JsonInclude(Include.NON_NULL)
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = -3882440604404556006L;

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S)
	private Timestamp timestamp;

	private int status;

	private String code;

	private String message;

	private String path;

	private Object response;


	public MessageResponse() {
	}


	public MessageResponse(BeErrorCodeEnum codeEnum, String path) {
		timestamp = DateUtil.getSQLTimestamp();
		status = codeEnum.getCode();
		code = codeEnum.name();
		message = codeEnum.getMessage();
		this.path = path;
	}


	public MessageResponse(BeErrorCodeEnum codeEnum, String path, String msg) {
		timestamp = DateUtil.getSQLTimestamp();
		status = codeEnum.getCode();
		code = codeEnum.name();
		message = codeEnum.getMessage().replace("{0}", "[" + msg + "]");
		this.path = path;
	}


	public MessageResponse(BeErrorCodeEnum codeEnum, String path, Object response) {
		timestamp = DateUtil.getSQLTimestamp();
		status = codeEnum.getCode();
		code = codeEnum.name();
		message = codeEnum.getMessage();
		this.path = path;
		this.response = response;
	}


	public MessageResponse(Timestamp timestamp, int status, String code, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}


	public MessageResponse(Timestamp timestamp, int status, String code, String message, String path,
			Object response) {
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
		this.response = response;
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


	public Object getResponse() {
		return response;
	}


	public void setResponse(Object response) {
		this.response = response;
	}

}
