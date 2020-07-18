/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.sdk.model.Token;
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
 * @author mary.jane
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -5598980692217068236L;

	private Integer userProfId;

	private String userId;

	private Integer branchId;

	private String cntryCd;

	private String contactNo;

	private String fullName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date dob;

	private String docMgtId;
	
	private String dmBucket;

	private String email;

	private String gender;

	private String fcmAccessToken;

	private String firstName;

	private Date lastLogin;

	private String lastName;

	private String nationalId;

	private String position;

	private Integer profId;

	private String profRegNo;

	private Boolean roleBranch;

	private Integer syncFlag;

	private String status;

	private String userGroupRoleBranchCode;

	private UserGroup userRoleGroup;

	private UserType userType;

	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtTo;

	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtTo;

	private String userRoleGroupCode;

	private Token token;

	private List<UserRole> roles;

	private List<UserMenu> menus;

	private String authOption;

	public User() {
		// DO NOTHING
	}


	/**
	 * @return the userProfId
	 */
	public Integer getUserProfId() {
		return userProfId;
	}


	/**
	 * @param userProfId
	 *             the userProfId to set
	 */
	public void setUserProfId(Integer userProfId) {
		this.userProfId = userProfId;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId
	 *             the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}


	/**
	 * @param branchId
	 *             the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}


	/**
	 * @return the cntryCd
	 */
	public String getCntryCd() {
		return cntryCd;
	}


	/**
	 * @param cntryCd
	 *             the cntryCd to set
	 */
	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}


	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}


	/**
	 * @param contactNo
	 *             the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}


	/**
	 * @param fullName
	 *             the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}


	/**
	 * @param dob
	 *             the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}


	/**
	 * @return the docMgtId
	 */
	public String getDocMgtId() {
		return docMgtId;
	}


	/**
	 * @param docMgtId
	 *             the docMgtId to set
	 */
	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}
	

	/**
	 * @return the dmBucket
	 */
	public String getDmBucket() {
		return dmBucket;
	}


	/**
	 * @param dmBucket the dmBucket to set
	 */
	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email
	 *             the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}


	/**
	 * @param gender
	 *             the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}


	/**
	 * @return the fcmAccessToken
	 */
	public String getFcmAccessToken() {
		return fcmAccessToken;
	}


	/**
	 * @param fcmAccessToken
	 *             the fcmAccessToken to set
	 */
	public void setFcmAccessToken(String fcmAccessToken) {
		this.fcmAccessToken = fcmAccessToken;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName
	 *             the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}


	/**
	 * @param lastLogin
	 *             the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName
	 *             the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the nationalId
	 */
	public String getNationalId() {
		return nationalId;
	}


	/**
	 * @param nationalId
	 *             the nationalId to set
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}


	/**
	 * @param position
	 *             the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}


	/**
	 * @return the profId
	 */
	public Integer getProfId() {
		return profId;
	}


	/**
	 * @param profId
	 *             the profId to set
	 */
	public void setProfId(Integer profId) {
		this.profId = profId;
	}


	/**
	 * @return the profRegNo
	 */
	public String getProfRegNo() {
		return profRegNo;
	}


	/**
	 * @param profRegNo
	 *             the profRegNo to set
	 */
	public void setProfRegNo(String profRegNo) {
		this.profRegNo = profRegNo;
	}


	/**
	 * @return the roleBranch
	 */
	public Boolean getRoleBranch() {
		return roleBranch;
	}


	/**
	 * @param roleBranch
	 *             the roleBranch to set
	 */
	public void setRoleBranch(Boolean roleBranch) {
		this.roleBranch = roleBranch;
	}


	/**
	 * @return the syncFlag
	 */
	public Integer getSyncFlag() {
		return syncFlag;
	}


	/**
	 * @param syncFlag
	 *             the syncFlag to set
	 */
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status
	 *             the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the userGroupRoleBranchCode
	 */
	public String getUserGroupRoleBranchCode() {
		return userGroupRoleBranchCode;
	}


	/**
	 * @param userGroupRoleBranchCode the userGroupRoleBranchCode to set
	 */
	public void setUserGroupRoleBranchCode(String userGroupRoleBranchCode) {
		this.userGroupRoleBranchCode = userGroupRoleBranchCode;
	}


	/**
	 * @return the userRoleGroup
	 */
	public UserGroup getUserRoleGroup() {
		return userRoleGroup;
	}


	/**
	 * @param userRoleGroup
	 *             the userRoleGroup to set
	 */
	public void setUserRoleGroup(UserGroup userRoleGroup) {
		this.userRoleGroup = userRoleGroup;
	}


	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}


	/**
	 * @param userType
	 *             the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the createDt
	 */
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the createDtFrom
	 */
	public Timestamp getCreateDtFrom() {
		return createDtFrom;
	}


	/**
	 * @param createDtFrom
	 *             the createDtFrom to set
	 */
	public void setCreateDtFrom(Timestamp createDtFrom) {
		this.createDtFrom = createDtFrom;
	}


	/**
	 * @return the createDtTo
	 */
	public Timestamp getCreateDtTo() {
		return createDtTo;
	}


	/**
	 * @param createDtTo
	 *             the createDtTo to set
	 */
	public void setCreateDtTo(Timestamp createDtTo) {
		this.createDtTo = createDtTo;
	}


	/**
	 * @return the updateId
	 */
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the updateDt
	 */
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the updateDtFrom
	 */
	public Timestamp getUpdateDtFrom() {
		return updateDtFrom;
	}


	/**
	 * @param updateDtFrom
	 *             the updateDtFrom to set
	 */
	public void setUpdateDtFrom(Timestamp updateDtFrom) {
		this.updateDtFrom = updateDtFrom;
	}


	/**
	 * @return the updateDtTo
	 */
	public Timestamp getUpdateDtTo() {
		return updateDtTo;
	}


	/**
	 * @param updateDtTo
	 *             the updateDtTo to set
	 */
	public void setUpdateDtTo(Timestamp updateDtTo) {
		this.updateDtTo = updateDtTo;
	}


	/**
	 * @return the userRoleGroupCode
	 */
	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}


	/**
	 * @param userRoleGroupCode
	 *             the userRoleGroupCode to set
	 */
	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}


	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}


	/**
	 * @param token
	 *             the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}


	/**
	 * @return the roles
	 */
	public List<UserRole> getRoles() {
		return roles;
	}


	/**
	 * @param roles
	 *             the roles to set
	 */
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}


	/**
	 * @return the menus
	 */
	public List<UserMenu> getMenus() {
		return menus;
	}


	/**
	 * @param menus
	 *             the menus to set
	 */
	public void setMenus(List<UserMenu> menus) {
		this.menus = menus;
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
