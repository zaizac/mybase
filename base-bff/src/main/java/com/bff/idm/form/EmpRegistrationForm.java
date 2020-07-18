/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;

import java.io.Serializable;
import java.util.List;

import com.bff.cmn.form.DocUpload;
import com.bff.cmn.form.FileUpload;

/**
 * @author Mubarak
 * @since Feb 12, 2019
 */
public class EmpRegistrationForm implements Serializable {

	private static final long serialVersionUID = 6719857272007621024L;

	private Integer regId;
	private String cmpnyType;
	private String cmpnyRegNo;
	private String cmpnyNm;
	private String applcntNm;
	private String applcntId;
	private String applcntMobile;
	private String otpEmail;
	private String phoneNo;
	private String faxNo;
	private String claimRelease;
	private String remarks;
	private String captcha;
	private String captchaImage;
	private boolean checkBox;
	private String regAppId;
	private String docRefNo;
	private List<FileUpload> fileUpload;
	private String cmpnyAdd1;
	private String cmpnyAdd2;
	private String cmpnyAdd3;
	private String cmpnyAdd4;
	private String cmpnyAdd5;
	private String city;
	private String state;
	private String postcode;
	private List<DocUpload> docUploadList;
	private String ownerAdd1;
	private String ownerAdd2;
	private String ownerAdd3;
	private String ownerAdd4;
	private String ownerAdd5;
	private String country;
	private String ownerCity;
	private String ownerCountry;
	private String ownerPostcode;

	public Integer getRegId() {
		return regId;
	}

	public void setRegId(Integer regId) {
		this.regId = regId;
	}

	public String getCmpnyType() {
		return cmpnyType;
	}

	public void setCmpnyType(String cmpnyType) {
		this.cmpnyType = cmpnyType;
	}

	public String getCmpnyRegNo() {
		return cmpnyRegNo;
	}

	public void setCmpnyRegNo(String cmpnyRegNo) {
		this.cmpnyRegNo = cmpnyRegNo;
	}

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getApplcntNm() {
		return applcntNm;
	}

	public void setApplcntNm(String applcntNm) {
		this.applcntNm = applcntNm;
	}

	public String getApplcntId() {
		return applcntId;
	}

	public void setApplcntId(String applcntId) {
		this.applcntId = applcntId;
	}

	public String getApplcntMobile() {
		return applcntMobile;
	}

	public void setApplcntMobile(String applcntMobile) {
		this.applcntMobile = applcntMobile;
	}

	public String getOtpEmail() {
		return otpEmail;
	}

	public void setOtpEmail(String otpEmail) {
		this.otpEmail = otpEmail;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getClaimRelease() {
		return claimRelease;
	}

	public void setClaimRelease(String claimRelease) {
		this.claimRelease = claimRelease;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptchaImage() {
		return captchaImage;
	}

	public void setCaptchaImage(String captchaImage) {
		this.captchaImage = captchaImage;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getRegAppId() {
		return regAppId;
	}

	public void setRegAppId(String regAppId) {
		this.regAppId = regAppId;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public List<FileUpload> getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(List<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getCmpnyAdd1() {
		return cmpnyAdd1;
	}

	public void setCmpnyAdd1(String cmpnyAdd1) {
		this.cmpnyAdd1 = cmpnyAdd1;
	}

	public String getCmpnyAdd2() {
		return cmpnyAdd2;
	}

	public void setCmpnyAdd2(String cmpnyAdd2) {
		this.cmpnyAdd2 = cmpnyAdd2;
	}

	public String getCmpnyAdd3() {
		return cmpnyAdd3;
	}

	public void setCmpnyAdd3(String cmpnyAdd3) {
		this.cmpnyAdd3 = cmpnyAdd3;
	}

	public String getCmpnyAdd4() {
		return cmpnyAdd4;
	}

	public void setCmpnyAdd4(String cmpnyAdd4) {
		this.cmpnyAdd4 = cmpnyAdd4;
	}

	public String getCmpnyAdd5() {
		return cmpnyAdd5;
	}

	public void setCmpnyAdd5(String cmpnyAdd5) {
		this.cmpnyAdd5 = cmpnyAdd5;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public List<DocUpload> getDocUploadList() {
		return docUploadList;
	}

	public void setDocUploadList(List<DocUpload> docUploadList) {
		this.docUploadList = docUploadList;
	}

	public String getOwnerAdd1() {
		return ownerAdd1;
	}

	public void setOwnerAdd1(String ownerAdd1) {
		this.ownerAdd1 = ownerAdd1;
	}

	public String getOwnerAdd2() {
		return ownerAdd2;
	}

	public void setOwnerAdd2(String ownerAdd2) {
		this.ownerAdd2 = ownerAdd2;
	}

	public String getOwnerAdd3() {
		return ownerAdd3;
	}

	public void setOwnerAdd3(String ownerAdd3) {
		this.ownerAdd3 = ownerAdd3;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOwnerCity() {
		return ownerCity;
	}

	public void setOwnerCity(String ownerCity) {
		this.ownerCity = ownerCity;
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getOwnerPostcode() {
		return ownerPostcode;
	}

	public void setOwnerPostcode(String ownerPostcode) {
		this.ownerPostcode = ownerPostcode;
	}

	public String getOwnerAdd4() {
		return ownerAdd4;
	}

	public void setOwnerAdd4(String ownerAdd4) {
		this.ownerAdd4 = ownerAdd4;
	}

	public String getOwnerAdd5() {
		return ownerAdd5;
	}

	public void setOwnerAdd5(String ownerAdd5) {
		this.ownerAdd5 = ownerAdd5;
	}
	
}
