/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.sgw.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sgw.sdk.client.constants.BaseConstants;
import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.util.serializer.JsonDateTimeSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
//@JsonInclude(Include.NON_NULL)
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = -423519805431834764L;

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S)
	private Timestamp timestamp;

	private Integer status;

	private String code;

	private String message;

	private String path;
	
	private String locale;

	private Object result;


	public MessageResponse() {
	}


	public MessageResponse(SgwErrorCodeEnum sgwEnum, String path) {
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


	public MessageResponse(Object result, Timestamp timestamp, int status, String code, String message, String path) {
		this.result = result;
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}


	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}


	/**
	 * @param result
	 *             the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
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


	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}


	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
