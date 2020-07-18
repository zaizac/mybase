/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile implements Serializable {

	private static final long serialVersionUID = -3245199650153350590L;

	private String userId;

	private String status;

	private String fullName;

	private Date dob;

	private String gender;

	private String mobPhoneNo;

	private String email;

	private String cntry;

	private Integer profId;

	private Integer branchId;

	private String userType;

	private String userRoleGroupCode;

	private String parentRoleGroup;

	private String orgRegNo;

	private String nationalId;

	private String docMgtId;

	private Date lastLogin;

	private Integer syncFlag;

	private String createId;

	private Timestamp createDt;

	private String updateId;

	private Timestamp updateDt;

	private String password;

	private String role;

	private List<UserRole> rolesList;

	private List<UserMenu> menusList;

	private String userGroupCode;

	private String userRoleGroupDesc;

	private List<String> userRoleGroupCodeList;

	private boolean isDefaultActivated;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCntry() {
		return cntry;
	}


	public void setCntry(String cntry) {
		this.cntry = cntry;
	}


	public Integer getProfId() {
		return profId;
	}


	public void setProfId(Integer profId) {
		this.profId = profId;
	}


	public Integer getBranchId() {
		return branchId;
	}


	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}


	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}


	public String getParentRoleGroup() {
		return parentRoleGroup;
	}


	public void setParentRoleGroup(String parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}


	public String getOrgRegNo() {
		return orgRegNo;
	}


	public void setOrgRegNo(String orgRegNo) {
		this.orgRegNo = orgRegNo;
	}


	public String getNationalId() {
		return nationalId;
	}


	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


	public String getDocMgtId() {
		return docMgtId;
	}


	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}


	public Date getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	public Integer getSyncFlag() {
		return syncFlag;
	}


	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
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


	public List<UserRole> getRolesList() {
		return rolesList;
	}


	public void setRolesList(List<UserRole> rolesList) {
		this.rolesList = rolesList;
	}


	public List<UserMenu> getMenusList() {
		return menusList;
	}


	public void setMenusList(List<UserMenu> menusList) {
		this.menusList = menusList;
	}


	public String getUserGroupCode() {
		return userGroupCode;
	}


	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}


	public String getUserRoleGroupDesc() {
		return userRoleGroupDesc;
	}


	public void setUserRoleGroupDesc(String userRoleGroupDesc) {
		this.userRoleGroupDesc = userRoleGroupDesc;
	}


	public List<String> getUserRoleGroupCodeList() {
		return userRoleGroupCodeList;
	}


	public void setUserRoleGroupCodeList(List<String> userRoleGroupCodeList) {
		this.userRoleGroupCodeList = userRoleGroupCodeList;
	}


	public boolean isDefaultActivated() {
		return isDefaultActivated;
	}


	public void setDefaultActivated(boolean isDefaultActivated) {
		this.isDefaultActivated = isDefaultActivated;
	}

}