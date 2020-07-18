/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bstsb.idm.core.AbstractEntity;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_PROFILE")
public class IdmProfile extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "FULLNAME")
	private String fullName;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	private Date dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "CONTACT_NO")
	private String mobPhoneNo;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CNTRY")
	private String cntry;

	@Column(name = "PROF_ID")
	private Integer profId;

	@Column(name = "BRANCH_ID")
	private Integer branchId;

	@Column(name = "USER_TYPE_CODE")
	private String userType;

	@Column(name = "USER_ROLE_GROUP_CODE")
	private String userRoleGroupCode;

	@Column(name = "ORG_REG_NO")
	private String orgRegNo;

	@Column(name = "NATIONAL_ID")
	private String nationalId;

	@Column(name = "DOC_MGT_ID")
	private String docMgtId;

	@Column(name = "LAST_LOGIN")
	private Date lastLogin;

	@Column(name = "SYNC_FLAG")
	private Integer syncFlag;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Transient
	private String password;

	@Transient
	private String role;

	@Transient
	private List<IdmRole> rolesList;

	@Transient
	private List<IdmMenu> menusList;

	@Transient
	private String userGroupCode;

	@Transient
	private String userRoleGroupDesc;

	@Transient
	private List<String> userRoleGroupCodeList;

	@Transient
	private boolean isDefaultActivated;


	public IdmProfile() {
		// DO NOTHING
	}


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
		this.fullName = BaseUtil.getStrUpperWithNull(fullName);
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
		this.userType = BaseUtil.getStrUpperWithNull(userType);
	}


	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}


	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}


	public String getOrgRegNo() {
		return orgRegNo;
	}


	public void setOrgRegNo(String orgRegNo) {
		this.orgRegNo = BaseUtil.getStrUpperWithNull(orgRegNo);
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


	@Override
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
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


	public List<IdmRole> getRolesList() {
		return rolesList;
	}


	public void setRolesList(List<IdmRole> rolesList) {
		this.rolesList = rolesList;
	}


	public List<IdmMenu> getMenusList() {
		return menusList;
	}


	public void setMenusList(List<IdmMenu> menusList) {
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