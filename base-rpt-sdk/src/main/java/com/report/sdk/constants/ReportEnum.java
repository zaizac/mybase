/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum ReportEnum {

	// temporary
	EMB_APC_APP_JOBCAT_REPORT(ReportConstants.PATH_REPORT, "APC_APP_JOBCAT", null,
			new String[] { "APC_APP_JOBCAT_SUB" });

	private final String path;

	private final String fnamePdf;

	private final String fnameXls;

	private final String[] subReports;


	private ReportEnum(String path, String fnamePdf, String fnameXls, String[] subReports) {
		this.path = path;
		this.fnamePdf = fnamePdf;
		this.fnameXls = fnameXls;
		this.subReports = subReports;
	}


	public String getPath() {
		return path;
	}


	public String getFnamePdf() {
		return fnamePdf;
	}


	public String getFnameXls() {
		return fnameXls;
	}


	public String[] getSubReports() {
		return subReports;
	}

}
