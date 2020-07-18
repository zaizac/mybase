/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wfw.sdk.util.CmnUtil;


/**
 * WfwStatus entity.
 * 
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Entity
@Table(name = "WFW_STATUS")
public class WfwStatus extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "STATUS_ID")
	private String statusId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "APPL_STS_ID")
	private String applStsId;

	@Column(name = "FLOW_NO")
	private Integer flowNo;

	@Column(name = "TYPE_ID")
	private String typeId;

	@Column(name = "LEVEL_ID")
	private String levelId;

	@Column(name = "NEXT_PROC_ID")
	private String nextProcId;

	@Column(name = "IS_DISPLAY")
	private String isDisplay;

	@Column(name = "IS_INITIAL_STS")
	private String isInitialSts;

	@Column(name = "IS_EMAIL_REQUIRED")
	private String isEmailRequired;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_SKIP_APPRVR")
	private String isSkipApprvr;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwStatus() {
	}


	public String getStatusId() {
		return statusId;
	}


	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = CmnUtil.getStrUpper(description);
	}


	public Integer getFlowNo() {
		return flowNo;
	}


	public void setFlowNo(Integer flowNo) {
		this.flowNo = flowNo;
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


	public String getApplStsId() {
		return applStsId;
	}


	public void setApplStsId(String applStsId) {
		this.applStsId = applStsId;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getLevelId() {
		return levelId;
	}


	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public String getNextProcId() {
		return nextProcId;
	}


	public void setNextProcId(String nextProcId) {
		this.nextProcId = nextProcId;
	}


	public String getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}


	public String getIsInitialSts() {
		return isInitialSts;
	}


	public void setIsInitialSts(String isInitialSts) {
		this.isInitialSts = isInitialSts;
	}


	public String getIsEmailRequired() {
		return isEmailRequired;
	}


	public void setIsEmailRequired(String isEmailRequired) {
		this.isEmailRequired = isEmailRequired;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getIsSkipApprvr() {
		return isSkipApprvr;
	}


	public void setIsSkipApprvr(String isSkipApprvr) {
		this.isSkipApprvr = isSkipApprvr;
	}

}