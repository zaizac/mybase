/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author mary.jane
 *
 */
public class Metadata implements Serializable, IQfCriteria<Metadata> {

	private static final long serialVersionUID = 2651175081744146390L;

	private Integer mtdtId;

	private Timestamp createDt;

	private String createId;

	private String mtdtCd;

	private String mtdtDesc;

	private String mtdtType;

	private boolean status;

	private Timestamp updateDt;

	private String updateId;


	public Integer getMtdtId() {
		return mtdtId;
	}


	public void setMtdtId(Integer mtdtId) {
		this.mtdtId = mtdtId;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public String getMtdtCd() {
		return mtdtCd;
	}


	public void setMtdtCd(String mtdtCd) {
		this.mtdtCd = mtdtCd;
	}


	public String getMtdtDesc() {
		return mtdtDesc;
	}


	public void setMtdtDesc(String mtdtDesc) {
		this.mtdtDesc = mtdtDesc;
	}


	public String getMtdtType() {
		return mtdtType;
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


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}
