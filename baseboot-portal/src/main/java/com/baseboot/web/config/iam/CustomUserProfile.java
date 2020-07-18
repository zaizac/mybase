/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config.iam;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class CustomUserProfile implements Serializable {

	private static final long serialVersionUID = -5109350055022006567L;

	private Integer id;

	private String regNo;

	private String name;

	private String status;

	private CustomUserBranch branch;


	public CustomUserProfile() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRegNo() {
		return regNo;
	}


	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public CustomUserBranch getBranch() {
		return branch;
	}


	public void setBranch(CustomUserBranch branch) {
		this.branch = branch;
	}

}