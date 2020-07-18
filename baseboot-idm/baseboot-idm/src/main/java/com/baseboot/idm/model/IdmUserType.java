/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_USER_TYPE")
public class IdmUserType implements Serializable {

	private static final long serialVersionUID = 8513477949752093410L;

	@Id
	@Column(name="USR_TYPE_CODE")
	private String userTypeCode;
	
	@Column(name="USR_TYPE_DESC")
	private String userTypeDesc;
	
	@Column(name = "CREATE_ID")
	private String createId;
	
	@Column(name = "CREATE_DT")
	private Timestamp createDt;
	
	@Column(name = "UPDATE_ID")
	private String updateId;
	
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;
	
	public IdmUserType() {}

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

}