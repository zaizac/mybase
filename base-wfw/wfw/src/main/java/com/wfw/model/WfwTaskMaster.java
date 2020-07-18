/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_TASK_MASTER")
public class WfwTaskMaster extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6867307647667514113L;

	@Id
	@Column(name = "TASK_MASTER_ID")
	private String taskMasterId;

	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "REF_LEVEL_ID")
	private Integer levelId;

	@Column(name = "REF_STATUS_ID")
	private Integer statusId;

	@Column(name = "PREV_LEVEL_ID")
	private Integer prevLevelId;

	@Column(name = "PREV_STATUS_ID")
	private Integer prevStatusId;

	@Column(name = "APPLICATION_REF_NO")
	private String appRefNo;

	@Column(name = "APPLICATION_DATE")
	private Date appDate;

	@Column(name = "APPLICATION_STATUS")
	private String appStatus;

	@Column(name = "APPLICANT_ID")
	private String applicantId;

	@Column(name = "APPLICANT")
	private String applicant;

	@Column(name = "SUBMIT_BY_USER_ID")
	private String submitBy;

	@Column(name = "SUBMIT_BY_NAME")
	private String submitByName;

	@Column(name = "CLAIM_BY_USER_ID")
	private String claimBy;

	@Column(name = "CLAIM_BY_NAME")
	private String claimByName;

	@Column(name = "CLAIM_DATE")
	private Date claimDate;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "ATTEMPT_CNT")
	private Integer attemptCnt;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "DISPLAY")
	private boolean display;

	@Column(name = "ADDITIONAL_INFO")
	private String additionalInfo;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REF_TYPE_ID", referencedColumnName = "REF_TYPE_ID", insertable = false, updatable = false, nullable = false)
	private WfwRefType refType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REF_STATUS_ID", referencedColumnName = "REF_STATUS_ID", insertable = false, updatable = false, nullable = false)
	private WfwRefStatus refStatus;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REF_LEVEL_ID", referencedColumnName = "REF_LEVEL_ID", insertable = false, updatable = false, nullable = false)
	private WfwRefLevel refLevel;


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
	 * @return the appDate
	 */
	public Date getAppDate() {
		return appDate;
	}


	/**
	 * @param appDate
	 *             the appDate to set
	 */
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
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
	public boolean getDisplay() {
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
	 * @return the createId
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the createDt
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the updateId
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the updateDt
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the refType
	 */
	public WfwRefType getRefType() {
		return refType;
	}


	/**
	 * @param refType
	 *             the refType to set
	 */
	public void setRefType(WfwRefType refType) {
		this.refType = refType;
	}


	/**
	 * @return the refStatus
	 */
	public WfwRefStatus getRefStatus() {
		return refStatus;
	}


	/**
	 * @param refStatus
	 *             the refStatus to set
	 */
	public void setRefStatus(WfwRefStatus refStatus) {
		this.refStatus = refStatus;
	}


	/**
	 * @return the refLevel
	 */
	public WfwRefLevel getRefLevel() {
		return refLevel;
	}


	/**
	 * @param refLevel
	 *             the refLevel to set
	 */
	public void setRefLevel(WfwRefLevel refLevel) {
		this.refLevel = refLevel;
	}

}
