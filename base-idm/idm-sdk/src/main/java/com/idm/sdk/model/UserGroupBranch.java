/**
 * Copyright 2019. 
 */
package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
public class UserGroupBranch implements Serializable {

	private static final long serialVersionUID = 3203359941262503728L;

	private Integer branchId;
	
	private UserType userType;

	private String userGroupCode;
	
	private String branchCode;

	private String branchName;

	private String branchDept;

	private String addr1;

	private String addr2;

	private String addr3;

	private String addr4;

	private String addr5;

	private String cityCd;

	private String stateCd;

	private String cntryCd;

	private String zipcode;

	private String email;

	private String contactNo;

	private String faxNo;
	
	private String docRefNo;
	
	private Boolean status;

	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	private String systemType;

	public Integer getBranchId() {
		return branchId;
	}


	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public String getUserGroupCode() {
		return userGroupCode;
	}
	
	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
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


	public String getBranchDept() {
		return branchDept;
	}


	public void setBranchDept(String branchDept) {
		this.branchDept = branchDept;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getAddr2() {
		return addr2;
	}


	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}


	public String getAddr3() {
		return addr3;
	}


	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}


	public String getAddr4() {
		return addr4;
	}


	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}


	public String getAddr5() {
		return addr5;
	}


	public void setAddr5(String addr5) {
		this.addr5 = addr5;
	}


	public String getCityCd() {
		return cityCd;
	}


	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}


	public String getStateCd() {
		return stateCd;
	}


	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}


	public String getCntryCd() {
		return cntryCd;
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}


	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	/**
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}


	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	

	/**
	 * @return the docRefNo
	 */
	public String getDocRefNo() {
		return docRefNo;
	}


	/**
	 * @param docRefNo the docRefNo to set
	 */
	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
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
	
	

}
