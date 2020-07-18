package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_STATE database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Entity
@Table(name = "REF_STATE", indexes = {@Index(columnList = "STATE_CD", name = "stateCd", unique = true)})
public class RefState extends AbstractEntity implements Serializable, IQfCriteria<RefState> {

	private static final long serialVersionUID = -6510355585641387606L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STATE_ID")
	private Integer stateId;
	
	@Id
	@Column(name = "STATE_CD")
	private String stateCd;

	@Column(name = "STATE_DESC")
	private String stateDesc;

	// bi-directional many-to-one association to RefCountry
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CNTRY_CD", referencedColumnName = "CNTRY_CD")
	private RefCountry country;
		
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;	

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;


	public RefState() {
		// DO NOTHING
	}


	public Integer getStateId() {
		return stateId;
	}


	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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


	public String getStateCd() {
		return stateCd;
	}


	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}


	public String getStateDesc() {
		return stateDesc;
	}


	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public RefCountry getCountry() {
		return country;
	}


	public void setCountry(RefCountry country) {
		this.country = country;
	}

}