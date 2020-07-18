package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_USER_GROUP_BRANCH database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_USER_GROUP_BRANCH")
@NamedQuery(name = "IdmUserGroupBranch.findAll", query = "SELECT i FROM IdmUserGroupBranch i")
public class IdmUserGroupBranch extends AbstractEntity implements Serializable, IQfCriteria<IdmUserGroupBranch> {

	private static final long serialVersionUID = -5544619795986264155L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UG_BRANCH_ID", unique = true, nullable = false)
	private Integer branchId;

	// bi-directional many-to-one association to IdmUserType
	@JsonIgnoreProperties("idmUserGroupRoleBranches")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TYPE_CODE", nullable = false)
	private IdmUserType userType;
	
	@Column(name = "USER_GROUP_CODE", nullable = false, length = 50)
	private String userGroupCode;

	@Column(name = "UG_BRANCH_CD", nullable = false, length = 50)
	private String branchCode;

	@Column(name = "UG_BRANCH_NM", nullable = false, length = 255)
	private String branchName;

	@Column(name = "UG_BRANCH_DEPT", length = 255)
	private String branchDept;

	@Column(name = "ADDR_1", nullable = false, length = 100)
	private String addr1;

	@Column(name = "ADDR_2", length = 100)
	private String addr2;

	@Column(name = "ADDR_3", length = 100)
	private String addr3;

	@Column(name = "ADDR_4", length = 100)
	private String addr4;

	@Column(name = "ADDR_5", length = 100)
	private String addr5;

	@Column(name = "CITY_CD", nullable = false, length = 10)
	private String cityCd;

	@Column(name = "STATE_CD", nullable = false, length = 10)
	private String stateCd;

	@Column(name = "CNTRY_CD", nullable = false, length = 10)
	private String cntryCd;

	@Column(name = "ZIPCODE", length = 20)
	private String zipcode;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "CONTACT_NO", length = 50)
	private String contactNo;

	@Column(name = "FAX_NO", length = 50)
	private String faxNo;

	@Column(name = "DOC_REF_NO", length = 50)
	private String docRefNo;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "SYSTEM_TYPE")
	private String systemType;

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


	public Integer getBranchId() {
		return branchId;
	}


	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
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


	public String getUserGroupCode() {
		return BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	public String getBranchCode() {
		return BaseUtil.getStrUpperWithNull(branchCode);
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = BaseUtil.getStrUpperWithNull(branchCode);
	}


	public String getBranchName() {
		return BaseUtil.getStrUpperWithNull(branchName);
	}


	public void setBranchName(String branchName) {
		this.branchName = BaseUtil.getStrUpperWithNull(branchName);
	}


	public String getBranchDept() {
		return BaseUtil.getStrUpperWithNull(branchDept);
	}


	public void setBranchDept(String branchDept) {
		this.branchDept = BaseUtil.getStrUpperWithNull(branchDept);
	}


	public String getAddr1() {
		return BaseUtil.getStrUpperWithNull(addr1);
	}


	public void setAddr1(String addr1) {
		this.addr1 = BaseUtil.getStrUpperWithNull(addr1);
	}


	public String getAddr2() {
		return BaseUtil.getStrUpperWithNull(addr2);
	}


	public void setAddr2(String addr2) {
		this.addr2 = BaseUtil.getStrUpperWithNull(addr2);
	}


	public String getAddr3() {
		return BaseUtil.getStrUpperWithNull(addr3);
	}


	public void setAddr3(String addr3) {
		this.addr3 = BaseUtil.getStrUpperWithNull(addr3);
	}


	public String getAddr4() {
		return BaseUtil.getStrUpperWithNull(addr4);
	}


	public void setAddr4(String addr4) {
		this.addr4 = BaseUtil.getStrUpperWithNull(addr4);
	}


	public String getAddr5() {
		return BaseUtil.getStrUpperWithNull(addr5);
	}


	public void setAddr5(String addr5) {
		this.addr5 = BaseUtil.getStrUpperWithNull(addr5);
	}


	public String getCityCd() {
		return BaseUtil.getStrUpperWithNull(cityCd);
	}


	public void setCityCd(String cityCd) {
		this.cityCd = BaseUtil.getStrUpperWithNull(cityCd);
	}


	public String getStateCd() {
		return BaseUtil.getStrUpperWithNull(stateCd);
	}


	public void setStateCd(String stateCd) {
		this.stateCd = BaseUtil.getStrUpperWithNull(stateCd);
	}


	public String getCntryCd() {
		return BaseUtil.getStrUpperWithNull(cntryCd);
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = BaseUtil.getStrUpperWithNull(cntryCd);
	}


	public String getZipcode() {
		return BaseUtil.getStrUpperWithNull(zipcode);
	}


	public void setZipcode(String zipcode) {
		this.zipcode = BaseUtil.getStrUpperWithNull(zipcode);
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContactNo() {
		return BaseUtil.getStrUpperWithNull(contactNo);
	}


	public void setContactNo(String contactNo) {
		this.contactNo = BaseUtil.getStrUpperWithNull(contactNo);
	}


	/**
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return BaseUtil.getStrUpperWithNull(faxNo);
	}


	/**
	 * @param faxNo
	 *             the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = BaseUtil.getStrUpperWithNull(faxNo);
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
	 * @return the docRefNo
	 */
	public String getDocRefNo() {
		return docRefNo;
	}


	/**
	 * @param docRefNo
	 *             the docRefNo to set
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
