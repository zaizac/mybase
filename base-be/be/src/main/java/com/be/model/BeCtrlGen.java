/**
 * Copyright 2019
 */
package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.be.core.AbstractEntity;


/**
 * The persistent class for the BE_CTRL_GEN database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Entity
@Table(name = "BE_CTRL_GEN")
public class BeCtrlGen extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -4164629961600718046L;

	@EmbeddedId
	private BeCtrlGenPK beCtrlGenPK;

	@Column(name = "TRXN_ID", nullable = false)
	private Integer trxnId;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;


	public Integer getTrxnId() {
		return trxnId;
	}


	public void setTrxnId(Integer trxnId) {
		this.trxnId = trxnId;
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
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public BeCtrlGenPK getBeCtrlGenPK() {
		return beCtrlGenPK;
	}


	public void setBeCtrlGenPK(BeCtrlGenPK beCtrlGenPK) {
		this.beCtrlGenPK = beCtrlGenPK;
	}

}
