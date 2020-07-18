/**
 * Copyright 2019.
 */
package com.idm.sdk.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;

/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class AuditAction implements Serializable {

	private static final long serialVersionUID = -5014948914185420246L;

	private Integer auditId;

	private String userId;

	private String auditInfo;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp auditDt;

	private String portal;

	private String page;

	private String lookupData;

	/**
	 * @return the auditId
	 */
	public Integer getAuditId() {
		return auditId;
	}

	/**
	 * @param auditId the auditId to set
	 */
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the auditInfo
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * @param auditInfo the auditInfo to set
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
	 * @return the auditDt
	 */
	public Timestamp getAuditDt() {
		return auditDt;
	}

	/**
	 * @param auditDt the auditDt to set
	 */
	public void setAuditDt(Timestamp auditDt) {
		this.auditDt = auditDt;
	}

	/**
	 * @return the portal
	 */
	public String getPortal() {
		return portal;
	}

	/**
	 * @param portal the portal to set
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
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the lookupData
	 */
	public String getLookupData() {
		return lookupData;
	}

	/**
	 * @param lookupData the lookupData to set
	 */
	public void setLookupData(String lookupData) {
		this.lookupData = lookupData;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}