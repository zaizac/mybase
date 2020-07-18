/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.esb.util;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 3, 2016
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserProfile implements Serializable {
	
	private static final long serialVersionUID = -3245199650153350590L;

	private String status;
	
	private List<String> userRoleGroupCodeList;
	
	private Integer syncFlag;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getUserRoleGroupCodeList() {
		return userRoleGroupCodeList;
	}

	public void setUserRoleGroupCodeList(List<String> userRoleGroupCodeList) {
		this.userRoleGroupCodeList = userRoleGroupCodeList;
	}

	public Integer getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
	
}