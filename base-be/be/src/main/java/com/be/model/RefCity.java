package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_CITY database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Entity
@Table(name = "REF_CITY")
public class RefCity extends AbstractEntity implements Serializable, IQfCriteria<RefCity> {

	private static final long serialVersionUID = 2270425912125487517L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CITY_ID")
	private Integer cityId;

	@Id
	@Column(name = "CITY_CD")
	private String cityCd;

	@Lob
	@Column(name = "CITY_DESC")
	private String cityDesc;
	
	// bi-directional many-to-one association to RefState
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_CD")
	private RefState state;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	public RefCity() {
		// DO NOTHING
	}

	public Integer getCityId() {
		return this.cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	public String getCityCd() {
		return this.cityCd;
	}


	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}


	public String getCityDesc() {
		return this.cityDesc;
	}


	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
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


	public RefState getState() {
		return state;
	}


	public void setState(RefState state) {
		this.state = state;
	}

}
