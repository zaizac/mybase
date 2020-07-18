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
@Table(name = "SGW_AUTHORIZATION")
public class SgwAuthorization implements Serializable {

	private static final long serialVersionUID = 4516912818570316614L;

	@Id
	@Column(name = "AUTH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "TRXN_NO")
	private String trxnNo;

	@Column(name = "STATUS")
	private String status;

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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

}