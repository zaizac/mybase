/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_REF_STATUS")
public class WfwRefStatus extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -7088080601645639979L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REF_STATUS_ID")
	private Integer statusId;

	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "REF_LEVEL_ID")
	private Integer levelId;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "STATUS_DESC")
	private String statusDesc;

	@Column(name = "STATUS_SEQUENCE")
	private Integer statusSequence;

	@Column(name = "NEXT_LEVEL_ID")
	private Integer nextLevelId;

	@Column(name = "DISPLAY")
	private Integer display;

	@Column(name = "INITIAL_STATE")
	private Integer initialState;

	@Column(name = "NO_RELEASE")
	private Integer noRelease;

	@Column(name = "EMAIL_REQUIRED")
	private Integer emailRequired;

	@Column(name = "SKIP_APPROVER")
	private Integer skipApprover;

	@Column(name = "LEGEND_COLOR")
	private String legendColor;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	/**
	 * @return the statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}


	/**
	 * @param statusId
	 *             the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}


	/**
	 * @param statusCd
	 *             the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}


	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}


	/**
	 * @param statusDesc
	 *             the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	/**
	 * @return the statusSequence
	 */
	public Integer getStatusSequence() {
		return statusSequence;
	}


	/**
	 * @param statusSequence
	 *             the statusSequence to set
	 */
	public void setStatusSequence(Integer statusSequence) {
		this.statusSequence = statusSequence;
	}


	/**
	 * @return the nextLevelId
	 */
	public Integer getNextLevelId() {
		return nextLevelId;
	}


	/**
	 * @param nextLevelId
	 *             the nextLevelId to set
	 */
	public void setNextLevelId(Integer nextLevelId) {
		this.nextLevelId = nextLevelId;
	}


	/**
	 * @return the display
	 */
	public Integer getDisplay() {
		return display;
	}


	/**
	 * @param display
	 *             the display to set
	 */
	public void setDisplay(Integer display) {
		this.display = display;
	}


	/**
	 * @return the initialState
	 */
	public Integer getInitialState() {
		return initialState;
	}


	/**
	 * @param initialState
	 *             the initialState to set
	 */
	public void setInitialState(Integer initialState) {
		this.initialState = initialState;
	}


	/**
	 * @return the noRelease
	 */
	public Integer getNoRelease() {
		return noRelease;
	}


	/**
	 * @param noRelease
	 *             the noRelease to set
	 */
	public void setNoRelease(Integer noRelease) {
		this.noRelease = noRelease;
	}


	/**
	 * @return the emailRequired
	 */
	public Integer getEmailRequired() {
		return emailRequired;
	}


	/**
	 * @param emailRequired
	 *             the emailRequired to set
	 */
	public void setEmailRequired(Integer emailRequired) {
		this.emailRequired = emailRequired;
	}


	/**
	 * @return the skipApprover
	 */
	public Integer getSkipApprover() {
		return skipApprover;
	}


	/**
	 * @param skipApprover
	 *             the skipApprover to set
	 */
	public void setSkipApprover(Integer skipApprover) {
		this.skipApprover = skipApprover;
	}


	/**
	 * @return the legendColor
	 */
	public String getLegendColor() {
		return legendColor;
	}


	/**
	 * @param legendColor
	 *             the legendColor to set
	 */
	public void setLegendColor(String legendColor) {
		this.legendColor = legendColor;
	}


	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * @param status
	 *             the status to set
	 */
	public void setStatus(Integer status) {
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

}
