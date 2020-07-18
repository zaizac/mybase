package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * The persistent class for the IDM_AUDIT_ACTION database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_AUDIT_ACTION")
//@NamedQuery(name = "IdmAuditAction.findAll", query = "SELECT i FROM IdmAuditAction i")
public class IdmAuditAction extends AbstractEntity implements Serializable, IQfCriteria<IdmAuditAction> {

	private static final long serialVersionUID = -291044852058915218L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUDIT_ID", unique = true, nullable = false)
	private Integer auditId;

	@Column(name = "PORTAL", length = 5)
	private String portal;

	@Column(name = "PAGE", length = 250)
	private String page;

	@Column(name = "USER_ID", nullable = false, length = 100)
	private String userId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "AUDIT_DT", nullable = false)
	private Timestamp auditDt;

	@Column(name = "ACTION", nullable = false, length = 255)
	private String auditInfo;

	@Lob
	@Column(name = "LOOKUP_DATA")
	private String lookupData;


	public IdmAuditAction() {
		// DO NOTHING
	}


	/**
	 * @return the auditId
	 */
	public Integer getAuditId() {
		return auditId;
	}


	/**
	 * @param auditId
	 *             the auditId to set
	 */
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}


	/**
	 * @return the portal
	 */
	public String getPortal() {
		return portal;
	}


	/**
	 * @param portal
	 *             the portal to set
	 */
	public void setPortal(String portal) {
		this.portal = portal;
	}


	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}


	/**
	 * @param page
	 *             the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId
	 *             the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the auditDt
	 */
	public Timestamp getAuditDt() {
		return auditDt;
	}


	/**
	 * @param auditDt
	 *             the auditDt to set
	 */
	public void setAuditDt(Timestamp auditDt) {
		this.auditDt = auditDt;
	}


	/**
	 * @return the auditInfo
	 */
	public String getAuditInfo() {
		return auditInfo;
	}


	/**
	 * @param auditInfo
	 *             the auditInfo to set
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}


	/**
	 * @return the lookupData
	 */
	public String getLookupData() {
		return lookupData;
	}


	/**
	 * @param lookupData
	 *             the lookupData to set
	 */
	public void setLookupData(String lookupData) {
		this.lookupData = lookupData;
	}


	@Override
	public String getCreateId() {
		return null;
	}


	@Override
	public void setCreateId(String crtUsrId) {
		// DO NOTHING
	}


	@Override
	public Timestamp getCreateDt() {
		return null;
	}


	@Override
	public void setCreateDt(Timestamp crtTs) {
		// DO NOTHING
	}


	@Override
	public String getUpdateId() {
		return null;
	}


	@Override
	public void setUpdateId(String modUsrId) {
		// DO NOTHING
	}


	@Override
	public Timestamp getUpdateDt() {
		return null;
	}


	@Override
	public void setUpdateDt(Timestamp modTs) {
		// DO NOTHING
	}

}