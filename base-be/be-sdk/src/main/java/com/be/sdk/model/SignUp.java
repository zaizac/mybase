/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author mary.jane
 *
 */
public class SignUp implements Serializable {

	private int id;

	private String refNo;

	private String fullName;

	private String email;

	private String status;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;

	private String taskMasterId;

	private String statusCd;

	private String remark;

	private String taskAuthorId;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getTaskMasterId() {
		return taskMasterId;
	}


	public void setTaskMasterId(String taskMasterId) {
		this.taskMasterId = taskMasterId;
	}


	public String getStatusCd() {
		return statusCd;
	}


	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getTaskAuthorId() {
		return taskAuthorId;
	}


	public void setTaskAuthorId(String taskAuthorId) {
		this.taskAuthorId = taskAuthorId;
	}

}
