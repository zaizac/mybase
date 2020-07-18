/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_REF_LEVEL_ROLE")
public class WfwRefLevelRole extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -5986118334836562881L;

	@EmbeddedId
	private WfwRefLevelRolePk levelRolePk;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_LEVEL_ID", insertable = false, updatable = false)
	// @JsonIgnoreProperties("wfwRefLevelRoleList")
	@JsonBackReference
	private WfwRefLevel refLevel;


	/**
	 * @return the levelRolePk
	 */
	public WfwRefLevelRolePk getLevelRolePk() {
		return levelRolePk;
	}


	/**
	 * @param levelRolePk
	 *             the levelRolePk to set
	 */
	public void setLevelRolePk(WfwRefLevelRolePk levelRolePk) {
		this.levelRolePk = levelRolePk;
	}


	/**
	 * @return the createId
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the createDt
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the updateId
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the updateDt
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the refLevel
	 */
	public WfwRefLevel getRefLevel() {
		return refLevel;
	}


	/**
	 * @param refLevel
	 *             the refLevel to set
	 */
	public void setRefLevel(WfwRefLevel refLevel) {
		this.refLevel = refLevel;
	}

}
