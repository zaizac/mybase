/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.form;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateSerializer;


/**
 * @author michelle.angela
 *
 */
@JsonInclude(Include.NON_NULL)
public class Inbox implements Serializable {

	private static final long serialVersionUID = -5027600926380914688L;

	private String taskIds;

	private String refNo;

	private String taskAuthorId;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateFrom;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date appDateTo;

	private String companyName;

	private String cmpnyRegNo;

	private String country;


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


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

}
