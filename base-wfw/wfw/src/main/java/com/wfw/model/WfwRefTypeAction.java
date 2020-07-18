/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_REF_TYPE_ACTION")
public class WfwRefTypeAction extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4278505942561784209L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REF_TYPE_ACTION_ID")
	private Integer typeActionId;

	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "REF_LEVEL_ID")
	private Integer levelId;

	@Column(name = "ACTION_NAME")
	private String actionName;

	@Column(name = "REDIRECT_PATH")
	private String redirectPath;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_LEVEL_ID", insertable = false, updatable = false)
	@JsonBackReference
	private WfwRefLevel refLevel;


	/**
	 * @return the typeActionId
	 */
	public Integer getTypeActionId() {
		return typeActionId;
	}


	/**
	 * @param typeActionId
	 *             the typeActionId to set
	 */
	public void setTypeActionId(Integer typeActionId) {
		this.typeActionId = typeActionId;
	}


	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}


	/**
	 * @param typeId
	 *             the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}


	/**
	 * @param levelId
	 *             the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}


	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}


	/**
	 * @param actionName
	 *             the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	/**
	 * @return the redirectPath
	 */
	public String getRedirectPath() {
		return redirectPath;
	}


	/**
	 * @param redirectPath
	 *             the redirectPath to set
	 */
	public void setRedirectPath(String redirectPath) {
		this.redirectPath = redirectPath;
	}


	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}


	/**
	 * @param status
	 *             the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
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
