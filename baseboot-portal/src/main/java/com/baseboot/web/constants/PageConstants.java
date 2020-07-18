/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.constants;


import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class PageConstants {

	public static final String PAGE_SRC = "/";

	public static final String PAGE_LOGIN = "/login";

	public static final String PAGE_MAIN = "/main";

	public static final String PAGE_LOGIN_SUC = "/login-success";

	public static final String PAGE_LOGIN_ERR = "/login-error";

	public static final String PAGE_LOGOUT = "/logout-process";

	public static final String PAGE_ERROR = "/error";

	public static final String PAGE_ERROR_403 = "/403";

	public static final String PAGE_HOME = "/home";

	public static final String PAGE_WELCOME = "/welcome";

	public static final String PAGE_DISCLAIMER = "/disclaimer";

	public static final String PAGE_FIND_OUT_MORE = PAGE_MAIN + "/findOutMore";

	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String PAGE_FAQ = "/faq";

	public static final String PAGE_CONTACT = "/contact";

	public static final String PAGE_COMPONENTS = "/components";

	public static final String PAGE_TEST_VIEW = "/testView";

	public static final String PAGE_SETTINGS = "/settings";

	public static final String PAGE_SRC_IDM = "/idm";

	// Maintenance Screens
	public static final String GET_USERGROUP_CODE = "/getMenu";

	public static final String PAGE_IDM_CREATE_USERGROUP = "createUserGroup";

	public static final String PAGE_IDM_UPDATE_USERGROUP = "updateUserGroup";

	public static final String PAGE_IDM_USR_ROLE = PAGE_SRC_IDM + "/user-role-assignment";

	public static final String PAGE_USR_ROLE_GROUP = PAGE_SRC_IDM + "/user-role-group";

	public static final String PAGE_USR_GROUP = PAGE_SRC_IDM + "/usergroup-maintenance";

	public static final String GET_USERGROUP = "/getUserGroup";

	public static final String PAGE_APP = "/application";

	public static final String PAGE_APP_LIST = PAGE_APP + "/list";

	public static final String PAGE_APP_INBOX = PAGE_APP + "/inbox";

	public static final String PAGE_IDM_USER_TYPE_MAINTENANCE = PAGE_SRC_IDM + "/userType-maintenance";

	public static final String PAGE_IDM_CREATE_USER_TYPE = "/createUserType";

	public static final String PAGE_IDM_UPDATE_USER_TYPE = "updateUserType";

	public static final String PAGE_IDM_ROLE_CREATE_NEW_USER_TYPE = "createUserType";

	public static final String GET_MAINTENANCE_USER_TYPE_CODE = "/getUserType/{userTypeCode}/{userTypeDesc}/{userTypeId}";

	public static final String PAGE_CMN_STATIC = "/static-list";

	public static final String PAGE_CMN_OTP = "/otp";

	public static final String PAGE_CMN_CAPTCHA = "/captcha";

	public static final String PAGE_CMN_REPORT = "/reports";

	public static final String MAIL_TEMPLATE = "/mail-template";

	public static final String PAGE_DASHBOARD = "/dashboard";

	public static final String RESET = "reset";

	public static final String CANCEL = "cancel";

	public static final String DOCUMENTS = "/documents";

	public static final String REPORT = "/reports";

	public static final String NAME_BLOCK = "<th:block th:text=\"${name}\"/>";

	public static final String LOGIN_BLOCK = "<td th:text=\"${loginName}\" />";

	public static final String PASSWORD_BLOCK = "<td th:text=\"${password}\" />";

	public static final String FWCMS_URL_BLOCK = "<a th:href=\"${fwcmsUrl}\" th:text=\"${fwcmsUrl}\"/>";

	public static final String OTP_BLOCK = "<td th:text=\"${otp}\"/>";

	public static final String OTP_VARIABLE = "__otp__";

	public static final String NAME_VARIABLE = "__name__";

	public static final String LOGIN_NAME_VARIABLE = "__loginName__";

	public static final String PASSWORD_VARIABLE = "__password__";

	public static final String FWCMS_URL_VARIABLE = "__fwcmsUrl__";

	// User Management
	public static final String PAGE_IDM_USR_LST = "/user-list";

	public static final String PAGE_IDM_USR_UPD_LOGIN = "/user-list/id";

	public static final String PAGE_IDM_FRGT_PWORD = "/forgotPassword";

	public static final String PAGE_IDM_USR_CHNG_PWORD = "/user/change/password";

	// Registration
	public static final String PAGE_REGISTER = "/register/tki";

	public static final String REGISTER_EMPLOYER = "/register/employer";

	public static final String TKI_BIO_DATA = "/worker-profile/TKI-Biodata";

	// Interview
	public static final String PAGE_INTERVIEW = "/interview";

	public static final String PAGE_INTERVIEW_HISTORY = "/history";

	public static final String PAGE_APPLY_INTERVIEW = "/apply";

	public static final String INTERVIEW_RESULT = "/interview/result";

	// Training
	public static final String PAGE_TKI_ATTENDANCE = "/training/attendance";

	public static final String TRAINING_ENROLLMENT = "/training/enrollment";

	public static final String TRAINING_RESULT = "/training/result";

	// Worker Profile
	public static final String PAGE_RA_WRKR_PROFILE_SEARCH = "/search-worker-profile";

	// Booking
	public static final String VIEW_MAID = "/booking/selection";

	public static final String SEARCH_MAID = "/search-maid";

	public static final String VIEW_MAID_DETAILS = "/view-maid-details";

	public static final String PAGE_SEARCH_EMPLOYER = "/tki/booking";

	// Pre Medical Report
	public static final String PAGE_TKI_PRE_MEDICAL = "/tki/premedical";

	// Job Order
	public static final String JOB_ORDER = "/tki/joborder";

	public static final String JOB_ORDER_CREATE = "/tki/joborder/create";

	public static final String APS_JOB_ORDER = "/aps/joborder";

	public static final String UPDATE_JOB_ORDER_SIP = "/tki/sip";

	public static final String JOB_ORDER_INFO = "/job-order-info";

	public static final String VIEW_JOB_ORDER = "/view-job-order";

	public static final String SEARCH_JOB_ORDER = "/search-job-order";

	// Aps viewRequested
	public static final String PAGE_VIEW_REQ = "/view-request";

	// PT Management
	public static final String FINANCIER = "/financier";

	public static final String SEARCH_PAYMENT_RELEASE = "/search-payment-release";

	public static final String PRINT_BILLING_SLIP = "/print-billing-slip";

	// Topup Wallet
	public static final String PAGE_TOPUP = "/manual/topup";

	public static final String PAGE_TOPUP_SUBMIT = PAGE_TOPUP + "/confirm";

	// Profiles
	public static final String PAGE_APS_PROFILE = "/profiles/aps";

	public static final String PAGE_APS_BRANCH = "/profiles/apsBranch";

	public static final String PAGE_BLK_PROFILE = "/profiles/blk";

	public static final String PAGE_BLK_BRANCH = "/profiles/blkBranch";

	public static final String PAGE_PT_PROFILE = "/profiles/pt";

	public static final String PAGE_PT_BRANCH = "/profiles/ptBranch";

	public static final String PAGE_UTC_PROFILE = "/profiles/utc";

	public static final String PAGE_UTC_BRANCH = "/profiles/utcBranch";

	public static final String PAGE_MC_PROFILE = "/profiles/mc";

	public static final String PAGE_EMP_PROFILE = "/profiles/employer";

	public static final String PLC_TKI_MOD = "/profiles/plctki";

	public static final String PAGE_TKI_PROFILE = "/profiles/tki";

	public static final String PAGE_RA_WRKR_PROFILE = PAGE_TKI_PROFILE + "/biodata";

	// BLK Schedule
	public static final String PAGE_BLK_SCHEDULE = "/interview/schedule";

	public static final String PAGE_BLK_SCHEDULE_ID = PAGE_BLK_SCHEDULE + "/new";

	public static final String CITIES_STATE_CODE = "/getCitiesByStateCode";

	// VDR
	public static final String PAGE_VDR = "/tki/vdr";

	public static final String VDR_PAGINATED = "/paginated";

	public static final String VDR_UPDATE = "/update";

	// Arrival
	public static final String PAGE_ARRIVAL = "/tki/arrival";

	public static final String PAGE_TKI_BMS = "/tki/biomedical";

	public static final String PAGE_TKI_FOMEMA = "/tki/fomema";

	public static final String PAGE_TKI_PLKS = "/tki/plks";

	public static final String PAGE_BILING = "/billing";

	// Job Opening
	public static final String PAGE_JOB_OPENING = "/opening";

	public static final String PAGE_JOB_OPENING_LST = PAGE_JOB_OPENING + "/list";

	public static final String PAGE_JOB_OPENING_DTL = PAGE_JOB_OPENING + "/details";

	// Job Seeker Registration
	public static final String PAGE_JOB_SEEKER_SIGNUP = "/jobSeekerSignUp";

	public static final String PAGE_JOB_SEEKER_DETAILS = "/js-appln-details";

	// Job Seeker Profile
	public static final String PAGE_JOB_SEEKER_PROFILE = "/jobSeekerProfile";

	public static final String PAGE_JOB_SEEKER_LIST = "/emp-jobSeeker-list";

	public static final String PAGE_JOB_SEEKER_PROFILE_UPDATE_CONTACT = PAGE_JOB_SEEKER_PROFILE
			+ "/updateContactDetails";

	public static final String PAGE_JOB_SEEKER_PROFILE_VIEW_PASSPORT = PAGE_JOB_SEEKER_PROFILE + "/passportView";

	// Job Seeker View Company
	public static final String PAGE_JOB_SEEKER = "/job-seeker";

	public static final String PAGE_JOB_SEEKER_VIEW_COMPNY_LIST = PAGE_JOB_SEEKER + "/viewCompanyList";

	public static final String PAGE_JOB_SEEKER_VIEW_COMPNY_DTL = PAGE_JOB_SEEKER + "/viewCompanyDetails";

	public static final String PAGE_JOB_SEEKER_VIEW_COMPNY_LIST_SEARCH = "search";

	public static final String PAGE_JOB_SEEKER_VIEW_COMPNY_LIST_RESET = "reset";

	// Job Seeker Job Offer
	public static final String PAGE_JOB_SEEKER_JOB_OFFER = "/jobSeekerJobOffer";

	public static final String PAGE_JS_OFFER_LIST = "/jobSeekerOfferList";

	public static final String PROFILE_VIEWER_NOTIFICATION = "/profile-viewer-list";

	public static final String PAGE_IDM_ACTIVATE_USER = "/activate-user";

	public static final String PAGE_JOB_SEEKER_VIEW_INTERVIEW_LIST = PAGE_JOB_SEEKER + "/viewInterviewList";

	public static final String PAGE_JOB_SEEKER_INTERVIEW_DETAILS = "/jsInterviewInvitationDetails";


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
