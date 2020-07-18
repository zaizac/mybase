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
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author mary.jane
 *
 */
public class UserGroupRoleGroup implements Serializable, IQfCriteria<UserGroupRoleGroup> {

	private static final long serialVersionUID = -7272523887037532380L;

	private int groupId;

	private String userGroupCode;

	private String parentRoleGroup;

	private UserGroup userRoleGroup;

	private String userTypeCode;

	private int maxNoOfUser;

	private boolean createByProfId;

	private boolean createByBranchId;

	private boolean systemCreate;

	private boolean selCountry;

	private boolean selBranch;

	private String cntryCd;

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


	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}


	/**
	 * @param groupId
	 *             the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
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
	 * @return the userTypeCode
	 */
	public String getUserTypeCode() {
		return userTypeCode;
	}


	/**
	 * @param userTypeCode
	 *             the userTypeCode to set
	 */
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}


	/**
	 * @return the maxNoOfUser
	 */
	public int getMaxNoOfUser() {
		return maxNoOfUser;
	}


	/**
	 * @param maxNoOfUser
	 *             the maxNoOfUser to set
	 */
	public void setMaxNoOfUser(int maxNoOfUser) {
		this.maxNoOfUser = maxNoOfUser;
	}


	/**
	 * @return the createByProfId
	 */
	public boolean isCreateByProfId() {
		return createByProfId;
	}


	/**
	 * @param createByProfId
	 *             the createByProfId to set
	 */
	public void setCreateByProfId(boolean createByProfId) {
		this.createByProfId = createByProfId;
	}


	/**
	 * @return the createByBranchId
	 */
	public boolean isCreateByBranchId() {
		return createByBranchId;
	}


	/**
	 * @param createByBranchId
	 *             the createByBranchId to set
	 */
	public void setCreateByBranchId(boolean createByBranchId) {
		this.createByBranchId = createByBranchId;
	}


	/**
	 * @return the systemCreate
	 */
	public boolean isSystemCreate() {
		return systemCreate;
	}


	/**
	 * @param systemCreate
	 *             the systemCreate to set
	 */
	public void setSystemCreate(boolean systemCreate) {
		this.systemCreate = systemCreate;
	}


	/**
	 * @return the selCountry
	 */
	public boolean isSelCountry() {
		return selCountry;
	}


	/**
	 * @param selCountry
	 *             the selCountry to set
	 */
	public void setSelCountry(boolean selCountry) {
		this.selCountry = selCountry;
	}


	/**
	 * @return the selBranch
	 */
	public boolean isSelBranch() {
		return selBranch;
	}


	/**
	 * @param selBranch
	 *             the selBranch to set
	 */
	public void setSelBranch(boolean selBranch) {
		this.selBranch = selBranch;
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

}
