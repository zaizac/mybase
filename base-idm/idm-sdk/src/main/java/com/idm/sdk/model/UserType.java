package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserType implements Serializable {

	private static final long serialVersionUID = -8384322779505544126L;

	private String userTypeCode;

	private LangDesc userTypeDesc;

	private Boolean emailAsUserId;

	private String createId;
	
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;


	public String getUserTypeCode() {
		return userTypeCode;
	}


	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}


	public LangDesc getUserTypeDesc() {
		return userTypeDesc;
	}


	public void setUserTypeDesc(LangDesc userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
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


	public Boolean getEmailAsUserId() {
		return emailAsUserId;
	}


	public void setEmailAsUserId(boolean emailAsUserId) {
		this.emailAsUserId = emailAsUserId;
	}

}
