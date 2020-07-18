/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class Fcm.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Fcm implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7965438783905532833L;

	/** The user id. */
	private String userId;

	/** The token. */
	private String token;


	/**
	 * Instantiates a new fcm.
	 *
	 * @param userId
	 *             the user id
	 * @param token
	 *             the token
	 */
	public Fcm(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}


	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *             the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}


	/**
	 * Sets the token.
	 *
	 * @param token
	 *             the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}
}