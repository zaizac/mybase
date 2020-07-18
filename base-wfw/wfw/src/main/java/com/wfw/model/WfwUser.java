/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wfw.sdk.util.CmnUtil;


/**
 * WfwUser entity.
 * 
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Entity
@Table(name = "WFW_USER")
public class WfwUser extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "WF_USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer wfUserId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "USER_ROLE")
	private String userRole;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwUser() {
	}


	public Integer getWfUserId() {
		return wfUserId;
	}


	public void setWfUserId(Integer wfUserId) {
		this.wfUserId = wfUserId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = CmnUtil.getStrUpper(name);
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
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