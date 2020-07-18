/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wfw.sdk.constants.CmnConstants;


/**
 * @author michelle.angela
 * @since 19 Sep 2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class WfwReference implements Serializable {

	private static final long serialVersionUID = 7419867289877819559L;

	private Integer typeId;

	private String tranId;

	private String moduleId;

	private String redirectPath;

	private String description;

	private Integer autoClaim;

	private Integer status;

	private Integer levelId;

	private Integer statusId;

	private List<RefLevel> refLevelList;

	private String roles;

	private RefLevel refLevel;

	private RefStatus refStatus;

	private String action = CmnConstants.BTN_UPDATE;

	private String config;

	private String userId;

	private Integer display;

	private List<String> levelList;

	private boolean withLevel;

	private boolean withStatus;

	private String groupBy;


	public WfwReference() {
	}


	public WfwReference(Integer typeId, boolean withLevel, boolean withStatus) {
		this.typeId = typeId;
		this.withLevel = withLevel;
		this.withStatus = withStatus;
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
	 * @return the tranId
	 */
	public String getTranId() {
		return tranId;
	}


	/**
	 * @param tranId
	 *             the tranId to set
	 */
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}


	/**
	 * @param moduleId
	 *             the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
	 * @return the autoClaim
	 */
	public Integer getAutoClaim() {
		return autoClaim;
	}


	/**
	 * @param autoClaim
	 *             the autoClaim to set
	 */
	public void setAutoClaim(Integer autoClaim) {
		this.autoClaim = autoClaim;
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
	 * @return the refLevelList
	 */
	public List<RefLevel> getRefLevelList() {
		return refLevelList;
	}


	/**
	 * @param refLevelList
	 *             the refLevelList to set
	 */
	public void setRefLevelList(List<RefLevel> refLevelList) {
		this.refLevelList = refLevelList;
	}


	/**
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}


	/**
	 * @param roles
	 *             the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
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
	 * @return the refLevel
	 */
	public RefLevel getRefLevel() {
		return refLevel;
	}


	/**
	 * @param refLevel
	 *             the refLevel to set
	 */
	public void setRefLevel(RefLevel refLevel) {
		this.refLevel = refLevel;
	}


	/**
	 * @return the refStatus
	 */
	public RefStatus getRefStatus() {
		return refStatus;
	}


	/**
	 * @param refStatus
	 *             the refStatus to set
	 */
	public void setRefStatus(RefStatus refStatus) {
		this.refStatus = refStatus;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}


	/**
	 * @param config
	 *             the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
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
	 * @return the display
	 */
	public Integer isDisplay() {
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
	 * @return the levelList
	 */
	public List<String> getLevelList() {
		return levelList;
	}


	/**
	 * @param levelList
	 *             the levelList to set
	 */
	public void setLevelList(List<String> levelList) {
		this.levelList = levelList;
	}


	/**
	 * @return the withLevel
	 */
	public boolean isWithLevel() {
		return withLevel;
	}


	/**
	 * @param withLevel
	 *             the withLevel to set
	 */
	public void setWithLevel(boolean withLevel) {
		this.withLevel = withLevel;
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


	/**
	 * @return the display
	 */
	public Integer getDisplay() {
		return display;
	}

}
