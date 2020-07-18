package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateSerializer;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class WfwPayload implements Serializable {

	private static final long serialVersionUID = 6978507994624708111L;

	private String taskIds;

	private String refNo;

	private String recruitmentAgent;

	private Integer raProfileId;

	private String raCmpnyRegNo;

	private String modules;

	private String tranId;

	private String roles;

	private String taskAuthorId;

	private String taskAuthorName;

	private String isDisplay;

	private String queueInd;

	private String applStatusCode;

	private String pymntSts;

	private String paymentInd;

	private String isQuotaSec;

	private String country;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateFrom;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateTo;

	private String cmpnyRegNo;

	private String cmpnyName;

	private String sectorAgency;

	private Date intvwDate;

	private String printStatus;

	private String userType;

	private String groupAdminId;

	private String claimedDate;

	private String portalType;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date apprveDtTo;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date apprveDtFrm;

	private String invoiceNo;

	private String paymentType;


	public String getTaskIds() {
		return taskIds;
	}


	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getModules() {
		return modules;
	}


	public void setModules(String modules) {
		this.modules = modules;
	}


	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getTaskAuthorId() {
		return taskAuthorId;
	}


	public void setTaskAuthorId(String taskAuthorId) {
		this.taskAuthorId = taskAuthorId;
	}


	public Date getAppDateFrom() {
		return appDateFrom;
	}


	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}


	public Date getAppDateTo() {
		return appDateTo;
	}


	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}


	public String getCmpnyRegNo() {
		return cmpnyRegNo;
	}


	public void setCmpnyRegNo(String cmpnyRegNo) {
		this.cmpnyRegNo = cmpnyRegNo;
	}


	public String getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}


	public String getApplStatusCode() {
		return applStatusCode;
	}


	public void setApplStatusCode(String applStatusCode) {
		this.applStatusCode = applStatusCode;
	}


	public String getQueueInd() {
		return queueInd;
	}


	public void setQueueInd(String queueInd) {
		this.queueInd = queueInd;
	}


	public String getTaskAuthorName() {
		return taskAuthorName;
	}


	public void setTaskAuthorName(String taskAuthorName) {
		this.taskAuthorName = taskAuthorName;
	}


	public String getSectorAgency() {
		return sectorAgency;
	}


	public void setSectorAgency(String sectorAgency) {
		this.sectorAgency = sectorAgency;
	}


	public Date getIntvwDate() {
		return intvwDate;
	}


	public void setIntvwDate(Date intvwDate) {
		this.intvwDate = intvwDate;
	}


	public String getPrintStatus() {
		return printStatus;
	}


	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getGroupAdminId() {
		return groupAdminId;
	}


	public void setGroupAdminId(String groupAdminId) {
		this.groupAdminId = groupAdminId;
	}


	public String getTranId() {
		return tranId;
	}


	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	public String getCmpnyName() {
		return cmpnyName;
	}


	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}


	public String getPymntSts() {
		return pymntSts;
	}


	public void setPymntSts(String pymntSts) {
		this.pymntSts = pymntSts;
	}


	public String getPaymentInd() {
		return paymentInd;
	}


	public void setPaymentInd(String paymentInd) {
		this.paymentInd = paymentInd;
	}


	public String getRecruitmentAgent() {
		return recruitmentAgent;
	}


	public void setRecruitmentAgent(String recruitmentAgent) {
		this.recruitmentAgent = recruitmentAgent;
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


	public Date getApprveDtTo() {
		return apprveDtTo;
	}


	public void setApprveDtTo(Date apprveDtTo) {
		this.apprveDtTo = apprveDtTo;
	}


	public Date getApprveDtFrm() {
		return apprveDtFrm;
	}


	public void setApprveDtFrm(Date apprveDtFrm) {
		this.apprveDtFrm = apprveDtFrm;
	}


	public String getInvoiceNo() {
		return invoiceNo;
	}


	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public String getClaimedDate() {
		return claimedDate;
	}


	public void setClaimedDate(String claimedDate) {
		this.claimedDate = claimedDate;
	}


	public String getIsQuotaSec() {
		return isQuotaSec;
	}


	public void setIsQuotaSec(String isQuotaSec) {
		this.isQuotaSec = isQuotaSec;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPortalType() {
		return portalType;
	}


	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

}
