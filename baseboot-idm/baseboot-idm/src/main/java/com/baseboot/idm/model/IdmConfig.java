/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Entity
@Table(name = "IDM_CONFIG")
@JsonInclude(Include.NON_NULL)
public class IdmConfig implements Serializable {

	private static final long serialVersionUID = 5598797065888769861L;

	@Id
	@Column(name = "CONFIG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


	public IdmConfig() {
		// TODO Auto-generated constructor stub
	}


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


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}

}
