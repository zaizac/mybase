/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = 5302969888427091997L;

	private Integer id;

	private String roleCode;

	private String roleDesc;

	private String roleType;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;

	private String selected;


	public UserRole() {
	}


	public UserRole(String roleCode, String roleDesc) {
		this.roleCode = roleCode;
		this.roleDesc = roleDesc;
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


	public String getRoleType() {
		return roleType;
	}


	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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


	public String getSelected() {
		return selected;
	}


	public void setSelected(String selected) {
		this.selected = selected;
	}

}