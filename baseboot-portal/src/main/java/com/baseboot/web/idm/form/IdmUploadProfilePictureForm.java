/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.form;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.baseboot.web.dto.FileUpload;
import com.dm.sdk.model.Documents;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class IdmUploadProfilePictureForm implements Serializable {

	private static final long serialVersionUID = -757006248093527321L;

	private String docRefNo;

	private String nationalId;

	private String userId;

	private String password;

	private String role;

	private String fullName;

	private String email;

	private String status;

	private Date dob;

	private String gender;

	private String mobPhoneNo;

	private String cntry;

	private String userType;

	private String userGroupCode;

	private String userRoleGroupCode;

	private String userRoleGroupDesc;

	private String parentRoleGroup;

	private String createId;

	private String branchCode;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;

	private List<UserRole> rolesList;

	private List<String> selectedRoles;

	private List<UserMenu> menusList;

	private boolean isFWCMSLogin;

	private String isFwcms;

	private List<String> cmpanyRegNoList;

	private String transactionType; // user create =C, update= U, profile
								// update = UP

	private List<FileUpload> fileUploads;

	private String docMgtId;

	private String myKadId;

	private String searchId;

	private String isMyimms;

	private String position;

	// added for upload profile picture///
	private List<TrxnDocuments> trxnDocuments;

	private String RefNo;

	private Documents profilePicture;

	private String recordCardNo;


	public String getDocRefNo() {
		return docRefNo;
	}


	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}


	public String getNationalId() {
		return nationalId;
	}


	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMobPhoneNo() {
		return mobPhoneNo;
	}


	public void setMobPhoneNo(String mobPhoneNo) {
		this.mobPhoneNo = mobPhoneNo;
	}


	public String getCntry() {
		return cntry;
	}


	public void setCntry(String cntry) {
		this.cntry = cntry;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getUserGroupCode() {
		return userGroupCode;
	}


	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}


	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}


	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}


	public String getUserRoleGroupDesc() {
		return userRoleGroupDesc;
	}


	public void setUserRoleGroupDesc(String userRoleGroupDesc) {
		this.userRoleGroupDesc = userRoleGroupDesc;
	}


	public String getParentRoleGroup() {
		return parentRoleGroup;
	}


	public void setParentRoleGroup(String parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public List<UserRole> getRolesList() {
		return rolesList;
	}


	public void setRolesList(List<UserRole> rolesList) {
		this.rolesList = rolesList;
	}


	public List<String> getSelectedRoles() {
		return selectedRoles;
	}


	public void setSelectedRoles(List<String> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}


	public List<UserMenu> getMenusList() {
		return menusList;
	}


	public void setMenusList(List<UserMenu> menusList) {
		this.menusList = menusList;
	}


	public boolean isFWCMSLogin() {
		return isFWCMSLogin;
	}


	public void setFWCMSLogin(boolean isFWCMSLogin) {
		this.isFWCMSLogin = isFWCMSLogin;
	}


	public String getIsFwcms() {
		return isFwcms;
	}


	public void setIsFwcms(String isFwcms) {
		this.isFwcms = isFwcms;
	}


	public List<String> getCmpanyRegNoList() {
		return cmpanyRegNoList;
	}


	public void setCmpanyRegNoList(List<String> cmpanyRegNoList) {
		this.cmpanyRegNoList = cmpanyRegNoList;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}


	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}


	public String getMyKadId() {
		return myKadId;
	}


	public void setMyKadId(String myKadId) {
		this.myKadId = myKadId;
	}


	public String getSearchId() {
		return searchId;
	}


	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}


	public String getIsMyimms() {
		return isMyimms;
	}


	public void setIsMyimms(String isMyimms) {
		this.isMyimms = isMyimms;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public List<TrxnDocuments> getTrxnDocuments() {
		return trxnDocuments;
	}


	public void setTrxnDocuments(List<TrxnDocuments> trxnDocuments) {
		this.trxnDocuments = trxnDocuments;
	}


	public String getRefNo() {
		return RefNo;
	}


	public void setRefNo(String refNo) {
		RefNo = refNo;
	}


	public Documents getProfilePicture() {
		return profilePicture;
	}


	public void setProfilePicture(Documents profilePicture) {
		this.profilePicture = profilePicture;
	}


	public String getRecordCardNo() {
		return recordCardNo;
	}


	public void setRecordCardNo(String recordCardNo) {
		this.recordCardNo = recordCardNo;
	}


	public String getDocMgtId() {
		return docMgtId;
	}


	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}

}
