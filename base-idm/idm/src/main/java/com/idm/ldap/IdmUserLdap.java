package com.idm.ldap;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class IdmUserLdap implements Serializable {

	private static final long serialVersionUID = -3005146779975144113L;

	private String userName;

	private String cn;

	private String sn;

	private String userPassword;

	private String email;


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCn() {
		return cn;
	}


	public void setCn(String cn) {
		this.cn = cn;
	}


	public String getSn() {
		return sn;
	}


	public void setSn(String sn) {
		this.sn = sn;
	}

}