/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.report.sdk.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Hafidz Malik
 * @since Oct 16, 2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ReportSyncFussion implements Serializable {

	private static final long serialVersionUID = 8825358944813573683L;

	private String reportName;
	
	private String reportType;
	
	private String Param;
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getParam() {
		return Param;
	}

	public void setParam(String param) {
		Param = param;
	}

	
}
