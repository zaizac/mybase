/**
 * Copyright 2019. 
 */
package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserGroup implements Serializable, IQfCriteria<UserGroup> {

	private static final long serialVersionUID = 7164699802967151845L;

	private Integer id;

	private String userGroupCode;

	private String userRoleGroupCode;

	private String parentRoleGroup;

	private Integer maxNoOfUser;

	private String userRoleGroupDesc;

	private String crtUsrId;

	private String modUsrId;

	private Timestamp crtTs;

	private Timestamp modTs;

	private UserType userType;

	private String userGroupDesc;

	private String userTypeDesc;

	private String userTypeCode;

	private Boolean selCountry;

	private Boolean selBranch;


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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCrtUsrId() {
		return crtUsrId;
	}


	public void setCrtUsrId(String crtUsrId) {
		this.crtUsrId = crtUsrId;
	}


	public String getModUsrId() {
		return modUsrId;
	}


	public void setModUsrId(String modUsrId) {
		this.modUsrId = modUsrId;
	}


	public Timestamp getCrtTs() {
		return crtTs;
	}


	public void setCrtTs(Timestamp crtTs) {
		this.crtTs = crtTs;
	}


	public Timestamp getModTs() {
		return modTs;
	}


	public void setModTs(Timestamp modTs) {
		this.modTs = modTs;
	}


	public String getParentRoleGroup() {
		return parentRoleGroup;
	}


	public void setParentRoleGroup(String parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}


	public Integer getMaxNoOfUser() {
		return maxNoOfUser;
	}


	public void setMaxNoOfUser(Integer maxNoOfUser) {
		this.maxNoOfUser = maxNoOfUser;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public String getUserGroupDesc() {
		return userGroupDesc;
	}


	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}


	public String getUserTypeDesc() {
		return userTypeDesc;
	}


	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
	}


	public Boolean isSelBranch() {
		return selBranch;
	}


	public void setSelBranch(Boolean selBranch) {
		this.selBranch = selBranch;
	}


	public Boolean isSelCountry() {
		return selCountry;
	}


	public void setSelCountry(Boolean selCountry) {
		this.selCountry = selCountry;
	}


	public String getUserTypeCode() {
		return userTypeCode;
	}


	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

}