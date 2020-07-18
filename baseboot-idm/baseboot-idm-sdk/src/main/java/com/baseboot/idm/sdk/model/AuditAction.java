/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class AuditAction implements Serializable {

	private static final long serialVersionUID = -5014948914185420246L;

	private int id;

	private String userId;

	private String auditInfo;

	private Timestamp auditDt;

	private String portal;

	private String page;

	private String lookupInfo;


	public AuditAction() {
		// Do Nothing
	}


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

}