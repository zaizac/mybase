/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baseboot.notify.core.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "NOT_CONFIG")
public class NotConfig extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6016032592885161865L;

	@Id
	@Column(name = "CONFIG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CONFIG_CODE")
	private String configCode;

	@Column(name = "CONFIG_DESC")
	private String configDesc;

	@Column(name = "CONFIG_VAL")
	private String configVal;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getConfigCode() {
		return configCode;
	}


	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}


	public String getConfigDesc() {
		return configDesc;
	}


	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}


	public String getConfigVal() {
		return configVal;
	}


	public void setConfigVal(String configVal) {
		this.configVal = configVal;
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