/**
 * Copyright 2019
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
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_STATUS database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Entity
@Table(name = "REF_STATUS")
public class RefStatus extends AbstractEntity implements Serializable, IQfCriteria<RefStatus> {

	private static final long serialVersionUID = 3362801765553782305L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STATUS_ID")
	private Integer statusId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private Boolean active;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Lob
	@Column(name = "STATUS_DESC")
	private String statusDesc;

	@Column(name = "STATUS_TYPE")
	private String statusType;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;


	public RefStatus() {
	}


	public Integer getStatusId() {
		return this.statusId;
	}


	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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


	public Boolean isActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getStatusCd() {
		return this.statusCd;
	}


	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	public String getStatusDesc() {
		return this.statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public String getStatusType() {
		return this.statusType;
	}


	public void setStatusType(String statusType) {
		this.statusType = statusType;
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
