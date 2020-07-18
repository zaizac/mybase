/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_USER_GROUP_ROLE_GROUP")
@JsonInclude(Include.NON_NULL)
public class IdmUserGroupRoleGroup implements java.io.Serializable {

	private static final long serialVersionUID = -1533934058750880876L;
	
	@Id
	@Column(name = "GROUP_ID")
	private Integer id;	
	
	@Column(name = "GROUP_CODE")
	private String userGroupCode;
	
	@Column(name = "USER_GROUP_ROLE_PARENT")
	private String parentRoleGroup;
	
	@Column(name = "USER_GROUP_ROLE_CHILD")
	private String userRoleGroupCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="USER_GROUP_ROLE_CHILD", referencedColumnName="USER_GROUP_CODE", insertable=false, updatable=false)
	private IdmUserGroup userRoleGroup;
	
	@Column(name = "MAX_NO_OF_USER")
	private int maxNoOfUser;
	
	@Column(name = "USER_TYPE_CODE")
	private String  userType;
	
	@Column(name = "CREATE_ID")
	private String createId;
	
	@Column(name = "CREATE_DT")
	private Timestamp createDt;
	
	@Column(name = "UPDATE_ID")
	private String updateId;
	
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	public IdmUserGroupRoleGroup() {}
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
	
	public IdmUserGroup getUserRoleGroup() {
		return userRoleGroup;
	}
	
	public void setUserRoleGroup(IdmUserGroup userRoleGroup) {
		this.userRoleGroup = userRoleGroup;
	}
	
}