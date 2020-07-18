package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
 * The persistent class for the IDM_USER_GROUP database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_USER_GROUP")
@NamedQuery(name = "IdmUserGroup.findAll", query = "SELECT i FROM IdmUserGroup i")
public class IdmUserGroup extends AbstractEntity implements Serializable, IQfCriteria<IdmUserGroup> {

	private static final long serialVersionUID = -566713785243470981L;

	@Id
	@Column(name = "USER_GROUP_CODE", unique = true, nullable = false, length = 50)
	private String userGroupCode;

	@Column(name = "USER_GROUP_DESC", nullable = false, length = 250)
	private String userGroupDesc;

	// bi-directional many-to-one association to IdmUserType
	@JsonIgnoreProperties("idmUserGroups")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TYPE_CODE", nullable = false)
	private IdmUserType userType;

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

	@JsonIgnoreProperties("userRoleGroup")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRoleGroup")
	private List<IdmProfile> idmProfileLst;

	@JsonIgnoreProperties({ "userGroup", "idmUserGroupRoles" })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userGroup")
	private List<IdmUserGroupRole> idmUserGroupRoles;


	public IdmUserGroup() {
		// DO NOTHING
	}


	public List<IdmProfile> getIdmProfileLst() {
		return idmProfileLst;
	}


	public void setIdmProfileLst(List<IdmProfile> idmProfileLst) {
		this.idmProfileLst = idmProfileLst;
	}


	public String getUserGroupCode() {
		return BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = BaseUtil.getStrUpperWithNull(userGroupCode);
	}


	public String getUserGroupDesc() {
		return BaseUtil.getStrUpperWithNull(userGroupDesc);
	}


	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = BaseUtil.getStrUpperWithNull(userGroupDesc);
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


	public List<IdmUserGroupRole> getIdmUserGroupRoles() {
		return idmUserGroupRoles;
	}


	public void setIdmUserGroupRoles(List<IdmUserGroupRole> idmUserGroupRoles) {
		this.idmUserGroupRoles = idmUserGroupRoles;
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
