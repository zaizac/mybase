/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config.audit;


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

	;

	private final String page;

	private final String action;


	AuditActionPolicy(final String page, final String action) {
		this.page = page;
		this.action = action;
	}


	public String page() {
		return page;
	}


	public String action() {
		return action;
	}

}
