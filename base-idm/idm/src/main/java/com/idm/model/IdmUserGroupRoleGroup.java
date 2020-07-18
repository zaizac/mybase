package com.idm.model;


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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_USER_GROUP_ROLE_GROUP database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_USER_GROUP_ROLE_GROUP")
@JsonInclude(Include.NON_NULL)
@NamedQuery(name = "IdmUserGroupRoleGroup.findAll", query = "SELECT i FROM IdmUserGroupRoleGroup i")
public class IdmUserGroupRoleGroup extends AbstractEntity
		implements java.io.Serializable, IQfCriteria<IdmUserGroupRoleGroup> {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GROUP_ID", unique = true, nullable = false)
	private int groupId;

	@Column(name = "GROUP_CODE", nullable = false, length = 50)
	private String userGroupCode;

	@Column(name = "USER_GROUP_ROLE_PARENT", nullable = false, length = 100)
	private String parentRoleGroup;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_GROUP_ROLE_CHILD", referencedColumnName = "USER_GROUP_CODE")
	private IdmUserGroup userRoleGroup;

	@Column(name = "USER_TYPE_CODE", nullable = false, length = 5)
	private String userTypeCode;

	@Column(name = "MAX_NO_OF_USER", nullable = false)
	private Integer maxNoOfUser;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "CREATE_BY_PROF_ID")
	private Boolean createByProfId;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "CREATE_BY_BRANCH_ID")
	private Boolean createByBranchId;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "SYSTEM_CREATE")
	private Boolean systemCreate;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "COUNTRY_SEL")
	private Boolean selCountry;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "BRANCH_SEL")
	private Boolean selBranch;

	@Column(name = "CNTRY_CD", nullable = false, length = 10)
	private String cntryCd;

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

	@Transient
	private Integer maxNoOfUserCreated;


	public IdmUserGroupRoleGroup() {
		// DO NOTHING
	}


	public IdmUserGroupRoleGroup(IdmUserGroupRoleGroup idmUserGroupRoleGroup) {
		groupId = idmUserGroupRoleGroup.getGroupId();
		userGroupCode = idmUserGroupRoleGroup.getUserGroupCode();
		parentRoleGroup = idmUserGroupRoleGroup.getParentRoleGroup();
		userRoleGroup = idmUserGroupRoleGroup.getUserRoleGroup();
		userTypeCode = idmUserGroupRoleGroup.getUserTypeCode();
		maxNoOfUser = idmUserGroupRoleGroup.getMaxNoOfUser();
		createByProfId = idmUserGroupRoleGroup.isCreateByProfId();
		createByBranchId = idmUserGroupRoleGroup.isCreateByBranchId();
		systemCreate = idmUserGroupRoleGroup.isSystemCreate();
		selCountry = idmUserGroupRoleGroup.isSelCountry();
		selBranch = idmUserGroupRoleGroup.isSelBranch();
		cntryCd = idmUserGroupRoleGroup.getCntryCd();
		systemType = idmUserGroupRoleGroup.getSystemType();
		createId = idmUserGroupRoleGroup.getCreateId();
		createDt = idmUserGroupRoleGroup.getCreateDt();
		updateId = idmUserGroupRoleGroup.getUpdateId();
		updateDt = idmUserGroupRoleGroup.getUpdateDt();
		maxNoOfUserCreated = idmUserGroupRoleGroup.getMaxNoOfUserCreated();
		if (!BaseUtil.isObjNull(idmUserGroupRoleGroup.getUserRoleGroup())
				&& !BaseUtil.isListNullZero(idmUserGroupRoleGroup.getUserRoleGroup().getIdmProfileLst())) {
			maxNoOfUserCreated = idmUserGroupRoleGroup.getUserRoleGroup().getIdmProfileLst().size();
		}
	}


	public IdmUserGroupRoleGroup(IdmUserGroupRoleGroup idmUserGroupRoleGroup, IdmUserGroup userGroup) {
		groupId = idmUserGroupRoleGroup.getGroupId();
		userGroupCode = idmUserGroupRoleGroup.getUserGroupCode();
		parentRoleGroup = idmUserGroupRoleGroup.getParentRoleGroup();
		userRoleGroup = idmUserGroupRoleGroup.getUserRoleGroup();
		userTypeCode = idmUserGroupRoleGroup.getUserTypeCode();
		maxNoOfUser = idmUserGroupRoleGroup.getMaxNoOfUser();
		createByProfId = idmUserGroupRoleGroup.isCreateByProfId();
		createByBranchId = idmUserGroupRoleGroup.isCreateByBranchId();
		systemCreate = idmUserGroupRoleGroup.isSystemCreate();
		selCountry = idmUserGroupRoleGroup.isSelCountry();
		selBranch = idmUserGroupRoleGroup.isSelBranch();
		cntryCd = idmUserGroupRoleGroup.getCntryCd();
		systemType = idmUserGroupRoleGroup.getSystemType();
		createId = idmUserGroupRoleGroup.getCreateId();
		createDt = idmUserGroupRoleGroup.getCreateDt();
		updateId = idmUserGroupRoleGroup.getUpdateId();
		updateDt = idmUserGroupRoleGroup.getUpdateDt();
		if (!BaseUtil.isObjNull(userGroup) && !BaseUtil.isListNullZero(userGroup.getIdmProfileLst())) {
			maxNoOfUserCreated = userGroup.getIdmProfileLst().size();
		}
	}


	public int getGroupId() {
		return groupId;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	public String getUserGroupCode() {
		return userGroupCode;
	}


	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}


	public String getParentRoleGroup() {
		return parentRoleGroup;
	}


	public void setParentRoleGroup(String parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}


	public IdmUserGroup getUserRoleGroup() {
		return userRoleGroup;
	}


	public void setUserRoleGroup(IdmUserGroup userRoleGroup) {
		this.userRoleGroup = userRoleGroup;
	}


	public String getUserTypeCode() {
		return userTypeCode;
	}


	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}


	public Integer getMaxNoOfUser() {
		return maxNoOfUser;
	}


	public void setMaxNoOfUser(Integer maxNoOfUser) {
		this.maxNoOfUser = maxNoOfUser;
	}


	public Boolean isCreateByProfId() {
		return createByProfId;
	}


	public void setCreateByProfId(Boolean createByProfId) {
		this.createByProfId = createByProfId;
	}


	public Boolean isCreateByBranchId() {
		return createByBranchId;
	}


	public void setCreateByBranchId(Boolean createByBranchId) {
		this.createByBranchId = createByBranchId;
	}


	public Boolean isSystemCreate() {
		return systemCreate;
	}


	public void setSystemCreate(Boolean systemCreate) {
		this.systemCreate = systemCreate;
	}


	public Boolean isSelCountry() {
		return selCountry;
	}


	public void setSelCountry(Boolean selCountry) {
		this.selCountry = selCountry;
	}


	public Boolean isSelBranch() {
		return selBranch;
	}


	public void setSelBranch(Boolean selBranch) {
		this.selBranch = selBranch;
	}


	public String getCntryCd() {
		return BaseUtil.getStrUpperWithNull(cntryCd);
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = BaseUtil.getStrUpperWithNull(cntryCd);
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


	public Integer getMaxNoOfUserCreated() {
		return maxNoOfUserCreated;
	}


	public void setMaxNoOfUserCreated(Integer maxNoOfUserCreated) {
		this.maxNoOfUserCreated = maxNoOfUserCreated;
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