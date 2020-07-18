/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;


/**
 * @author mary.jane
 *
 */
public class Logout {

	private String userId;

	private String authToken;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAuthToken() {
		return authToken;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
