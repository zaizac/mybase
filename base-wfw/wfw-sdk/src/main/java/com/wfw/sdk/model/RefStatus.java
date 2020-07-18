/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.List;


/**
 * @author michelle.angela
 *
 */
public class RefStatus implements Serializable {

	private static final long serialVersionUID = -4068715442751733977L;

	private Integer statusId;

	private Integer typeId;

	private Integer levelId;

	private String statusCd;

	private String statusDesc;

	private Integer statusSequence;

	private Integer nextLevelId;

	private Integer display;

	private Integer initialState;

	private Integer noRelease;

	private Integer emailRequired;

	private Integer skipApprover;

	private String legendColor;

	private Integer status;

	private String userId;

	private List<Integer> levelIdList;

	private String groupBy;


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


	public Integer getTypeId() {
		return typeId;
	}


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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId
	 *             the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the levelIdList
	 */
	public List<Integer> getLevelIdList() {
		return levelIdList;
	}


	/**
	 * @param levelIdList
	 *             the levelIdList to set
	 */
	public void setLevelIdList(List<Integer> levelIdList) {
		this.levelIdList = levelIdList;
	}


	/**
	 * @return the groupBy
	 */
	public String getGroupBy() {
		return groupBy;
	}


	/**
	 * @param groupBy
	 *             the groupBy to set
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

}
