/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserGroup implements Serializable {

	private static final long serialVersionUID = -245438070614211066L;

	private Integer id;

	private String userGroupCode;

	private String userRoleGroupCode;

	private String parentRoleGroup;

	private int maxNoOfUser;

	private int maxNoOfUserCreated;

	private boolean createByProfId;

	private boolean createByBranchId;

	private boolean systemCreate;

	private String userRoleGroupDesc;

	private String crtUsrId;

	private String modUsrId;

	private Timestamp crtTs;

	private Timestamp modTs;

	private String userType;

	private String userGroupDesc;

	private String userTypeDesc;

	private boolean canCreate;


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


	public int getMaxNoOfUser() {
		return maxNoOfUser;
	}


	public void setMaxNoOfUser(int maxNoOfUser) {
		this.maxNoOfUser = maxNoOfUser;
	}


	public int getMaxNoOfUserCreated() {
		return maxNoOfUserCreated;
	}


	public void setMaxNoOfUserCreated(int maxNoOfUserCreated) {
		this.maxNoOfUserCreated = maxNoOfUserCreated;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
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


	public boolean isCreateByProfId() {
		return createByProfId;
	}


	public void setCreateByProfId(boolean createByProfId) {
		this.createByProfId = createByProfId;
	}


	public boolean isCreateByBranchId() {
		return createByBranchId;
	}


	public void setCreateByBranchId(boolean createByBranchId) {
		this.createByBranchId = createByBranchId;
	}


	public boolean isSystemCreate() {
		return systemCreate;
	}


	public void setSystemCreate(boolean systemCreate) {
		this.systemCreate = systemCreate;
	}


	public boolean isCanCreate() {
		canCreate = maxNoOfUser > maxNoOfUserCreated;
		return canCreate;
	}

}