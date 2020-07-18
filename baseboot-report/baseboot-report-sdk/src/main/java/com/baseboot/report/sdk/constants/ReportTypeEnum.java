/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum ReportTypeEnum {

	PDF("pdf", "application/pdf"), XLS("xls", "application/vnd.ms-excel"),;

	private final String type;

	private final String mimeType;


	private ReportTypeEnum(String type, String mimeType) {
		this.type = type;
		this.mimeType = mimeType;
	}


	public String getMimeType() {
		return mimeType;
	}


	public String getType() {
		return type;
	}

}
