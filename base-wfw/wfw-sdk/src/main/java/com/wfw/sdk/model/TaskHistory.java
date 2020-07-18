/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author michelle.angela
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TaskHistory implements Serializable {

	private static final long serialVersionUID = 9151489423037093111L;

	private String taskHistoryId;

	private String taskMasterId;

	private Integer typeId;

	private Integer levelId;

	private Integer statusId;

	private String appStatus;

	private String submitBy;

	private String submitByName;

	private String action;

	private String claimBy;

	private String claimByName;

	private Date claimDate;

	private String remark;

	private Integer attemptCnt;

	private Integer display;

	private String additionalInfo;

	private Timestamp createDt;

	private RefStatus refStatus;

	private RefLevel refLevel;


	/**
	 * @return the taskHistoryId
	 */
	public String getTaskHistoryId() {
		return taskHistoryId;
	}


	/**
	 * @param taskHistoryId
	 *             the taskHistoryId to set
	 */
	public void setTaskHistoryId(String taskHistoryId) {
		this.taskHistoryId = taskHistoryId;
	}


	/**
	 * @return the taskMasterId
	 */
	public String getTaskMasterId() {
		return taskMasterId;
	}


	/**
	 * @param taskMasterId
	 *             the taskMasterId to set
	 */
	public void setTaskMasterId(String taskMasterId) {
		this.taskMasterId = taskMasterId;
	}


	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}


	/**
	 * @param typeId
	 *             the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}


	/**
	 * @param levelId
	 *             the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}


	/**
	 * @return the statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}


	/**
	 * @param statusId
	 *             the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}


	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}


	/**
	 * @param appStatus
	 *             the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}


	/**
	 * @return the submitBy
	 */
	public String getSubmitBy() {
		return submitBy;
	}


	/**
	 * @param submitBy
	 *             the submitBy to set
	 */
	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}


	/**
	 * @return the submitByName
	 */
	public String getSubmitByName() {
		return submitByName;
	}


	/**
	 * @param submitByName
	 *             the submitByName to set
	 */
	public void setSubmitByName(String submitByName) {
		this.submitByName = submitByName;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}


	/**
	 * @param action
	 *             the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}


	/**
	 * @return the claimBy
	 */
	public String getClaimBy() {
		return claimBy;
	}


	/**
	 * @param claimBy
	 *             the claimBy to set
	 */
	public void setClaimBy(String claimBy) {
		this.claimBy = claimBy;
	}


	/**
	 * @return the claimByName
	 */
	public String getClaimByName() {
		return claimByName;
	}


	/**
	 * @param claimByName
	 *             the claimByName to set
	 */
	public void setClaimByName(String claimByName) {
		this.claimByName = claimByName;
	}


	/**
	 * @return the claimDate
	 */
	public Date getClaimDate() {
		return claimDate;
	}


	/**
	 * @param claimDate
	 *             the claimDate to set
	 */
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}


	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark
	 *             the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	 * @return the attemptCnt
	 */
	public Integer getAttemptCnt() {
		return attemptCnt;
	}


	/**
	 * @param attemptCnt
	 *             the attemptCnt to set
	 */
	public void setAttemptCnt(Integer attemptCnt) {
		this.attemptCnt = attemptCnt;
	}


	/**
	 * @return the display
	 */
	public Integer getDisplay() {
		return display;
	}


	/**
	 * @param display
	 *             the display to set
	 */
	public void setDisplay(Integer display) {
		this.display = display;
	}


	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}


	/**
	 * @param additionalInfo
	 *             the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}


	/**
	 * @return the refStatus
	 */
	public RefStatus getRefStatus() {
		return refStatus;
	}


	/**
	 * @param refStatus
	 *             the refStatus to set
	 */
	public void setRefStatus(RefStatus refStatus) {
		this.refStatus = refStatus;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public RefLevel getRefLevel() {
		return refLevel;
	}


	public void setRefLevel(RefLevel refLevel) {
		this.refLevel = refLevel;
	}

}
