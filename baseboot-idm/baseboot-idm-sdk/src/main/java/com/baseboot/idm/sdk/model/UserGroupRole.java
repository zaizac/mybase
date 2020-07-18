/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;

/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserGroupRole implements Serializable {

	private static final long serialVersionUID = 3526094662318342264L;

	private int id;

	private String userRoleGroupCode;

	private String roleCode;


	public int getId() {
		return id;
	}


	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}


	public String getRoleCode() {
		return roleCode;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
