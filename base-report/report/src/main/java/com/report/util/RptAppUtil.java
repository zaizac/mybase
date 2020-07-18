/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.report.util;


import com.report.sdk.constants.ReportConstants;


/**
 * @author Naem Othman
 * @since 05/03/2018
 */
public class RptAppUtil {

	public static String convertQuotaSPPA(String appNo) {
		if (appNo == null) {
			return null;
		}

		if (appNo.contains(".")) {
			appNo = appNo.replace(".", "*");
			appNo = appNo.replace(ReportConstants.DASH, "^");
			appNo = appNo.replace(ReportConstants.SLASH, ReportConstants.DASH);
			appNo = appNo.replace(" ", "_");
		} else {
			if (!appNo.contains("*")) {
				appNo = appNo.replace(ReportConstants.SLASH, ReportConstants.DASH);
			}
		}
		return appNo.toString().toUpperCase();
	}


	public static String revertQuotaSPPA(String appNo) {
		if (appNo == null) {
			return null;
		}

		if (appNo.contains("*")) {
			appNo = appNo.replace("*", ".");
			appNo = appNo.replace(ReportConstants.DASH, ReportConstants.SLASH);
			appNo = appNo.replace("^", ReportConstants.DASH);
			appNo = appNo.replace("_", " ");
		} else {
			if (!appNo.contains(".")) {
				appNo = appNo.replace(ReportConstants.DASH, ReportConstants.SLASH);
			}
		}
		return appNo.toString().toUpperCase();
	}

}
