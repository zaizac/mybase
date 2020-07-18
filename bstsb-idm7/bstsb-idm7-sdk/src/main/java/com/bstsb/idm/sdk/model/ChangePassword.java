/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ChangePassword implements Serializable {

	private static final long serialVersionUID = 3627919998320638180L;

	private String userId;

	private String currPword;

	private String newPword;

	private String repNewPword;

	private String recoveryMethod;

	private String branchId;

	private String message;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getCurrPword() {
		return currPword;
	}


	public void setCurrPword(String currPword) {
		this.currPword = currPword;
	}


	public String getNewPword() {
		return newPword;
	}


	public void setNewPword(String newPword) {
		this.newPword = newPword;
	}


	public String getRepNewPword() {
		return repNewPword;
	}


	public void setRepNewPword(String repNewPword) {
		this.repNewPword = repNewPword;
	}


	public String getRecoveryMethod() {
		return recoveryMethod;
	}


	public void setRecoveryMethod(String recoveryMethod) {
		this.recoveryMethod = recoveryMethod;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

}
