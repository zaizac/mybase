/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.bff.cmn.form.CustomMultipartFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserRole;
import com.idm.sdk.model.UserType;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateDeserializer;
import com.util.serializer.JsonDateSerializer;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class IdmUploadProfilePictureForm implements Serializable {

	private static final long serialVersionUID = -757006248093527321L;

	private String id;

	private String docRefNo;

	private String nationalId;

	private String userId;

	private String password;

	private String role;

	private String firstName;

	private String lastName;

	private String email;

	private String status;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date dob;

	private String gender;

	private String contactNo;

	private String cntryCd;

	private UserType userType;

	private UserGroup userRoleGroup;

	private String userGroupCode;

	private String userRoleGroupCode;

	private String userRoleGroupDesc;

	private String parentRoleGroup;

	private String createId;

	private String branchCode;

	private String userGroupRoleBranchCode;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	private List<UserRole> rolesList;

	private List<String> selectedRoles;

	private List<UserMenu> menusList;

	private List<String> cmpanyRegNoList;

	private String docMgtId;

	private String myKadId;

	private String searchId;

	private String isMyimms;

	private String position;

	private String refNo;

	private List<CustomMultipartFile> fileUploads;

	private String recordCardNo;

	// created to set profile for userid
	private CustomMultipartFile resFile;

	private String isAdmin;
	
	private String authOption;


	public IdmUploadProfilePictureForm() {
		// DO NOTHING
	}


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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public String getCntryCd() {
		return cntryCd;
	}


	public void setCntry(String cntryCd) {
		this.cntryCd = cntryCd;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public UserGroup getUserRoleGroup() {
		return userRoleGroup;
	}


	public void setUserRoleGroup(UserGroup userRoleGroup) {
		this.userRoleGroup = userRoleGroup;
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
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


	public String getUserGroupRoleBranchCode() {
		return userGroupRoleBranchCode;
	}


	public void setUserGroupRoleBranchCode(String userGroupRoleBranchCode) {
		this.userGroupRoleBranchCode = userGroupRoleBranchCode;
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


	public List<String> getCmpanyRegNoList() {
		return cmpanyRegNoList;
	}


	public void setCmpanyRegNoList(List<String> cmpanyRegNoList) {
		this.cmpanyRegNoList = cmpanyRegNoList;
	}


	public List<CustomMultipartFile> getFileUploads() {
		return fileUploads;
	}


	public void setFileUploads(List<CustomMultipartFile> fileUploads) {
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


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public CustomMultipartFile getResFile() {
		return resFile;
	}


	public void setResFile(CustomMultipartFile resFile) {
		this.resFile = resFile;
	}


	public String getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}


	/**
	 * @return the authOption
	 */
	public String getAuthOption() {
		return authOption;
	}


	/**
	 * @param authOption the authOption to set
	 */
	public void setAuthOption(String authOption) {
		this.authOption = authOption;
	}
	
	

}
