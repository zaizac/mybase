/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
public class UserGroupRoleBranch implements Serializable {

	private static final long serialVersionUID = 3203359941262503728L;

	private String branchId;

	private String userGroupRoleCode;

	private String branchCode;

	private String branchName;

	private String branchAddress1;

	private String branchAddress2;

	private String branchAddress3;

	private String branchAddress4;

	private String branchAddress5;

	private String postcode;

	private String stateCode;

	private String cntryCode;

	private boolean status;

	private String createId;

	private Timestamp createDt;

	private String updateId;

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

}
