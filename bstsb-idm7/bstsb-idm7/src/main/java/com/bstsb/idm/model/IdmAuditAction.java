/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstsb.idm.core.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_AUDIT_ACTION")
public class IdmAuditAction extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -291044852058915218L;

	@Id
	@Column(name = "AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "PORTAL")
	private String portal;

	@Column(name = "PAGE")
	private String page;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ACTION")
	private String auditInfo;

	@Column(name = "LOOKUP_DATA")
	private String lookupInfo;

	@Column(name = "AUDIT_DT")
	private Timestamp auditDt;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAuditInfo() {
		return auditInfo;
	}


	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}


	public Timestamp getAuditDt() {
		return auditDt;
	}


	public void setAuditDt(Timestamp auditDt) {
		this.auditDt = auditDt;
	}


	public String getPortal() {
		return portal;
	}


	public void setPortal(String portal) {
		this.portal = portal;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getLookupInfo() {
		return lookupInfo;
	}


	public void setLookupInfo(String lookupInfo) {
		this.lookupInfo = lookupInfo;
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