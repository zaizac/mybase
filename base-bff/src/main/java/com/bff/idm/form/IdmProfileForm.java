/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;


import java.io.Serializable;

import com.util.BaseUtil;


/**
 * @author Suhada
 * @since 13/6/2018
 */
public class IdmProfileForm implements Serializable {

	private static final long serialVersionUID = 5359418269864371405L;

	private String email;

	private String userId;

	private String fullName;

	private String firstName;

	private String lastName;

	private String nationality;

	private String gender;

	private String captcha;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = BaseUtil.getStrUpperWithNull(fullName);
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = BaseUtil.getStrUpperWithNull(firstName);
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = BaseUtil.getStrUpperWithNull(lastName);
	}


	public String getCaptcha() {
		return captcha;
	}


	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
