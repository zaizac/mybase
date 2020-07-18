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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_ROLE_MENU database table.
 *
 */
@Entity
@Table(name = "IDM_ROLE_MENU")
@NamedQuery(name = "IdmRoleMenu.findAll", query = "SELECT i FROM IdmRoleMenu i")
public class IdmRoleMenu extends AbstractEntity implements Serializable, IQfCriteria<IdmRoleMenu> {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_MENU_ID", unique = true, nullable = false)
	private Integer roleMenuId;

	// bi-directional many-to-one association to IdmRole
	@JsonProperty("role")
	@JsonIgnoreProperties("idmRoleMenus")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_CODE")
	private IdmRole idmRole;

	// bi-directional many-to-one association to IdmMenu
	@JsonProperty("menu")
	@JsonIgnoreProperties("idmRoleMenus")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MENU_CODE")
	private IdmMenu idmMenu;
	
	// bi-directional many-to-one association to IdmPortalType
	@JsonProperty("portalType")
	@JsonIgnoreProperties("idmRoleMenus")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PORTAL_TYPE_CODE")
	private IdmPortalType idmPortalType;


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


	public IdmRoleMenu() {
		// DO NOTHING
	}


	public Integer getRoleMenuId() {
		return roleMenuId;
	}


	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}


	public IdmRole getIdmRole() {
		return idmRole;
	}


	public void setIdmRole(IdmRole idmRole) {
		this.idmRole = idmRole;
	}


	public IdmMenu getIdmMenu() {
		return idmMenu;
	}


	public void setIdmMenu(IdmMenu idmMenu) {
		this.idmMenu = idmMenu;
	}


	public IdmPortalType getIdmPortalType() {
		return idmPortalType;
	}


	public void setIdmPortalType(IdmPortalType idmPortalType) {
		this.idmPortalType = idmPortalType;
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