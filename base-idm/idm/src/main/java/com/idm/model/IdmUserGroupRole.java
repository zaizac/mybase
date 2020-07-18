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
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_USER_GROUP_ROLE")
@NamedQuery(name = "IdmUserGroupRole.findAll", query = "SELECT i FROM IdmUserGroupRole i")
public class IdmUserGroupRole extends AbstractEntity implements java.io.Serializable, IQfCriteria<IdmUserGroupRole> {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "USER_GROUP_ROLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonProperty("userGroup")
	@JsonIgnoreProperties("idmUserGroupRoles")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_GROUP_ROLE_CODE", referencedColumnName = "USER_GROUP_CODE")
	private IdmUserGroup userGroup;

	@JsonProperty("role")
	@JsonIgnoreProperties("idmUserGroupRoles")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_CODE", referencedColumnName = "ROLE_CODE")
	private IdmRole role;

	@Column(name = "CREATE_ID")
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public IdmUserGroupRole() {
		// DO NOTHING
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public IdmRole getRole() {
		return role;
	}


	public void setRole(IdmRole role) {
		this.role = role;
	}


	public IdmUserGroup getUserGroup() {
		return userGroup;
	}


	public void setUserGroup(IdmUserGroup userGroup) {
		this.userGroup = userGroup;
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