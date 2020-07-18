/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.bstsb.idm.core.AbstractEntity;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
@Entity
@Table(name = "IDM_USER_GROUP_ROLE_BRANCH")
public class IdmUserGroupRoleBranch extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -5544619795986264155L;

	@Id
	@Column(name = "BRANCH_ID")
	private String branchId;

	@Column(name = "USER_GROUP_ROLE_CODE")
	private String userGroupRoleCode;

	@Column(name = "BRANCH_CODE")
	private String branchCode;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Column(name = "BRANCH_ADDRESS1")
	private String branchAddress1;

	@Column(name = "BRANCH_ADDRESS2")
	private String branchAddress2;

	@Column(name = "BRANCH_ADDRESS3")
	private String branchAddress3;

	@Column(name = "BRANCH_ADDRESS4")
	private String branchAddress4;

	@Column(name = "BRANCH_ADDRESS5")
	private String branchAddress5;

	@Column(name = "POSTCODE")
	private String postcode;

	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "CNTRY_CODE")
	private String cntryCode;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getUserGroupRoleCode() {
		return userGroupRoleCode;
	}


	public void setUserGroupRoleCode(String userGroupRoleCode) {
		this.userGroupRoleCode = userGroupRoleCode;
	}


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getBranchAddress1() {
		return branchAddress1;
	}


	public void setBranchAddress1(String branchAddress1) {
		this.branchAddress1 = branchAddress1;
	}


	public String getBranchAddress2() {
		return branchAddress2;
	}


	public void setBranchAddress2(String branchAddress2) {
		this.branchAddress2 = branchAddress2;
	}


	public String getBranchAddress3() {
		return branchAddress3;
	}


	public void setBranchAddress3(String branchAddress3) {
		this.branchAddress3 = branchAddress3;
	}


	public String getBranchAddress4() {
		return branchAddress4;
	}


	public void setBranchAddress4(String branchAddress4) {
		this.branchAddress4 = branchAddress4;
	}


	public String getBranchAddress5() {
		return branchAddress5;
	}


	public void setBranchAddress5(String branchAddress5) {
		this.branchAddress5 = branchAddress5;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getStateCode() {
		return stateCode;
	}


	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	public String getCntryCode() {
		return cntryCode;
	}


	public void setCntryCode(String cntryCode) {
		this.cntryCode = cntryCode;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
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

}
