/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since 11/12/2014
 */
public class ReportConstants {

	private ReportConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String KEY_REAL_PATH = "RPT_REAL_PATH";

	public static final String KEY_GENERATED_BY = "GENERATED_BY";

	public static final String API_VERSION = "/api/v1";

	public static final String REPORT_URL = API_VERSION + "/reports";

	public static final String SLASH = "/";

	public static final String PATH_REPORT = "report/";

	public static final String REPORT_PDF = "PDF";

	public static final String REPORT_EXCEL = "EXCEL";

	public static final String REPORT_DATA = "DATA";

	public static final String REPORT_SUCCESS = "Success";

	public static final String RPT_IMG_PATH = "/images";

	public static final String RPT_IMG_LOGO_PASS = "/passed.png";

	public static final String RPT_IMG_LOGO_FAIL = "/failed.png";

	public static final String RPT_IMG_LOGO = "/logo-orange.png";

	public static final String DAY = "DAY";

	public static final String MONTH = "MONTH";

	public static final String YEAR = "YEAR";


}
