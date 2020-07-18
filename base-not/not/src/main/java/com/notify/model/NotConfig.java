/**
 * Copyright 2019. 
 */
package com.notify.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.notify.core.AbstractEntity;



/**
 * The Class NotConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "NOT_CONFIG")
public class NotConfig extends AbstractEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6016032592885161865L;

	/** The id. */
	@Id
	@Column(name = "CONFIG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/** The config code. */
	@Column(name = "CONFIG_CODE")
	private String configCode;

	/** The config desc. */
	@Column(name = "CONFIG_DESC")
	private String configDesc;

	/** The config val. */
	@Column(name = "CONFIG_VAL")
	private String configVal;

	/** The create id. */
	@Column(name = "CREATE_ID")
	private String createId;

	/** The create dt. */
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	/** The update id. */
	@Column(name = "UPDATE_ID")
	private String updateId;

	/** The update dt. */
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Gets the config code.
	 *
	 * @return the config code
	 */
	public String getConfigCode() {
		return configCode;
	}


	/**
	 * Sets the config code.
	 *
	 * @param configCode the new config code
	 */
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}


	/**
	 * Gets the config desc.
	 *
	 * @return the config desc
	 */
	public String getConfigDesc() {
		return configDesc;
	}


	/**
	 * Sets the config desc.
	 *
	 * @param configDesc the new config desc
	 */
	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}


	/**
	 * Gets the config val.
	 *
	 * @return the config val
	 */
	public String getConfigVal() {
		return configVal;
	}


	/**
	 * Sets the config val.
	 *
	 * @param configVal the new config val
	 */
	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getCreateId()
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateId(java.lang.String)
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getCreateDt()
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateDt(java.sql.Timestamp)
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateId()
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateId(java.lang.String)
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateDt()
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateDt(java.sql.Timestamp)
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}

}