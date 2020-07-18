/**
 * Copyright 2019. 
 */
package com.util.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The Class Report.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Report implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8536372586169834980L;

	/** The report type. */
	private String reportType;

	/** The report bytes. */
	private byte[] reportBytes;

	/** The file name. */
	private String fileName;

	/** The mime type. */
	private String mimeType;


	/**
	 * Gets the report type.
	 *
	 * @return the report type
	 */
	public String getReportType() {
		return reportType;
	}


	/**
	 * Sets the report type.
	 *
	 * @param reportType the new report type
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}


	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * Gets the mime type.
	 *
	 * @return the mime type
	 */
	public String getMimeType() {
		return mimeType;
	}


	/**
	 * Sets the mime type.
	 *
	 * @param mimeType the new mime type
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	/**
	 * Gets the report bytes.
	 *
	 * @return the report bytes
	 */
	public byte[] getReportBytes() {
		return reportBytes;
	}


	/**
	 * Sets the report bytes.
	 *
	 * @param reportBytes the new report bytes
	 */
	public void setReportBytes(byte[] reportBytes) {
		this.reportBytes = reportBytes;
	}

}
