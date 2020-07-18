/**
 * Copyright 2019. 
 */
package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author mary.jane
 * @since Feb 20, 2019
 */
public class IdmConfigDto implements Serializable {

	private static final long serialVersionUID = 1687384409891732894L;
	
	private Integer configId;

	private String configCode;

	private String configDesc;

	private String configVal;
	
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtTo;

	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtTo;



	public IdmConfigDto() {
		// DO NOTHING
	}
	
	public IdmConfigDto(String configCode, String configDesc, String configVal) {
		this.configCode = configCode;
		this.configDesc = configDesc;
		this.configVal = configVal;
	}


	public IdmConfigDto(Integer configId, String configCode, String configDesc, String configVal) {
		this.configId = configId;
		this.configCode = configCode;
		this.configDesc = configDesc;
		this.configVal = configVal;
	}
	

	public Integer getConfigId() {
		return configId;
	}


	public void setConfigId(Integer configId) {
		this.configId = configId;
	}


	public String getConfigCode() {
		return configCode;
	}


	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}


	public String getConfigDesc() {
		return configDesc;
	}


	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}


	public String getConfigVal() {
		return configVal;
	}


	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public Timestamp getCreateDtFrom() {
		return createDtFrom;
	}


	public void setCreateDtFrom(Timestamp createDtFrom) {
		this.createDtFrom = createDtFrom;
	}


	public Timestamp getCreateDtTo() {
		return createDtTo;
	}


	public void setCreateDtTo(Timestamp createDtTo) {
		this.createDtTo = createDtTo;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public Timestamp getUpdateDtFrom() {
		return updateDtFrom;
	}


	public void setUpdateDtFrom(Timestamp updateDtFrom) {
		this.updateDtFrom = updateDtFrom;
	}


	public Timestamp getUpdateDtTo() {
		return updateDtTo;
	}


	public void setUpdateDtTo(Timestamp updateDtTo) {
		this.updateDtTo = updateDtTo;
	}
	

}
