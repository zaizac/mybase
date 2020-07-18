package com.idm.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.idm.util.LangDescConverter;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_ROLE database table.
 *
 */
@Entity
@Table(name = "IDM_ROLE")
@NamedQuery(name = "IdmRole.findAll", query = "SELECT i FROM IdmRole i")
public class IdmRole extends AbstractEntity implements java.io.Serializable, IQfCriteria<IdmRole> {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "ROLE_CODE", unique = true, nullable = false, length = 20)
	private String roleCode;

	@Column(name = "ROLE_DESC", length = 45)
	@Convert(converter = LangDescConverter.class)
	private LangDesc roleDesc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTAL_TYPE_CODE", nullable = false)
	private IdmPortalType portalType;

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

	@JsonIgnoreProperties("idmRole")
	@OneToMany(mappedBy = "idmRole", fetch = FetchType.LAZY)
	private List<IdmRoleMenu> idmRoleMenus;

	@JsonIgnoreProperties({ "userRole", "idmUserGroupRoles" })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private List<IdmUserGroupRole> idmUserGroupRoles;
	
	//@JsonIgnoreProperties({ "userGroup", "idmUserGroupRoles" })
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userGroup")
	//private List<IdmUserGroupRole> idmUserGroupRoles;


	public IdmRole() {
		// DO NOTHING
	}


	public String getRoleCode() {
		return BaseUtil.getStrUpperWithNull(roleCode);
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = BaseUtil.getStrUpperWithNull(roleCode);
	}


	public LangDesc getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(LangDesc roleDesc) {
		this.roleDesc = roleDesc;
	}


	public IdmPortalType getPortalType() {
		return portalType;
	}


	public void setPortalType(IdmPortalType portalType) {
		this.portalType = portalType;
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


	public List<IdmRoleMenu> getIdmRoleMenus() {
		return idmRoleMenus;
	}


	public void setIdmRoleMenus(List<IdmRoleMenu> idmRoleMenus) {
		this.idmRoleMenus = idmRoleMenus;
	}


	public List<IdmUserGroupRole> getIdmUserGroupRoles() {
		return idmUserGroupRoles;
	}


	public void setIdmUserGroupRoles(List<IdmUserGroupRole> idmUserGroupRoles) {
		this.idmUserGroupRoles = idmUserGroupRoles;
	}

}