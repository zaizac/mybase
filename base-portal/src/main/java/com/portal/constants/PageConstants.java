/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.constants;


import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class PageConstants {

	private PageConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String PAGE_SRC = "/";

	public static final String PAGE_MAIN = "/main";

	public static final String PAGE_LOGIN = "/login";

	public static final String PAGE_LOGIN_SUC = "/login-success";

	public static final String PAGE_LOGIN_ERR = "/login-error";

	public static final String PAGE_LOGOUT = "/logout-process";

	public static final String PAGE_ERROR = "/error";

	public static final String PAGE_ERROR_403 = "/403";

	public static final String PAGE_HOME = "/home";

	public static final String PAGE_DASHBOARD = "/dashboard";

	public static final String PAGE_WELCOME = "/welcome";

	public static final String PAGE_POLICY = "/policy";

	public static final String PAGE_CONTACT = "/contact-us";

	public static final String PAGE_FAQ = "/faq";

	public static final String PAGE_DOWNLOAD = "/download/forms";

	// User Management
	public static final String PAGE_IDM_USR_LST = "/user-list";

	public static final String PAGE_IDM_USR_UPD_LOGIN = "/user-list/user/id";

	public static final String PAGE_IDM_FRGT_PWORD = "/password/reset";

	public static final String PAGE_IDM_USR_CHNG_PWORD = "/user/password/change";

	public static final String PAGE_UPDATE_PROFILE = "/updateProfile";

	public static final String PAGE_COMPONENTS = "/components/{item}";

	public static final String PAGE_CMN_OTP = "/otp";

	public static final String PAGE_CMN_CAPTCHA = "/captcha";

	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String PAGE_CMN_STATIC = "/static-list";

	public static final String DOCUMENTS = "/documents";

	public static final String PAGE_PATIENT = "/patient";

	public static final String INQUIRY = "/inquiry";

	public static final String PATIENT_MNGMNT = "/patient/form";

	public static final String PAGE_TEST_RESULT = "/patient/result";

	public static final String PAGE_LAB_RESULT = "/labResult";

	public static final String PAGE_TEST_RESULT_UPDATE = "/patient/result/update";

	public static final String PAGE_REPORT = "/report";

	public static final String PAGE_REPORT_BY_STATE = PAGE_REPORT + "/state";

	public static final String PAGE_REPORT_BY_STATE_TESTCENTER = PAGE_REPORT + "/statetestcenter";

	public static final String PAGE_REPORT_BY_TESTCENTER = PAGE_REPORT + "/testcenter";

	public static final String PAGE_PATIANT_INVESTIGATION = PAGE_PATIENT + "/movement";

	public static final String FILE_DOWNLOAD = "/fileDownload";

	public static final String PAGE_TRANSFER = PAGE_PATIENT + "/transfer";

	public static final String PAGE_UPLOAD_CASE = "/bulk/case";
	
	public static final String PAGE_UPDATE = PAGE_TRANSFER + "/update";
	
	public static final String PAGE_TRANSFER_BY_BULK = PAGE_PATIENT + "/transferByBulk";
	
	public static final String PAGE_REPORT_COVID = PAGE_REPORT + "/rptCovid";
	
	public static final String PAGE_LAB_RESULT_LIST = "/labResultLst";

	

	public static String getRedirectUrl(String url) {
		return getRedirectUrl(url, null);
	}


	public static String getRedirectUrl(String url, String pathVar) {
		String newURL = "redirect:" + url;
		if (!BaseUtil.isObjNull(pathVar)) {
			newURL = newURL + "/" + pathVar;
		}
		return newURL;
	}

}
