/**
 * Copyright 2019. 
 */
package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = 5302969888427091997L;

	private Integer id;

	private PortalType portalType;

	private String roleCode;

	private LangDesc roleDesc;

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

	private String selected;
	
	private List<RoleMenu> roleMenus;
	
	private List<UserGroupRole> idmUserGroupRoles;


	public UserRole() {
		// DO NOTHING
	}


	public UserRole(String roleCode, String roleDesc) {
		this.roleCode = roleCode;
		LangDesc roleLangDesc = new LangDesc();
		roleLangDesc.setEn(roleDesc);
		this.roleDesc = roleLangDesc;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id
	 *             the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}


	/**
	 * @param roleCode
	 *             the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	/**
	 * @return the roleDesc
	 */
	public LangDesc getRoleDesc() {
		return roleDesc;
	}


	/**
	 * @param roleDesc
	 *             the roleDesc to set
	 */
	public void setRoleDesc(LangDesc roleDesc) {
		this.roleDesc = roleDesc;
	}


	public PortalType getPortalType() {
		return portalType;
	}


	public void setPortalType(PortalType portalType) {
		this.portalType = portalType;
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


	/**
	 * @return the selected
	 */
	public String getSelected() {
		return selected;
	}


	/**
	 * @param selected
	 *             the selected to set
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}


	public List<RoleMenu> getRoleMenus() {
		return roleMenus;
	}


	public void setRoleMenus(List<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}


	public List<UserGroupRole> getIdmUserGroupRoles() {
		return idmUserGroupRoles;
	}


	public void setIdmUserGroupRoles(List<UserGroupRole> idmUserGroupRoles) {
		this.idmUserGroupRoles = idmUserGroupRoles;
	}

}