/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config.audit;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public enum AuditActionPolicy {

	LOGIN("LOGIN", "Logged In"),
	LOGIN_FAILED("LOGIN", "Logged In Failed"),
	LOGOUT("LOGIN", "Logged out"),
	FORCE_LOGOUT("LOGIN", "Force/Session Logged out"),
	FORGOT_PASSWORD("FORGOT PASSWORD", "Reset Password"),
	CHANGE_PWORD("CHANGE PASSWORD", "Change Password"),
	CHANGE_PWORD_FT("CHANGE PASSWORD", "First Time Password"),

	MANAGE_USERS("MANAGE USERS", "View Manage Users"),
	CREATE_USER("USER PROFILE", "Create User Profile"),
	UPDATE_USER("USER PROFILE", "Update User Profile"),
	UPDATE_PROF("USER PROFILE", "Update Profile"),
	RESET_PWORD("USER PROFILE", "Reset Password"),
	ACTIVATE_USER("USER PROFILE", "Activate User"),
	DEACTIVATE_USER("USER PROFILE", "Dectivate User"),
	RESEND_CRED("USER PROFILE", "Resend User Credentials"),
	
	UPDATE_JS_PASSPORT("JOB SEEKER PROFILE", "Update Job Seeker Passport"),
	CREATE_JS_KIDS("JOB SEEKER PROFILE", "Create Job Seeker Kids"),
	CREATE_JS_SPOUSE("JOB SEEKER PROFILE", "Create Job Seeker Spouse"),
	CREATE_JS_NEXTOFKIN("JOB SEEKER PROFILE", "Create Job Seeker Next Of Kin"),
	CREATE_JS_EDUCATION("JOB SEEKER PROFILE", "Create Job Seeker Education"),
	CREATE_JS_JOBINTERESTED("JOB SEEKER PROFILE", "Create Job Seeker Job Interested"),
	CREATE_JS_EXPERIENCE("JOB SEEKER PROFILE", "Create Job Seeker Experience"),

	;

	private final String page;

	private final String action;


	AuditActionPolicy(final String page, final String action) {
		this.page = page;
		this.action = action;
	}


	public String page() {
		return this.page;
	}


	public String action() {
		return this.action;
	}

}
