/**
 * Copyright 2019. 
 */
package com.notify.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.notify.core.AbstractEntity;



/**
 * The Class NotAuditTrail.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "NOT_AUDIT_TRAIL")
@JsonInclude(Include.NON_NULL)
public class NotAuditTrail extends AbstractEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3186445980890420147L;

	/** The id. */
	@Id
	@Column(name = "AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** The message id. */
	@Column(name = "MESSAGE_ID")
	private String messageId;

	/** The build version. */
	@Column(name = "BUILD_VERSION")
	private String buildVersion;

	/** The trace id. */
	@Column(name = "TRACE_ID")
	private String traceId;

	/** The session id. */
	@Column(name = "SESSION_ID")
	private String sessionId;

	/** The client id. */
	@Column(name = "CLIENT_ID")
	private String clientId;

	/** The trxn no. */
	@Column(name = "TRXN_NO")
	private String trxnNo;

	/** The trxn url. */
	@Column(name = "TRXN_URL")
	private String trxnUrl;

	/** The request. */
	@Column(name = "REQUEST")
	private String request;

	/** The response. */
	@Column(name = "RESPONSE")
	private String response;

	/** The create id. */
	@Column(name = "CREATE_ID")
	private String createId;

	/** The create dt. */
	@Column(name = "CREATE_DT")
	private Timestamp createDt;


	/**
	 * Instantiates a new not audit trail.
	 */
	public NotAuditTrail() {
		// DO NOTHING
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public String getMessageId() {
		return messageId;
	}


	/**
	 * Sets the message id.
	 *
	 * @param messageId the new message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	/**
	 * Gets the builds the version.
	 *
	 * @return the builds the version
	 */
	public String getBuildVersion() {
		return buildVersion;
	}


	/**
	 * Sets the builds the version.
	 *
	 * @param buildVersion the new builds the version
	 */
	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}


	/**
	 * Gets the trace id.
	 *
	 * @return the trace id
	 */
	public String getTraceId() {
		return traceId;
	}


	/**
	 * Sets the trace id.
	 *
	 * @param traceId the new trace id
	 */
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}


	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		return sessionId;
	}


	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	/**
	 * Gets the client id.
	 *
	 * @return the client id
	 */
	public String getClientId() {
		return clientId;
	}


	/**
	 * Sets the client id.
	 *
	 * @param clientId the new client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	/**
	 * Gets the trxn no.
	 *
	 * @return the trxn no
	 */
	public String getTrxnNo() {
		return trxnNo;
	}


	/**
	 * Sets the trxn no.
	 *
	 * @param trxnNo the new trxn no
	 */
	public void setTrxnNo(String trxnNo) {
		this.trxnNo = trxnNo;
	}


	/**
	 * Gets the trxn url.
	 *
	 * @return the trxn url
	 */
	public String getTrxnUrl() {
		return trxnUrl;
	}


	/**
	 * Sets the trxn url.
	 *
	 * @param trxnUrl the new trxn url
	 */
	public void setTrxnUrl(String trxnUrl) {
		this.trxnUrl = trxnUrl;
	}


	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}


	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(String request) {
		this.request = request;
	}


	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}


	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(String response) {
		this.response = response;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getCreateId()
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateId(java.lang.String)
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getCreateDt()
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateDt(java.sql.Timestamp)
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateId()
	 */
	@Override
	public String getUpdateId() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateId(java.lang.String)
	 */
	@Override
	public void setUpdateId(String modUsrId) {
		// DO NOTHING
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateDt()
	 */
	@Override
	public Timestamp getUpdateDt() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateDt(java.sql.Timestamp)
	 */
	@Override
	public void setUpdateDt(Timestamp modTs) {
		// DO NOTHING
	}

}