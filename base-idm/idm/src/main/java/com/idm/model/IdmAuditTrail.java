package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_AUDIT_TRAIL database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_AUDIT_TRAIL")
@NamedQuery(name = "IdmAuditTrail.findAll", query = "SELECT i FROM IdmAuditTrail i")
public class IdmAuditTrail extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3186445980890420147L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUDIT_ID", unique = true, nullable = false)
	private Integer auditId;

	@Column(name = "MESSAGE_ID", length = 50)
	private String messageId;

	@Column(name = "BUILD_VERSION", length = 20)
	private String buildVersion;

	@Column(name = "TRACE_ID", length = 45)
	private String traceId;

	@Column(name = "SESSION_ID", length = 45)
	private String sessionId;

	@Column(name = "CLIENT_ID", length = 255)
	private String clientId;

	@Column(name = "TRXN_NO", length = 255)
	private String trxnNo;

	@Lob
	@Column(name = "TRXN_URL")
	private String trxnUrl;

	@Lob
	@Column(name = "REQUEST")
	private String request;

	@Lob
	@Column(name = "RESPONSE")
	private String response;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "CREATE_ID", length = 100)
	private String createId;


	public IdmAuditTrail() {
		// DO NOTHING
	}


	public Integer getAuditId() {
		return auditId;
	}


	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
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
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
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