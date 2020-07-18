package com.idm.sdk.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;

public class Fcm implements Serializable{
	
	private static final long serialVersionUID = 8113508316031366864L;

	private Integer fcmId;

	private String userId;

	private String pinCd;

	private String faceId;

	private boolean status;
	
	private List<FcmDevice> fcmDevices;

	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;
	
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	
	public Integer getFcmId() {
		return fcmId;
	}

	
	public void setFcmId(Integer fcmId) {
		this.fcmId = fcmId;
	}

	
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getPinCd() {
		return pinCd;
	}

	
	public void setPinCd(String pinCd) {
		this.pinCd = pinCd;
	}

	
	public String getFaceId() {
		return faceId;
	}

	
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	
	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the fcmDevices
	 */
	public List<FcmDevice> getFcmDevices() {
		return fcmDevices;
	}


	/**
	 * @param fcmDevices the fcmDevices to set
	 */
	public void setFcmDevices(List<FcmDevice> fcmDevices) {
		this.fcmDevices = fcmDevices;
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
}
