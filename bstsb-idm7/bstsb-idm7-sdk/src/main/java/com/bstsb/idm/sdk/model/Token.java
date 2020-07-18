/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class Token implements Serializable {

	private static final long serialVersionUID = 129382973082959032L;

	private String accessToken;

	private String tokenType;

	private String refreshToken;

	private String scope;

	private String expiresIn;

	private UserProfile user;

	private UserRole role;


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getTokenType() {
		return tokenType;
	}


	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public String getExpiresIn() {
		return expiresIn;
	}


	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}


	public UserProfile getUser() {
		return user;
	}


	public void setUser(UserProfile user) {
		this.user = user;
	}


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}

}
