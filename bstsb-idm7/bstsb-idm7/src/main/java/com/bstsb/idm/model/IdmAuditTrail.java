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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_AUDIT_TRAIL")
@JsonInclude(Include.NON_NULL)
public class IdmAuditTrail extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3186445980890420147L;

	@Id
	@Column(name = "AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "MESSAGE_ID")
	private String messageId;

	@Column(name = "BUILD_VERSION")
	private String buildVersion;

	@Column(name = "TRACE_ID")
	private String traceId;

	@Column(name = "SESSION_ID")
	private String sessionId;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "TRXN_NO")
	private String trxnNo;

	@Column(name = "TRXN_URL")
	private String trxnUrl;

	@Column(name = "REQUEST")
	private String request;

	@Column(name = "RESPONSE")
	private String response;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;


	public IdmAuditTrail() {
		// DO NOTHING
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMessageId() {
		return messageId;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String getBuildVersion() {
		return buildVersion;
	}


	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}


	public String getTraceId() {
		return traceId;
	}


	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public String getTrxnNo() {
		return trxnNo;
	}


	public void setTrxnNo(String trxnNo) {
		this.trxnNo = trxnNo;
	}


	public String getTrxnUrl() {
		return trxnUrl;
	}


	public void setTrxnUrl(String trxnUrl) {
		this.trxnUrl = trxnUrl;
	}


	public String getRequest() {
		return request;
	}


	public void setRequest(String request) {
		this.request = request;
	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
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