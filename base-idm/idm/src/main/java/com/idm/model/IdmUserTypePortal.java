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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_USER_TYPE_PORTAL database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_USER_TYPE_PORTAL")
@NamedQuery(name = "IdmUserTypePortal.findAll", query = "SELECT i FROM IdmUserTypePortal i")
public class IdmUserTypePortal extends AbstractEntity implements Serializable, IQfCriteria<IdmUserTypePortal> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_PORTAL_ID", unique = true, nullable = false)
	private Integer userPortalId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TYPE_CODE", nullable = false)
	private IdmUserType userType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PORTAL_TYPE_CODE", nullable = false)
	private IdmPortalType portalType;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "CREATE_ID", length = 100)
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID", length = 100)
	private String updateId;


	public IdmUserTypePortal() {
		// DO NOTHING
	}


	public IdmUserTypePortal(String userType, String portalType) {
		this.portalType = new IdmPortalType(portalType);
		this.userType = new IdmUserType(userType);
	}


	/**
	 * @return the userPortalId
	 */
	public Integer getUserPortalId() {
		return userPortalId;
	}


	/**
	 * @param userPortalId
	 *             the userPortalId to set
	 */
	public void setUserPortalId(Integer userPortalId) {
		this.userPortalId = userPortalId;
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


	/**
	 * @return the portalType
	 */
	public IdmPortalType getPortalType() {
		return portalType;
	}


	/**
	 * @param portalType
	 *             the portalType to set
	 */
	public void setPortalType(IdmPortalType portalType) {
		this.portalType = portalType;
	}


	/**
	 * @return the createDt
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the createId
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the updateDt
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the updateId
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}