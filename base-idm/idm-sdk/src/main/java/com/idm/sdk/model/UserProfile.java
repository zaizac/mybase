package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonDateDeserializer;
import com.util.serializer.JsonDateSerializer;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserProfile implements Serializable, IQfCriteria<UserProfile> {

	private static final long serialVersionUID = -8651723011419030466L;

	private Integer userProfId;

	private String userId;

	private Integer branchId;

	private String cntryCd;

	private String contactNo;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date dob;

	private String docMgtId;

	private String email;

	private String fcmAccessToken;
	
	private String activationCode;

	private String firstName;

	private String gender;

	private Date lastLogin;

	private String lastName;

	private String nationalId;

	private String position;

	private Integer profId;

	private String profRegNo;

	private Boolean roleBranch;

	private Integer syncFlag;

	private String status;

	private String userGroupRoleBranchCd;

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

	private List<String> userRoleGroupCodeList;

	private String userGroupRoleBranchCode;
	
	private String authOption;
	
	private String systemType;

	// -------------------- END

	private String fullName;


	// private String userType;

	private String userRoleGroupCode;

	private String parentRoleGroup;

	private String orgRegNo;

	private String password;

	private String role;

	private List<UserRole> roles;

	private List<UserMenu> menus;

	private String userGroupCode;

	private String userRoleGroupDesc;
	
	private String userTypeCode;
	
	private String userTypeDesc;
	
	private boolean isDefaultActivated;

	private String remarks;

	private String baseUrl;

	private CustomNotification customNotification;
	
	private Integer countUser;


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


	public String getActivationCode() {
		return activationCode;
	}


	public void setActivationCode(String accessCode) {
		this.activationCode = accessCode;
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
	 * @return the userGroupRoleBranchCd
	 */
	public String getUserGroupRoleBranchCd() {
		return userGroupRoleBranchCd;
	}


	/**
	 * @param userGroupRoleBranchCd
	 *             the userGroupRoleBranchCd to set
	 */
	public void setUserGroupRoleBranchCd(String userGroupRoleBranchCd) {
		this.userGroupRoleBranchCd = userGroupRoleBranchCd;
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
	 * @return the userGroupRoleBranchCode
	 */
	public String getUserGroupRoleBranchCode() {
		return userGroupRoleBranchCode;
	}


	/**
	 * @param userGroupRoleBranchCode
	 *             the userGroupRoleBranchCode to set
	 */
	public void setUserGroupRoleBranchCode(String userGroupRoleBranchCode) {
		this.userGroupRoleBranchCode = userGroupRoleBranchCode;
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


	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}


	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
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
	 * @return the parentRoleGroup
	 */
	public String getParentRoleGroup() {
		return parentRoleGroup;
	}


	/**
	 * @param parentRoleGroup
	 *             the parentRoleGroup to set
	 */
	public void setParentRoleGroup(String parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}


	/**
	 * @return the orgRegNo
	 */
	public String getOrgRegNo() {
		return orgRegNo;
	}


	/**
	 * @param orgRegNo
	 *             the orgRegNo to set
	 */
	public void setOrgRegNo(String orgRegNo) {
		this.orgRegNo = orgRegNo;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password
	 *             the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role
	 *             the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
	 * @return the userGroupCode
	 */
	public String getUserGroupCode() {
		return userGroupCode;
	}


	/**
	 * @param userGroupCode
	 *             the userGroupCode to set
	 */
	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}


	/**
	 * @return the userRoleGroupDesc
	 */
	public String getUserRoleGroupDesc() {
		return userRoleGroupDesc;
	}


	/**
	 * @param userRoleGroupDesc
	 *             the userRoleGroupDesc to set
	 */
	public void setUserRoleGroupDesc(String userRoleGroupDesc) {
		this.userRoleGroupDesc = userRoleGroupDesc;
	}


	/**
	 * @return the userRoleGroupCodeList
	 */
	public List<String> getUserRoleGroupCodeList() {
		return userRoleGroupCodeList;
	}


	/**
	 * @param userRoleGroupCodeList
	 *             the userRoleGroupCodeList to set
	 */
	public void setUserRoleGroupCodeList(List<String> userRoleGroupCodeList) {
		this.userRoleGroupCodeList = userRoleGroupCodeList;
	}


	/**
	 * @return the isDefaultActivated
	 */
	public boolean isDefaultActivated() {
		return isDefaultActivated;
	}


	/**
	 * @param isDefaultActivated
	 *             the isDefaultActivated to set
	 */
	public void setDefaultActivated(boolean isDefaultActivated) {
		this.isDefaultActivated = isDefaultActivated;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks
	 *             the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}


	/**
	 * @param baseUrl
	 *             the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


	/**
	 * @return the customNotification
	 */
	public CustomNotification getCustomNotification() {
		return customNotification;
	}


	/**
	 * @param customNotification
	 *             the customNotification to set
	 */
	public void setCustomNotification(CustomNotification customNotification) {
		this.customNotification = customNotification;
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

	public String getUserTypeCode() {
		return userTypeCode;
	}


	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}


	public String getUserTypeDesc() {
		return userTypeDesc;
	}


	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
	}


	@Override
	public String toString() {
		return new Gson().toJson(this);
	}


	/**
	 * @return the countUser
	 */
	public Integer getCountUser() {
		return countUser;
	}


	/**
	 * @param countUser the countUser to set
	 */
	public void setCountUser(Integer countUser) {
		this.countUser = countUser;
	}
	
	
}