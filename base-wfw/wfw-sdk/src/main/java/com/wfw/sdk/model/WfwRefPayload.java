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
public class WfwRefPayload implements Serializable {

	private static final long serialVersionUID = -70850413263360956L;

	private String taskMasterId;

	private Integer typeId;

	private Integer levelId;

	private Integer statusId;

	private String appRefNo;

	private String appStatus;

	private String applicantId;

	private String applicant;

	private String submitBy;

	private String submitByName;

	private String claimBy;

	private String claimByName;

	private Date claimDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateFrom;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateTo;

	private String additionalInfo;

	private String taskHistoryId;

	private String action;

	private List<String> roles;

	private List<Integer> levelList;

	private String statusCd;

	private String statuses;

	private List<String> statusList;

	private List<String> taskMasterIdList;

	private String taskAuthorId;

	private String taskAuthorName;

	private boolean autoClaim;

	private String tranId;

	private boolean history;

	private boolean pool;

	private String remark;

	private boolean display;

	private Integer prevLevelId;

	private Integer prevStatusId;

	private Integer active;

	private String moduleId;

	private String type;

	private String globalParam;
	
	private String profId;
	
	private String raId;
	
	private String empProfId;
	
	private String empIndId;
	
	private String inboxType;


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
	 * @return the appRefNo
	 */
	public String getAppRefNo() {
		return appRefNo;
	}


	/**
	 * @param appRefNo
	 *             the appRefNo to set
	 */
	public void setAppRefNo(String appRefNo) {
		this.appRefNo = appRefNo;
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
	 * @return the appDateFrom
	 */
	public Date getAppDateFrom() {
		return appDateFrom;
	}


	/**
	 * @param appDateFrom
	 *             the appDateFrom to set
	 */
	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}


	/**
	 * @return the appDateTo
	 */
	public Date getAppDateTo() {
		return appDateTo;
	}


	/**
	 * @param appDateTo
	 *             the appDateTo to set
	 */
	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}


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


	// /**
	// * @return the roles
	// */
	// public String getRoles() {
	// return roles;
	// }
	//
	//
	// /**
	// * @param roles
	// * the roles to set
	// */
	// public void setRoles(String roles) {
	// this.roles = roles;
	// }

	/**
	 * @return the levelList
	 */
	public List<Integer> getLevelList() {
		return levelList;
	}


	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}


	/**
	 * @param roles
	 *             the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	/**
	 * @param levelList
	 *             the levelList to set
	 */
	public void setLevelList(List<Integer> levelList) {
		this.levelList = levelList;
	}


	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}


	/**
	 * @param statusCd
	 *             the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	/**
	 * @return the statuses
	 */
	public String getStatuses() {
		return statuses;
	}


	/**
	 * @param statuses
	 *             the statuses to set
	 */
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}


	/**
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}


	/**
	 * @param statusList
	 *             the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}


	/**
	 * @return the taskMasterIdList
	 */
	public List<String> getTaskMasterIdList() {
		return taskMasterIdList;
	}


	/**
	 * @param taskMasterIdList
	 *             the taskMasterIdList to set
	 */
	public void setTaskMasterIdList(List<String> taskMasterIdList) {
		this.taskMasterIdList = taskMasterIdList;
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
	 * @return the autoClaim
	 */
	public boolean isAutoClaim() {
		return autoClaim;
	}


	/**
	 * @param autoClaim
	 *             the autoClaim to set
	 */
	public void setAutoClaim(boolean autoClaim) {
		this.autoClaim = autoClaim;
	}


	/**
	 * @return the tranId
	 */
	public String getTranId() {
		return tranId;
	}


	/**
	 * @param tranId
	 *             the tranId to set
	 */
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	/**
	 * @return the history
	 */
	public boolean isHistory() {
		return history;
	}


	/**
	 * @param history
	 *             the history to set
	 */
	public void setHistory(boolean history) {
		this.history = history;
	}


	/**
	 * @return the taskAuthorId
	 */
	public String getTaskAuthorId() {
		return taskAuthorId;
	}


	/**
	 * @param taskAuthorId
	 *             the taskAuthorId to set
	 */
	public void setTaskAuthorId(String taskAuthorId) {
		this.taskAuthorId = taskAuthorId;
	}


	/**
	 * @return the taskAuthorName
	 */
	public String getTaskAuthorName() {
		return taskAuthorName;
	}


	/**
	 * @param taskAuthorName
	 *             the taskAuthorName to set
	 */
	public void setTaskAuthorName(String taskAuthorName) {
		this.taskAuthorName = taskAuthorName;
	}


	/**
	 * @return the pool
	 */
	public boolean isPool() {
		return pool;
	}


	/**
	 * @param pool
	 *             the pool to set
	 */
	public void setPool(boolean pool) {
		this.pool = pool;
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
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}


	/**
	 * @param display
	 *             the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
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
	 * @return the active
	 */
	public Integer getActive() {
		return active;
	}


	/**
	 * @param active
	 *             the active to set
	 */
	public void setActive(Integer active) {
		this.active = active;
	}


	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}


	/**
	 * @param moduleId
	 *             the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the globalParam
	 */
	public String getGlobalParam() {
		return globalParam;
	}


	/**
	 * @param globalParam
	 *             the globalParam to set
	 */
	public void setGlobalParam(String globalParam) {
		this.globalParam = globalParam;
	}


	public String getProfId() {
		return profId;
	}


	public void setProfId(String profId) {
		this.profId = profId;
	}


	public String getRaId() {
		return raId;
	}


	public void setRaId(String raId) {
		this.raId = raId;
	}


	public String getEmpProfId() {
		return empProfId;
	}


	public void setEmpProfId(String empProfId) {
		this.empProfId = empProfId;
	}


	public String getEmpIndId() {
		return empIndId;
	}


	public void setEmpIndId(String empIndId) {
		this.empIndId = empIndId;
	}


	public String getInboxType() {
		return inboxType;
	}


	public void setInboxType(String inboxType) {
		this.inboxType = inboxType;
	}
	
	

}
