package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateSerializer;


@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TaskDetails implements Serializable {

	private static final long serialVersionUID = 6997498555417869496L;

	private String taskId;

	private String refNo;

	private String applUserId;

	private String applName;

	private boolean blinkFlag;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date applDate;

	private String applStatus;

	private String applStsCode;

	private String companyName;

	private String sector;

	private String sectorAgency;

	private String remarks;

	private int noOfWorkers;

	private String officerName;

	private String officerId;

	private String raCmpnyRegNo;// yell

	private String statusDescription; // for pagination

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date approveDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date expiryDate;

	private int attemptCount;

	private String transId;

	private String transIdDesc;

	private String branchCode;

	private String wfStatus;

	private String moduleId;

	private String isDisplay;

	private List<TaskStatus> statuses; // all workflow status in current stage

	private ArrayList<TaskRemark> remarkList;

	private String actionBy;

	private String actionByFullName;

	private String cmpnyRegNo;

	private String country;

	private List<String> recruitmentAgent;

	private String taskClaimBy;

	private Date interviewDate;

	private String queueInd;

	private String employerName;

	private Integer raProfileId;

	private Date claimedDate;

	private String levelId;

	private String updateId;

	private Date updateDt;


	public TaskDetails() {
	}


	public TaskDetails(String taskId, String refNo, Date applDate, String companyName, String sector, String moduleId,
			String applStsCode, String actionBy) {
		this.taskId = taskId;
		this.refNo = refNo;
		this.applDate = applDate;
		this.companyName = companyName;
		this.sector = sector;
		this.moduleId = moduleId;
		this.applStsCode = applStsCode;
		this.actionBy = actionBy;
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


	public String getApplName() {
		return applName;
	}


	public void setApplName(String applName) {
		this.applName = applName;
	}


	public List<TaskStatus> getStatuses() {
		return statuses;
	}


	public void setStatuses(List<TaskStatus> statuses) {
		this.statuses = statuses;
	}


	public String getApplStatus() {
		return applStatus;
	}


	public void setApplStatus(String applStatus) {
		this.applStatus = applStatus;
	}


	public Date getApplDate() {
		return applDate;
	}


	public void setApplDate(Date applDate) {
		this.applDate = applDate;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getTransId() {
		return transId;
	}


	public void setTransId(String transId) {
		this.transId = transId;
	}


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public String getWfStatus() {
		return wfStatus;
	}


	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getSectorAgency() {
		return sectorAgency;
	}


	public void setSectorAgency(String sectorAgency) {
		this.sectorAgency = sectorAgency;
	}


	public String getModuleId() {
		return moduleId;
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public int getAttemptCount() {
		return attemptCount;
	}


	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
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


	public String getActionBy() {
		return actionBy;
	}


	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}


	public String getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}


	public String getCmpnyRegNo() {
		return cmpnyRegNo;
	}


	public void setCmpnyRegNo(String cmpnyRegNo) {
		this.cmpnyRegNo = cmpnyRegNo;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public List<String> getRecruitmentAgent() {
		return recruitmentAgent;
	}


	public void setRecruitmentAgent(List<String> recruitmentAgent) {
		this.recruitmentAgent = recruitmentAgent;
	}


	public String getApplStsCode() {
		return applStsCode;
	}


	public void setApplStsCode(String applStsCode) {
		this.applStsCode = applStsCode;
	}


	public String getTaskClaimBy() {
		return taskClaimBy;
	}


	public void setTaskClaimBy(String taskClaimBy) {
		this.taskClaimBy = taskClaimBy;
	}


	public List<TaskRemark> getRemarkList() {
		return remarkList;
	}


	public void setRemarkList(List<TaskRemark> remarkList) {
		this.remarkList = (ArrayList<TaskRemark>) remarkList;
	}


	public Date getInterviewDate() {
		return interviewDate;
	}


	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}


	public String getActionByFullName() {
		return actionByFullName;
	}


	public void setActionByFullName(String actionByFullName) {
		this.actionByFullName = actionByFullName;
	}


	public String getQueueInd() {
		return queueInd;
	}


	public void setQueueInd(String queueInd) {
		this.queueInd = queueInd;
	}


	public boolean isBlinkFlag() {
		return blinkFlag;
	}


	public void setBlinkFlag(boolean blinkFlag) {
		this.blinkFlag = blinkFlag;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Date getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}


	public String getOfficerName() {
		return officerName;
	}


	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}


	public String getOfficerId() {
		return officerId;
	}


	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}


	public String getEmployerName() {
		return employerName;
	}


	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}


	public Integer getRaProfileId() {
		return raProfileId;
	}


	public void setRaProfileId(Integer raProfileId) {
		this.raProfileId = raProfileId;
	}


	public String getRaCmpnyRegNo() {
		return raCmpnyRegNo;
	}


	public void setRaCmpnyRegNo(String raCmpnyRegNo) {
		this.raCmpnyRegNo = raCmpnyRegNo;
	}


	public String getLevelId() {
		return levelId;
	}


	public Date getClaimedDate() {
		return claimedDate;
	}


	public void setClaimedDate(Date claimedDate) {
		this.claimedDate = claimedDate;
	}


	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public String getTransIdDesc() {
		return transIdDesc;
	}


	public void setTransIdDesc(String transIdDesc) {
		this.transIdDesc = transIdDesc;
	}

}
