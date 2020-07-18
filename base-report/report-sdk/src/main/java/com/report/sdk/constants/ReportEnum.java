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
			new String[] { "APC_APP_JOBCAT_SUB" }),

	EQM_PAYMENT_INVOICE(ReportConstants.PATH_REPORT_EPAY, "SPPA", "SPPA", null),

	EQM_SPPA_OFFICIAL_RCPT(ReportConstants.PATH_REPORT_EPAY, "SPPA_OFFICIAL_RCPT", null, null), // SPPA_OFFICIAL_RCPT
	EQM_KDN_APPROVAL(ReportConstants.PATH_REPORT_EQM, "KDN_APPROVAL", null, null),

	EQM_PROFORMA_INVOICE(ReportConstants.PATH_REPORT_EPAY, "PROFORMA_INVOICE", "PROFORMA_INVOICE", null),
	RPT_CONTRACT_EMPLOYMENT(ReportConstants.PATH_REPORT_EMB, "CONTRACT_OF_EMPLOYMENT", "CONTRACT_OF_EMPLOYMENT", null),
	EMB_ATTESTATION_LETTER(ReportConstants.PATH_REPORT_EMB, "Attestation_Letter", null, null),
	EMB_DEMAND_LETTER(ReportConstants.PATH_REPORT_EMB, "Demand_Letter", null, null),
	EMB_POWER_ATTORNEY(ReportConstants.PATH_REPORT_EMB, "Power_Attorney", null, null),
	EMB_EMPLOYMENT_CONTRACT(ReportConstants.PATH_REPORT_EMB, "Emp_Contract", null, null),

	// worker
	REPORT_WRKR_ASG_LIST(ReportConstants.PATH_REPORT_MC_ASG, "WRKR_ASG_SLIP", null, null),
	REPORT_WRKR_REG_FORM(ReportConstants.PATH_REPORT_MC_ASG, "WRKR_REG_FORM", null, null),
	
	
	//covid
	WHO_FORM(ReportConstants.PATH_REPORT_COVID, "WHO_FORM", "WHO_FORM", new String[] { "WHO_FORM_SUB01", "WHO_FORM_SUB02" , "WHO_FORM_SUB04"}),
	CNTACT_CASES(ReportConstants.PATH_REPORT_COVID, "CNTACT_CASES", "CNTACT_CASES", null),
	PUI_LIST(ReportConstants.PATH_REPORT_COVID, "PUI_LIST", "PUI_LIST", null),
	STATISTICS_TOTAL_CASES_BY_STATE(ReportConstants.PATH_REPORT_COVID, "STATISTICS_TOTAL_CASES_BY_STATE", "STATISTICS_TOTAL_CASES_BY_STATE", null),
	MOVE_HISTORY(ReportConstants.PATH_REPORT_COVID, "MOVEMENT_HISTORY", "MOVEMENT_HISTORY",null),

	
	;			
	
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
