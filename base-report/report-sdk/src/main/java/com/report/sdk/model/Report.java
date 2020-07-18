/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@SuppressWarnings("serial")
public class Report implements Serializable {

	private String reportType;

	private byte[] reportBytes;

	private String fileName;

	private String mimeType;


	public String getReportType() {
		return reportType;
	}


	public void setReportType(String reportType) {
		this.reportType = reportType;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getMimeType() {
		return mimeType;
	}


	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	public byte[] getReportBytes() {
		return reportBytes;
	}


	public void setReportBytes(byte[] reportBytes) {
		this.reportBytes = reportBytes;
	}

}
