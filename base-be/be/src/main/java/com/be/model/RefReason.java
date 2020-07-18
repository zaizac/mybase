/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.model;


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

import org.hibernate.annotations.Type;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_REASON database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Entity
@Table(name = "REF_REASON")
@NamedQuery(name = "RefReason.findAll", query = "SELECT r FROM RefReason r")
public class RefReason extends AbstractEntity implements Serializable, IQfCriteria<RefReason> {

	private static final long serialVersionUID = 7443038187655424885L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REASON_ID")
	private Integer reasonId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "REASON_CD")
	private String reasonCd;

	@Lob
	@Column(name = "REASON_DESC")
	private String reasonDesc;

	@Column(name = "REASON_TYPE")
	private String reasonType;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;


	public RefReason() {
	}


	public Integer getReasonId() {
		return this.reasonId;
	}


	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}


	@Override
	public Timestamp getCreateDt() {
		return this.createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getCreateId() {
		return this.createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public String getReasonCd() {
		return this.reasonCd;
	}


	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}


	public String getReasonDesc() {
		return this.reasonDesc;
	}


	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}


	public String getReasonType() {
		return this.reasonType;
	}


	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public Timestamp getUpdateDt() {
		return this.updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	@Override
	public String getUpdateId() {
		return this.updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}
