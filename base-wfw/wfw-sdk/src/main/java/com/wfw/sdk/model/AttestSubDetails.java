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


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AttestSubDetails implements Serializable {

	private static final long serialVersionUID = 6997498555417869496L;

	private String quotaRefNo;

	private String companyName;

	private List<String> recruitmentAgent;

	private String recruitmentAgentStr;

	private String state;

	private List<String> companypic;

	private List<String> piccontact;

	private String sector;

	private String dateFrom;


	public String getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}


	public String getDateTo() {
		return dateTo;
	}


	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}


	private String dateTo;

	private String subSector;

	private String status;

	private String statusCd;

	private String applctnDtFrom;

	private String recAgentId;

	private String applctnDtTo;

	private String cmpnyRegNo;

	private String raCmpnyRegNo;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date applyDt;

	private int noQuotaApply;


	public String getQuotaRefNo() {
		return quotaRefNo;
	}


	public void setQuotaRefNo(String quotaRefNo) {
		this.quotaRefNo = quotaRefNo;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public Date getApplyDt() {
		return applyDt;
	}


	public void setApplyDt(Date applyDt) {
		this.applyDt = applyDt;
	}


	public int getNoQuotaApply() {
		return noQuotaApply;
	}


	public void setNoQuotaApply(int noQuotaApply) {
		this.noQuotaApply = noQuotaApply;
	}


	public List<String> getRecruitmentAgent() {
		return recruitmentAgent;
	}


	public void setRecruitmentAgent(List<String> recruitmentAgent) {
		this.recruitmentAgent = recruitmentAgent;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public List<String> getCompanypic() {
		return companypic;
	}


	public void setCompanypic(List<String> list) {
		companypic = list;
	}


	public List<String> getPiccontact() {
		return piccontact;
	}


	public void setPiccontact(List<String> list) {
		piccontact = list;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getSubSector() {
		return subSector;
	}


	public void setSubSector(String subSector) {
		this.subSector = subSector;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusCd() {
		return statusCd;
	}


	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	public String getApplctnDtFrom() {
		return applctnDtFrom;
	}


	public void setApplctnDtFrom(String applctnDtFrom) {
		this.applctnDtFrom = applctnDtFrom;
	}


	public String getApplctnDtTo() {
		return applctnDtTo;
	}


	public void setApplctnDtTo(String applctnDtTo) {
		this.applctnDtTo = applctnDtTo;
	}


	public String getCmpnyRegNo() {
		return cmpnyRegNo;
	}


	public void setCmpnyRegNo(String cmpnyRegNo) {
		this.cmpnyRegNo = cmpnyRegNo;
	}


	public String getRaCmpnyRegNo() {
		return raCmpnyRegNo;
	}


	public void setRaCmpnyRegNo(String raCmpnyRegNo) {
		this.raCmpnyRegNo = raCmpnyRegNo;
	}


	public String getRecruitmentAgentStr() {
		return recruitmentAgentStr;
	}


	public void setRecruitmentAgentStr(String recruitmentAgentStr) {
		this.recruitmentAgentStr = recruitmentAgentStr;
	}


	public String getRecAgentId() {
		return recAgentId;
	}


	public void setRecAgentId(String recAgentId) {
		this.recAgentId = recAgentId;
	}

}