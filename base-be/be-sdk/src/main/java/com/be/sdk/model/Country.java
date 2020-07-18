/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Serializable {

	private static final long serialVersionUID = -6255425222614429634L;

	private String cntryCd;

	private String cntryDesc;

	private boolean cntryInd;

	private Timestamp createDt;

	private String createId;

	private String currency;

	private String intlCallCd;

	private String ntnltyDesc;
	
	private int mcProfTypeMtdtId;

	private Timestamp updateDt;

	private String updateId;

	private String state;


	public Country() {
	}


	public String getCntryCd() {
		return this.cntryCd;
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}


	public String getCntryDesc() {
		return this.cntryDesc;
	}


	public void setCntryDesc(String cntryDesc) {
		this.cntryDesc = cntryDesc;
	}


	public boolean getCntryInd() {
		return this.cntryInd;
	}


	public void setCntryInd(boolean cntryInd) {
		this.cntryInd = cntryInd;
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


	public String getCurrency() {
		return this.currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getIntlCallCd() {
		return this.intlCallCd;
	}


	public void setIntlCallCd(String intlCallCd) {
		this.intlCallCd = intlCallCd;
	}


	public String getNtnltyDesc() {
		return this.ntnltyDesc;
	}


	public void setNtnltyDesc(String ntnltyDesc) {
		this.ntnltyDesc = ntnltyDesc;
	}


	public int getMcProfTypeMtdtId() {
		return mcProfTypeMtdtId;
	}


	public void setMcProfTypeMtdtId(int mcProfTypeMtdtId) {
		this.mcProfTypeMtdtId = mcProfTypeMtdtId;
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


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

}