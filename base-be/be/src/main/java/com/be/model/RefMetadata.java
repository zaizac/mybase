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
 * The persistent class for the REF_METADATA database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Entity
@Table(name = "REF_METADATA")
@NamedQuery(name = "RefMetadata.findAll", query = "SELECT r FROM RefMetadata r")
public class RefMetadata extends AbstractEntity implements Serializable, IQfCriteria<RefMetadata> {

	private static final long serialVersionUID = -6194194859975093735L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MTDT_ID")
	private Integer mtdtId;

	@Column(name = "MTDT_TYPE")
	private String mtdtType;

	@Column(name = "MTDT_CD")
	private String mtdtCd;

	@Lob
	@Column(name = "MTDT_DESC")
	private String mtdtDesc;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;


	public RefMetadata() {
	}


	public Integer getMtdtId() {
		return this.mtdtId;
	}


	public void setMtdtId(Integer mtdtId) {
		this.mtdtId = mtdtId;
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


	public String getMtdtCd() {
		return this.mtdtCd;
	}


	public void setMtdtCd(String mtdtCd) {
		this.mtdtCd = mtdtCd;
	}


	public String getMtdtDesc() {
		return this.mtdtDesc;
	}


	public void setMtdtDesc(String mtdtDesc) {
		this.mtdtDesc = mtdtDesc;
	}


	public String getMtdtType() {
		return this.mtdtType;
	}


	public void setMtdtType(String mtdtType) {
		this.mtdtType = mtdtType;
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
