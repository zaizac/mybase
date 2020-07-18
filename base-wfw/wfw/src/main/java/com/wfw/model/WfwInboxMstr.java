/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.util.BaseUtil;
import com.wfw.sdk.util.CmnUtil;


/**
 * WfwInboxMstr entity.
 *
 * @author Mary Jane Buenaventura
 * @since 08/06/2108
 */
@Entity
@Table(name = "WFW_INBOX_MSTR")
public class WfwInboxMstr extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "REF_NO")
	private String refNo;

	@Column(name = "APPL_USER_ID")
	private String applUserId;

	@Column(name = "APPL_USER_NAME")
	private String applUserName;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "APPL_DATE")
	private Date applDate;

	@Column(name = "APPL_STS_ID")
	private String applStsId;

	@Column(name = "APPL_STS_CODE")
	private String applStsCode;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "COMPANY_REG_NO")
	private String companyRegNo;

	@Column(name = "RECRUITMENT_AGENT")
	private String recruitmentAgent;

	@Column(name = "RA_PROF_ID")
	private Integer raProfileId;

	@Column(name = "RA_CMPNY_REG_NO")
	private String raCmpnyRegNo;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "SECTOR")
	private String sector;

	@Column(name = "APPL_REMARK")
	private String applRemark;

	@Column(name = "NO_OF_WORKERS")
	private int noOfWorkers;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "APPROVE_DATE")
	private Date approveDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "INTERVIEW_DATE")
	private Date interviewDate;

	@Column(name = "ATTEMPT_CNT")
	private int attemptCnt;

	@Column(name = "MODULE_ID")
	private String moduleId;

	@Column(name = "TRAN_ID")
	private String tranId;

	@Column(name = "BRANCH_ID")
	private String branchId;

	@Column(name = "LEVEL_ID")
	private String levelId;

	@Column(name = "STATUS_ID")
	private String statusId;

	@Column(name = "LAST_STATUS_ID")
	private String lastStatusId;

	@Column(name = "LAST_STATUS_DESC")
	private String lastStatusDesc;

	@Column(name = "OFFICER_ID")
	private String officerId;

	@Column(name = "OFFICER_NAME")
	private String officerName;

	@Column(name = "IS_DISPLAY")
	private String isDisplay;

	@Column(name = "QUEUE_IND")
	private String queueInd;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "CLAIMED_DATE")
	private Date claimedDate;

	@Transient
	private String typeDesc;

	@Transient
	private String levelDesc;

	@Transient
	private String statusDesc;


	public WfwInboxMstr() {
		//
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getApplUserId() {
		return applUserId;
	}


	public void setApplUserId(String applUserId) {
		this.applUserId = applUserId;
	}


	public String getApplUserName() {
		return applUserName;
	}


	public void setApplUserName(String applUserName) {
		this.applUserName = CmnUtil.getStrUpper(applUserName);
	}


	public Date getApplDate() {
		return applDate;
	}


	public void setApplDate(Date applDate) {
		this.applDate = applDate;
	}


	public String getApplStsId() {
		return applStsId;
	}


	public void setApplStsId(String applStsId) {
		this.applStsId = applStsId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getApplRemark() {
		return applRemark;
	}


	public void setApplRemark(String applRemark) {
		this.applRemark = CmnUtil.getStrUpper(applRemark);
	}


	public String getModuleId() {
		return moduleId;
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public String getTranId() {
		return tranId;
	}


	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getLevelId() {
		return levelId;
	}


	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public String getStatusId() {
		return statusId;
	}


	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}


	public String getLastStatusId() {
		return lastStatusId;
	}


	public void setLastStatusId(String lastStatusId) {
		this.lastStatusId = lastStatusId;
	}


	public String getOfficerId() {
		return officerId;
	}


	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}


	public String getQueueInd() {
		return queueInd;
	}


	public void setQueueInd(String queueInd) {
		this.queueInd = queueInd;
	}


	@Override
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getTypeDesc() {
		return typeDesc;
	}


	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}


	public String getLevelDesc() {
		return levelDesc;
	}


	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}


	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public String getLastStatusDesc() {
		return lastStatusDesc;
	}


	public void setLastStatusDesc(String lastStatusDesc) {
		this.lastStatusDesc = lastStatusDesc;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public int getAttemptCnt() {
		return attemptCnt;
	}


	public void setAttemptCnt(int attemptCnt) {
		this.attemptCnt = attemptCnt;
	}


	public int getNoOfWorkers() {
		return noOfWorkers;
	}


	public void setNoOfWorkers(int noOfWorkers) {
		this.noOfWorkers = noOfWorkers;
	}


	public Date getApproveDate() {
		return approveDate;
	}


	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}


	public String getApplStsCode() {
		return applStsCode;
	}


	public void setApplStsCode(String applStsCode) {
		this.applStsCode = applStsCode;
	}


	public String getCompanyRegNo() {
		return companyRegNo;
	}


	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}


	public String[] getRecruitmentAgent() {
		if (!BaseUtil.isObjNull(recruitmentAgent)) {
			return recruitmentAgent.split(",");
		}
		return new String[] {};
	}


	public void setRecruitmentAgent(String[] recruitmentAgent) {
		StringBuilder sb = new StringBuilder();
		if (!BaseUtil.isObjNull(recruitmentAgent)) {
			for (String element : recruitmentAgent) {
				sb.append(element).append(",");
			}
		}
		this.recruitmentAgent = sb.toString();
	}


	public Integer getRaProfileId() {
		return raProfileId;
	}


	public void setRaProfileId(Integer raProfileId) {
		this.raProfileId = raProfileId;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public Date getInterviewDate() {
		return interviewDate;
	}


	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}


	public String getOfficerName() {
		return officerName;
	}


	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}


	public String getRaCmpnyRegNo() {
		return raCmpnyRegNo;
	}


	public void setRaCmpnyRegNo(String raCmpnyRegNo) {
		this.raCmpnyRegNo = raCmpnyRegNo;
	}


	public Date getClaimedDate() {
		return claimedDate;
	}


	public void setClaimedDate(Date claimedDate) {
		this.claimedDate = claimedDate;
	}

}
