package com.wfw.sdk.model;


import java.sql.Timestamp;
import java.util.Date;

import com.util.BaseUtil;
import com.wfw.sdk.util.CmnUtil;


/**
 * WfwInboxHist entity. @author Kamruzzaman
 */
@Deprecated
public class InboxHist implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	private String histId;

	private String taskId;

	private String refNo;

	private String applUserId;

	private String applUserName;

	private Date applDate;

	private String applStsId;

	private String applStsCode;

	private String applStsCodePrev;

	private String companyName;

	private String companyRegNo;

	private String recruitmentAgent;

	private String country;

	private String sector;

	private String sectorAgency;

	private String applRemark;

	private int noQuotaApply;

	private int noQuotaApprove;

	private Date approveDate;

	private Date expiryDate;

	private Date interviewDate;

	private int attemptCnt;

	private String moduleId;

	private String tranId;

	private String branchId;

	private String levelId;

	private String statusId;

	private String lastStatusId;

	private String lastStatusDesc;

	private String officerId;

	private String officerName;

	private String isDisplay;

	private String printStatus;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;

	private String levelDescription;

	private String statusDescription;


	public InboxHist() {
		// empty constructor
	}


	public String getHistId() {
		return histId;
	}


	public void setHistId(String histId) {
		this.histId = histId;
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


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public String getLevelDescription() {
		return levelDescription;
	}


	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}


	public String getOfficerName() {
		return officerName;
	}


	public void setOfficerName(String officerName) {
		this.officerName = officerName;
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


	public int getAttemptCnt() {
		return attemptCnt;
	}


	public void setAttemptCnt(int attemptCnt) {
		this.attemptCnt = attemptCnt;
	}


	public int getNoQuotaApply() {
		return noQuotaApply;
	}


	public void setNoQuotaApply(int noQuotaApply) {
		this.noQuotaApply = noQuotaApply;
	}


	public int getNoQuotaApprove() {
		return noQuotaApprove;
	}


	public void setNoQuotaApprove(int noQuotaApprove) {
		this.noQuotaApprove = noQuotaApprove;
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
		return new String[0];
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


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getApplStsCodePrev() {
		return applStsCodePrev;
	}


	public void setApplStsCodePrev(String applStsCodePrev) {
		this.applStsCodePrev = applStsCodePrev;
	}


	public Date getInterviewDate() {
		return interviewDate;
	}


	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}


	public String getLastStatusDesc() {
		return lastStatusDesc;
	}


	public void setLastStatusDesc(String lastStatusDesc) {
		this.lastStatusDesc = lastStatusDesc;
	}


	public String getPrintStatus() {
		return printStatus;
	}


	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

}