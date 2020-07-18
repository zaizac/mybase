/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserType implements Serializable {

	private static final long serialVersionUID = -8384322779505544126L;

	private String userTypeId;

	private String userTypeCode;

	private String userTypeDesc;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;


	public String getUserTypeCode() {
		return userTypeCode;
	}


	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}


	public String getUserTypeDesc() {
		return userTypeDesc;
	}


	public void setUserTypeDesc(String userTypeDesc) {
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


	public String getUserTypeId() {
		return userTypeId;
	}


	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

}
