/**
 * Copyright 2019
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Ilhomjon
 * @since Feb 22, 2018
 */
public class State implements Serializable {

	private static final long serialVersionUID = -6234620539956436222L;

	private Integer stateId;

	private Timestamp createDt;

	private String createId;

	private String stateCd;

	private String stateDesc;

	private Timestamp updateDt;

	private String updateId;

	private Country country;


	public State() {
		// DO NOTHING
	}


	public Integer getStateId() {
		return this.stateId;
	}


	public void setStateId(int stateId) {
		this.stateId = stateId;
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


	public String getStateCd() {
		return this.stateCd;
	}


	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}


	public String getStateDesc() {
		return this.stateDesc;
	}


	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
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


	public Country getCountry() {
		return this.country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}
}