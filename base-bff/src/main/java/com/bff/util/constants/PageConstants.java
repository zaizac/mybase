/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util.constants;


import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class PageConstants {

	private PageConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String PAGE_API = "/api";

	public static final String PAGE_IDM = "/idm";

	public static final String PAGE_API_IDM = PAGE_API + PAGE_IDM;

	public static final String PAGE_API_SECURE = "/api/sec";

	public static final String PAGE_SRC = "/";

	public static final String PAGE_MAIN = "/main";

	public static final String PAGE_LOGIN = PAGE_API + "/login";

	public static final String PAGE_LOGOUT = PAGE_API + "/logout";

	public static final String PAGE_REGISTER = PAGE_API + "/register";

	public static final String PAGE_REF_STATIC = PAGE_API + "/references";

	public static final String PAGE_LOGIN_SUC = "/login-success";

	public static final String PAGE_LOGIN_ERR = "/login-error";

	// User Management
	public static final String PAGE_IDM_USR_LST = PAGE_API + "/users";

	public static final String PAGE_IDM_USR_UPD_LOGIN = PAGE_API + "/users/id";

	public static final String PAGE_IDM_USR_CRED = PAGE_API + "/idm/user-profile";

	public static final String PAGE_IDM_FRGT_PWORD = PAGE_API + "/password/reset";

	public static final String PAGE_IDM_USR_CHNG_PWORD = PAGE_API + "/password/change";

	public static final String PAGE_UPDATE_PROFILE = PAGE_API + "/updateProfile";

	public static final String PAGE_CMN_OTP = PAGE_API + "/otp";

	public static final String PAGE_CMN_CAPTCHA = PAGE_API + "/captcha";

	public static final String PAGE_CMN_QR = PAGE_API + "/qr";

	public static final String SERVICE_CHECK = PAGE_API + "/serviceCheck";

	public static final String DOCUMENTS = PAGE_API + "/references/documents";

	public static final String DOCUMENTS_DOWNLOAD = DOCUMENTS + "/download";

	public static final String LOGS = PAGE_API + "/logs";

	public static final String PAGE_INBOX = "/inbox";

	public static final String PAGE_WFW_REFERENCE = "/wfw/ref";

	public static final String PAGE_TYPE = "/type";

	public static final String PAGE_LEVEL = "/level";

	public static final String PAGE_STATUS = "/status";

	public static final String PAGE_ADDUPDATE = "/addUpdate";

	public static final String PAGE_IDM_MENU = PAGE_API_IDM + "/menu";

	public static final String PAGE_IDM_USER_GROUP = PAGE_API_IDM + "/userGroup";

	public static final String PAGE_IDM_USR_TYPE = PAGE_API_IDM + "/user-type";

	public static final String PAGE_IDM_USR_ROLE = PAGE_API_IDM + "/user-role";

	public static final String PAGE_IDM_ROLE_MENU = PAGE_API_IDM + "/role-menu";

	public static final String PAGE_IDM_USER_CONFIG = PAGE_API + "/idm/userConfig";

	public static final String PAGE_USER_GROUP_ROLE = PAGE_API_IDM + "/user-group-role";

	public static final String PAGE_IDM_OAUTH = PAGE_API_IDM + "/oauth";


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
