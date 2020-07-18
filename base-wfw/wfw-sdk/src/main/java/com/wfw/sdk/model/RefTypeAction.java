/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;


/**
 * @author michelle.angela
 *
 */
public class RefTypeAction implements Serializable {

	private static final long serialVersionUID = -1218716245216864934L;

	private Integer typeActionId;

	private Integer typeId;

	private Integer levelId;

	private String actionName;

	private String redirectPath;

	private boolean status;


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

}
