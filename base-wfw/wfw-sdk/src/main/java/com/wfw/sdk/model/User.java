/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.sql.Timestamp;


/**
 * @author mary.jane
 * @since Dec 28, 2018
 */
public class User {

	private Integer wfUserId;

	private String name;

	private String userName;

	private String password;

	private String userRole;

	private String sex;

	private String isActive;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;


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
		this.name = name;
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
