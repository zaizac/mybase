/**
 * Copyright 2019
 */
package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class Status implements Serializable, IQfCriteria<Status> {

	private static final long serialVersionUID = 607657392585361232L;

	private Integer statusId;

	private Timestamp createDt;

	private String createId;

	private Boolean active;

	private String statusCd;

	private String statusDesc;

	private String statusType;

	private Timestamp updateDt;

	private String updateId;


	public Integer getStatusId() {
		return statusId;
	}


	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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


	public Boolean isActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getStatusCd() {
		return statusCd;
	}


	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public String getStatusType() {
		return statusType;
	}


	public void setStatusType(String statusType) {
		this.statusType = statusType;
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