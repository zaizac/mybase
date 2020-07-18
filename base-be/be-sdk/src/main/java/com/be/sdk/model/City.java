package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Md Asif Aftab
 * @since 27th Feb 2018
 * @author hafidz.malik
 * @since 30th Oct 2019
 */

public class City implements Serializable {

	private static final long serialVersionUID = 607657392585361232L;

	private Integer cityId;

	private String cityCd;

	private String cityDesc;

	private Timestamp createDt;

	private String createId;

	private Timestamp updateDt;

	private String updateId;

	private Integer stateId;

	private State state;


	public City() {
		// DO NOTHING
	}


	public Integer getCityId() {
		return this.cityId;
	}


	public void setCityId(int cityId) {
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


	public Timestamp getCreateDt() {
		return this.createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getCreateId() {
		return this.createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getUpdateDt() {
		return this.updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getUpdateId() {
		return this.updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Integer getStateId() {
		return stateId;
	}


	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}


	public State getState() {
		return this.state;
	}


	public void setState(State state) {
		this.state = state;
	}

}
