package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonDateDeserializer;
import com.util.serializer.JsonDateSerializer;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_PROFILE database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_PROFILE")
//@NamedQuery(name = "IdmProfile.findAll", query = "SELECT i FROM IdmProfile i")
public class IdmProfile extends AbstractEntity implements Serializable, IQfCriteria<IdmProfile> {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_PROF_ID", unique = true, nullable = false)
	private Integer userProfId;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 100)
	private String userId;

	@Column(name = "FIRST_NM", nullable = false, length = 100)
	private String firstName;

	@Column(name = "LAST_NM", length = 100)
	private String lastName;

	@Column(name = "NATIONAL_ID", length = 25)
	private String nationalId;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	private Date dob;

	@Column(name = "GENDER", length = 1)
	private String gender;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "CONTACT_NO", length = 50)
	private String contactNo;

	@Column(name = "CNTRY_CD", length = 20)
	private String cntryCd;

	@Column(name = "POSITION", length = 255)
	private String position;

	@JsonIgnoreProperties("idmProfiles")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TYPE_CODE", nullable = false)
	private IdmUserType userType;

	@JsonIgnoreProperties("idmProfileLst")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ROLE_GROUP_CODE", referencedColumnName = "USER_GROUP_CODE")
	private IdmUserGroup userRoleGroup;

	@Column(name = "USER_GROUP_ROLE_BRANCH_CD", length = 50)
	private String userGroupRoleBranchCode;

	@Column(name = "ROLE_BRANCH")
	private Boolean roleBranch;

	@Column(name = "PROF_REG_NO", length = 20)
	private String profRegNo;

	@Column(name = "PROF_ID")
	private Integer profId;

	@Column(name = "BRANCH_ID")
	private Integer branchId;

	@Column(name = "DOC_MGT_ID", length = 40)
	private String docMgtId;

	@Column(name = "LAST_LOGIN")
	private Timestamp lastLogin;

	@Column(name = "AUTH_OPTION", length = 225)
	private String authOption;

	@Column(name = "FCM_ACCESS_TOKEN", length = 225)
	private String fcmAccessToken;

	@Column(name = "ACTIVATION_CODE", length = 20)
	private String activationCode;

	@Column(name = "STATUS", length = 4)
	private String status;

	@Column(name = "SYNC_FLAG")
	private Integer syncFlag;
	
	@Column(name = "SYSTEM_TYPE")
	private String systemType;
	
	@OneToOne(mappedBy = "idmProfile")
	private IdmFcm fcm;

	@Column(name = "CREATE_ID", length = 100)
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "UPDATE_ID", length = 100)
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;

	@Transient
	private List<IdmRole> roles;

	@Transient
	private List<IdmMenu> menus;

	@Transient
	private String password;

	@Transient
	private String role;

	@Transient
	private String userGroupCode;

	@Transient
	private String userRoleGroupDesc;

	@Transient
	private List<String> userRoleGroupCodeList;

	@Transient
	private boolean defaultActivated;


	public IdmProfile() {
		// DO NOTHING
	}


	/**
	 * @return the userProfId
	 */
	public int getUserProfId() {
		return userProfId;
	}


	/**
	 * @param userProfId
	 *             the userProfId to set
	 */
	public void setUserProfId(int userProfId) {
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return BaseUtil.getStrUpperWithNull(firstName);
	}


	/**
	 * @param firstName
	 *             the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = BaseUtil.getStrUpperWithNull(firstName);
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return BaseUtil.getStrUpperWithNull(lastName);
	}


	/**
	 * @param lastName
	 *             the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = BaseUtil.getStrUpperWithNull(lastName);
	}


	/**
	 * @return the nationalId
	 */
	public String getNationalId() {
		return BaseUtil.getStrUpperWithNull(nationalId);
	}


	/**
	 * @param nationalId
	 *             the nationalId to set
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = BaseUtil.getStrUpperWithNull(nationalId);
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
	 * @return the gender
	 */
	public String getGender() {
		return BaseUtil.getStrUpperWithNull(gender);
	}


	/**
	 * @param gender
	 *             the gender to set
	 */
	public void setGender(String gender) {
		this.gender = BaseUtil.getStrUpperWithNull(gender);
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
	 * @return the contactNo
	 */
	public String getContactNo() {
		return BaseUtil.getStrUpperWithNull(contactNo);
	}


	/**
	 * @param contactNo
	 *             the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = BaseUtil.getStrUpperWithNull(contactNo);
	}


	/**
	 * @return the cntryCd
	 */
	public String getCntryCd() {
		return BaseUtil.getStrUpperWithNull(cntryCd);
	}


	/**
	 * @param cntryCd
	 *             the cntryCd to set
	 */
	public void setCntryCd(String cntryCd) {
		this.cntryCd = BaseUtil.getStrUpperWithNull(cntryCd);
	}


	/**
	 * @return the position
	 */
	public String getPosition() {
		return BaseUtil.getStrUpperWithNull(position);
	}


	/**
	 * @param position
	 *             the position to set
	 */
	public void setPosition(String position) {
		this.position = BaseUtil.getStrUpperWithNull(position);
	}


	/**
	 * @return the userType
	 */
	public IdmUserType getUserType() {
		return userType;
	}


	/**
	 * @param userType
	 *             the userType to set
	 */
	public void setUserType(IdmUserType userType) {
		this.userType = userType;
	}


	/**
	 * @return the userRoleGroup
	 */
	public IdmUserGroup getUserRoleGroup() {
		return userRoleGroup;
	}


	/**
	 * @param userRoleGroup
	 *             the userRoleGroup to set
	 */
	public void setUserRoleGroup(IdmUserGroup userRoleGroup) {
		this.userRoleGroup = userRoleGroup;
	}


	/**
	 * @return the userGroupRoleBranchCode
	 */
	public String getUserGroupRoleBranchCode() {
		return BaseUtil.getStrUpperWithNull(userGroupRoleBranchCode);
	}


	/**
	 * @param userGroupRoleBranchCode
	 *             the userGroupRoleBranchCode to set
	 */
	public void setUserGroupRoleBranchCode(String userGroupRoleBranchCode) {
		this.userGroupRoleBranchCode = BaseUtil.getStrUpperWithNull(userGroupRoleBranchCode);
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
	 * @return the profRegNo
	 */
	public String getProfRegNo() {
		return BaseUtil.getStrUpperWithNull(profRegNo);
	}


	/**
	 * @param profRegNo
	 *             the profRegNo to set
	 */
	public void setProfRegNo(String profRegNo) {
		this.profRegNo = BaseUtil.getStrUpperWithNull(profRegNo);
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
	 * @return the lastLogin
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}


	/**
	 * @param lastLogin
	 *             the lastLogin to set
	 */
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}


	/**
	 * @return the authOption
	 */
	public String getAuthOption() {
		return authOption;
	}


	/**
	 * @param authOption
	 *             the authOption to set
	 */
	public void setAuthOption(String authOption) {
		this.authOption = authOption;
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


	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return BaseUtil.getStrUpperWithNull(status);
	}


	/**
	 * @param status
	 *             the status to set
	 */
	public void setStatus(String status) {
		this.status = BaseUtil.getStrUpperWithNull(status);
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
	 * @return the createId
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the createDt
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the updateId
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the updateDt
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the roles
	 */
	public List<IdmRole> getRoles() {
		return roles;
	}


	/**
	 * @param roles
	 *             the roles to set
	 */
	public void setRoles(List<IdmRole> roles) {
		this.roles = roles;
	}


	/**
	 * @return the menus
	 */
	public List<IdmMenu> getMenus() {
		return menus;
	}


	/**
	 * @param menus
	 *             the menus to set
	 */
	public void setMenus(List<IdmMenu> menus) {
		this.menus = menus;
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
		return BaseUtil.getStrUpperWithNull(role);
	}


	/**
	 * @param role
	 *             the role to set
	 */
	public void setRole(String role) {
		this.role = BaseUtil.getStrUpperWithNull(role);
	}


	/**
	 * @return the userGroupCode
	 */
	public String getUserGroupCode() {
		return BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	/**
	 * @param userGroupCode
	 *             the userGroupCode to set
	 */
	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	/**
	 * @return the userRoleGroupDesc
	 */
	public String getUserRoleGroupDesc() {
		return BaseUtil.getStrUpperWithNull(userRoleGroupDesc);
	}


	/**
	 * @param userRoleGroupDesc
	 *             the userRoleGroupDesc to set
	 */
	public void setUserRoleGroupDesc(String userRoleGroupDesc) {
		this.userRoleGroupDesc = BaseUtil.getStrUpperWithNull(userRoleGroupDesc);
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
	 * @return the defaultActivated
	 */
	public boolean isDefaultActivated() {
		return defaultActivated;
	}


	/**
	 * @param defaultActivated
	 *             the defaultActivated to set
	 */
	public void setDefaultActivated(boolean defaultActivated) {
		this.defaultActivated = defaultActivated;
	}
	
	public IdmFcm getFcm() {
		return fcm;
	}
	
	public void setFcm(IdmFcm fcm) {
		this.fcm = fcm;
	}

}