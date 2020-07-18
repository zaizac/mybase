
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


@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ReportTaskDetails implements Serializable {

	private static final long serialVersionUID = 6997498555417869496L;

	private String refNo;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date applDate;

	private String companyName;

	private int noQuotaApply;

	private List<String> recruitmentAgent;


	public ReportTaskDetails() {
	}


	public ReportTaskDetails(String refNo, Date applDate, String companyName, int noQuotaApply,
			String recruitmentAgent) {
		this.refNo = refNo;
		this.applDate = applDate;
		this.companyName = companyName;
		this.noQuotaApply = noQuotaApply;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
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

}