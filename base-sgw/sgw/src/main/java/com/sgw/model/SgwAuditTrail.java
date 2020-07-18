/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Entity
@Table(name = "SGW_AUDIT_TRAIL")
public class SgwAuditTrail implements Serializable {

	private static final long serialVersionUID = 1754232937948998695L;

	@Id
	@Column(name = "AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

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

	@Column(name = "CREATE_DT")
	private Timestamp createDt;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}
}