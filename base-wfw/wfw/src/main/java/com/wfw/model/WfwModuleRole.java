/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * @author mary.jane
 *
 */
@Entity
@Table(name = "WFW_MODULE_ROLE")
public class WfwModuleRole extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 587914620884065448L;

	@Id
	@Column(name = "MODULE_ROLE_ID")
	private String id;

	@Column(name = "MODULE_ID")
	private String moduleId;

	@Column(name = "ROLE_CODE")
	private String roleCode;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwModuleRole() {
		// DO NOTHING
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

}