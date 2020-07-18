/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author michelle.angela
 * @since 19 Sep 2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonInclude(Include.NON_NULL)
public class RefLevel implements Serializable {

	private static final long serialVersionUID = -2850487458141495582L;

	private Integer levelId;

	private Integer typeId;

	private String description;

	private Integer flowNo;

	private Integer status;

	private List<String> roles;

	private List<RefStatus> refStatus;

	private List<RefLevelRole> refLevelRoleList;

	private String userId;

	private List<RefTypeAction> refTypeActionList;

	private boolean withStatus;


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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description
	 *             the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the flowNo
	 */
	public Integer getFlowNo() {
		return flowNo;
	}


	/**
	 * @param flowNo
	 *             the flowNo to set
	 */
	public void setFlowNo(Integer flowNo) {
		this.flowNo = flowNo;
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


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	/**
	 * @return the refStatus
	 */
	public List<RefStatus> getRefStatus() {
		return refStatus;
	}


	/**
	 * @param refStatus
	 *             the refStatus to set
	 */
	public void setRefStatus(List<RefStatus> refStatus) {
		this.refStatus = refStatus;
	}


	/**
	 * @return the refLevelRoleList
	 */
	public List<RefLevelRole> getRefLevelRoleList() {
		return refLevelRoleList;
	}


	/**
	 * @param refLevelRoleList
	 *             the refLevelRoleList to set
	 */
	public void setRefLevelRoleList(List<RefLevelRole> refLevelRoleList) {
		this.refLevelRoleList = refLevelRoleList;
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
	 * @return the refTypeActionList
	 */
	public List<RefTypeAction> getRefTypeActionList() {
		return refTypeActionList;
	}


	/**
	 * @param refTypeActionList
	 *             the refTypeActionList to set
	 */
	public void setRefTypeActionList(List<RefTypeAction> refTypeActionList) {
		this.refTypeActionList = refTypeActionList;
	}


	/**
	 * @return the withStatus
	 */
	public boolean isWithStatus() {
		return withStatus;
	}


	/**
	 * @param withStatus
	 *             the withStatus to set
	 */
	public void setWithStatus(boolean withStatus) {
		this.withStatus = withStatus;
	}

}
