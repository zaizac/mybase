package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The persistent class for the IDM_CONFIG database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_CONFIG")
@NamedQuery(name = "IdmConfig.findAll", query = "SELECT i FROM IdmConfig i")
public class IdmConfig extends AbstractEntity implements Serializable, IQfCriteria<IdmConfig> {

	private static final long serialVersionUID = 5598797065888769861L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONFIG_ID", unique = true, nullable = false)
	private int configId;

	@Column(name = "CONFIG_CODE", nullable = false, length = 255)
	private String configCode;

	@Column(name = "CONFIG_DESC", length = 255)
	private String configDesc;

	@Column(name = "CONFIG_VAL", length = 255)
	private String configVal;

	@Column(name = "CREATE_ID", length = 100)
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "UPDATE_ID", length = 100)
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;


	public IdmConfig() {
		// DO NOTHING
	}


	public int getConfigId() {
		return configId;
	}


	public void setConfigId(int configId) {
		this.configId = configId;
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