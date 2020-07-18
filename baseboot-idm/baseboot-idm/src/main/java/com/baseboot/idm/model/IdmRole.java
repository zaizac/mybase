/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_ROLE")
@JsonInclude(Include.NON_NULL)
public class IdmRole implements java.io.Serializable {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "ROLE_CODE")
	private String roleCode;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "ROLE_TYPE")
	private String roleType;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public IdmRole() {
	}


	public String getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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


	public String getRoleType() {
		return roleType;
	}


	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}