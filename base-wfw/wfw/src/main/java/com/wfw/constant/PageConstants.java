/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.constant;


import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
public final class PageConstants {

	private PageConstants() {
		throw new IllegalStateException("PageConstants Utility class");
	}


	public static final String PAGE_REDIRECT = "redirect:";

	public static final String PAGE_ERROR_403 = "/403";

	public static final String PAGE_SRC_MAINTENACE = "/maintenance";

	public static final String PAGE_SRC_TASK = "/task";

	public static final String PAGE_URL_HOME = "/home";

	public static final String PAGE_URL_WF_LEVEL_LIST = PAGE_SRC_MAINTENACE + "/level-list";

	public static final String PAGE_URL_WF_STATUS_LIST = PAGE_SRC_MAINTENACE + "/status-list";

	public static final String PAGE_URL_WF_TYPE_LIST = PAGE_SRC_MAINTENACE + "/type-list";

	public static final String PAGE_URL_WF_CONFIG = PAGE_SRC_MAINTENACE + "/wf-config";

	public static final String PAGE_URL_WF_CONFIG_EDIT = PAGE_SRC_MAINTENACE + "/wf-config-edit";

	public static final String PAGE_URL_USER_LIST = PAGE_SRC_MAINTENACE + "/user-list";

	public static final String PAGE_URL_USER_EDIT = PAGE_SRC_MAINTENACE + "/user-edit";

	public static final String PAGE_URL_INBOX_LIST = PAGE_SRC_TASK + "/inbox-list";

	public static final String PAGE_URL_TASK_LIST = PAGE_SRC_TASK + "/task-list";

	public static final String PAGE_URL_TASK_CREATE = PAGE_SRC_TASK + "/task-create";

	public static final String PAGE_URL_TASK_DETAILS = PAGE_SRC_TASK + "/task-details";

	public static final String PAGE_URL_TASK_DETAILS_EDIT = PAGE_SRC_TASK + "/task-details-edit";

	public static final String PAGE_URL_INBOX_EDIT = PAGE_SRC_TASK + "/inbox-edit";

	public static final String PAGE_URL_TASK_HIST_LIST = PAGE_SRC_TASK + "/task-history-list";

	public static final String PAGE_URL_TASK_QUEUE = PAGE_SRC_TASK + "/task-queue";

	public static final String PAGE_URL_TASK_MY_INBOX = PAGE_SRC_TASK + "/task-my-inbox";

	public static final String PAGE_URL_TASK_RELEASE = PAGE_SRC_TASK + "/task-release";

	public static final String PAGE_URL_APPLICATION = PAGE_SRC_TASK + "/application";

	public static final String PAGE_URL_APPLICATION_DETAILS = PAGE_SRC_TASK + "/application-details";

	public static final String PAGE_SCR_ADMIN = "/admin";

	public static final String PAGE_WF_TYPE_DETAILS = PAGE_SCR_ADMIN + "/wf-type-details";

	public static final String PAGE_403 = "/403";

	public static final String PAGE_404 = "/404";

	public static final String PAGE_SRC = "/";

	public static final String PAGE_LOGIN = "/login";

	public static final String PAGE_LOGIN_SUC = "/login-success";

	public static final String PAGE_LOGIN_ERR = "/login-error";

	public static final String PAGE_LOGOUT = "/logout-process";

	public static final String PAGE_ABOUT = "/about-us";

	public static final String PAGE_CONTACT = "/contact-us";

	public static final String PAGE_SESS_TIMEOUT = "/timeout";

	public static final String PAGE_ERROR = "/error";

	public static final String PAGE_IDM_USR_CHNG_PWORD = "/user/change/password";

	public static final String PAGE_CONST_LEVEL_LIST = "level_list";

	public static final String PAGE_CONST_STATUS_LIST = "status_list";

	public static final String PAGE_CONST_TYPE_LIST = "type_list";

	public static final String PAGE_CONST_WF_CONFIG = "wf_config";

	public static final String PAGE_CONST_WF_CONFIG_EDIT = "wf_config_edit";

	public static final String PAGE_CONST_USER_LIST = "user_list";

	public static final String PAGE_CONST_USER_EDIT = "user_edit";

	public static final String PAGE_CONST_INBOX_LIST = "inbox_list";

	public static final String PAGE_CONST_TASK_LIST = "task_list";

	public static final String PAGE_CONST_TASK_CREATE = "task_create";

	public static final String PAGE_CONST_TASK_QUEUE = "task_queue";

	public static final String PAGE_CONST_TASK_MY_INBOX = "task_my_inbox";

	public static final String PAGE_CONST_TASK_RELEASE = "task_release";

	public static final String PAGE_CONST_TASK_DETAILS = "task_details";

	public static final String PAGE_CONST_TASK_DETAILS_EDIT = "task_details_edit";

	public static final String PAGE_CONST_INBOX_EDIT = "inbox_edit";

	public static final String PAGE_CONST_TASK_HIST_LIST = "task_history";

	public static final String PAGE_CONST_APPLICATION = "application";

	public static final String PAGE_CONST_APPLICATION_DETAILS = "application_details";

	public static final String DATA_LIST_OBJ = "dataListObj";

	public static final String DATA_OBJ = "dataObj";

	public static final String BUTTON_LIST = "btnList";

	public static final String PAGE_MAINTENANCE_REFERENCE = PAGE_SRC_MAINTENACE + "/reference";

	// New
	public static final String PAGE_SRC_TASK_LIST = "/task-list";

	public static final String PAGE_URL_TASK_MASTER = "/task-master";

	public static final String PAGE_URL_TASK_INBOX = "/task-inbox";

	public static final String PAGE_URL_TASK_POOL = "/task-pool";

	public static final String PAGE_SRC_TASK_DETAILS = "/task-details";

	public static final String PAGE_URL_CONFIG = "config";

	public static final String PAGE_URL_TYPE = "type";

	public static final String PAGE_URL_LEVEL = "level";

	public static final String PAGE_URL_STATUS = "status";

	public static final String REFERENCE = "reference";

	public static final String PAGE_CONST_TASK_MASTER = "task_master";

	public static final String PAGE_CONST_TASK_INBOX = "task_inbox";

	public static final String PAGE_CONST_TASK_POOL = "task_pool";

	public static final String PAGE_CONST_TASK_DTLS = "task_dtls";

	public static final String PAGE_CONST_TASK_HIST = "task_dtls_hist";

	public static final String ACTION = "action";


	public static String getRedirectUrl(String url) {
		return getRedirectUrl(url, null);
	}


	public static String getRedirectUrl(String url, String pathVar) {
		String newURL = PAGE_REDIRECT + url;
		if (!BaseUtil.isObjNull(pathVar)) {
			newURL = newURL + "/" + pathVar;
		}
		return newURL;
	}
}