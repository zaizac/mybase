/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateSerializer;


/**
 * @author michelle.angela
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TaskMaster implements Serializable {

	private static final long serialVersionUID = 217342895775007990L;

	private String taskMasterId;

	private Integer typeId;

	private Integer levelId;

	private Integer statusId;

	private String statusDesc;

	private Integer prevLevelId;

	private Integer prevStatusId;

	private String appRefNo;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDate;

	private String appStatus;

	private String applicantId;

	private String applicant;

	private String submitBy;

	private String submitByName;

	private String claimBy;

	private String claimByName;

	private Date claimDate;

	private String remark;

	private Integer attemptCnt;

	private Integer display;

	private String additionalInfo;

	private String roles;

	private List<TaskHistory> taskHistoryList;

	private RefStatus refStatus;

	private RefLevel refLevel;

	private RefStatus prevStatus;

	private RefLevel prevLevel;

	private WfwReference refType;


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
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}


	/**
	 * @param statusDesc
	 *             the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	/**
	 * @return the prevLevelId
	 */
	public Integer getPrevLevelId() {
		return prevLevelId;
	}


	/**
	 * @param prevLevelId
	 *             the prevLevelId to set
	 */
	public void setPrevLevelId(Integer prevLevelId) {
		this.prevLevelId = prevLevelId;
	}


	/**
	 * @return the prevStatusId
	 */
	public Integer getPrevStatusId() {
		return prevStatusId;
	}


	/**
	 * @param prevStatusId
	 *             the prevStatusId to set
	 */
	public void setPrevStatusId(Integer prevStatusId) {
		this.prevStatusId = prevStatusId;
	}


	/**
	 * @return the applicationRefNo
	 */
	public String getAppRefNo() {
		return appRefNo;
	}


	/**
	 * @param applicationRefNo
	 *             the applicationRefNo to set
	 */
	public void setAppRefNo(String applicationRefNo) {
		appRefNo = applicationRefNo;
	}


	/**
	 * @return the applicationDate
	 */
	public Date getAppDate() {
		return appDate;
	}


	/**
	 * @param applicationDate
	 *             the applicationDate to set
	 */
	public void setAppDate(Date applicationDate) {
		appDate = applicationDate;
	}


	/**
	 * @return the applicationStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}


	/**
	 * @param applicationStatus
	 *             the applicationStatus to set
	 */
	public void setAppStatus(String applicationStatus) {
		appStatus = applicationStatus;
	}


	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}


	/**
	 * @param applicantId
	 *             the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}


	/**
	 * @return the applicant
	 */
	public String getApplicant() {
		return applicant;
	}


	/**
	 * @param applicant
	 *             the applicant to set
	 */
	public void setApplicant(String applicant) {
		this.applicant = applicant;
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
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}


	/**
	 * @param roles
	 *             the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}


	/**
	 * @return the taskHistoryList
	 */
	public List<TaskHistory> getTaskHistoryList() {
		return taskHistoryList;
	}


	/**
	 * @param taskHistoryList
	 *             the taskHistoryList to set
	 */
	public void setTaskHistoryList(List<TaskHistory> taskHistoryList) {
		this.taskHistoryList = taskHistoryList;
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


	public RefLevel getRefLevel() {
		return refLevel;
	}


	public void setRefLevel(RefLevel refLevel) {
		this.refLevel = refLevel;
	}


	public WfwReference getRefType() {
		return refType;
	}


	public void setRefType(WfwReference refType) {
		this.refType = refType;
	}


	public RefStatus getPrevStatus() {
		return prevStatus;
	}


	public void setPrevStatus(RefStatus prevStatus) {
		this.prevStatus = prevStatus;
	}


	public RefLevel getPrevLevel() {
		return prevLevel;
	}


	public void setPrevLevel(RefLevel prevLevel) {
		this.prevLevel = prevLevel;
	}

}
