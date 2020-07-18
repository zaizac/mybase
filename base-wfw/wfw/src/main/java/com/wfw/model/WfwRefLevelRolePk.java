/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @author michelle.angela
 *
 */
@Embeddable
public class WfwRefLevelRolePk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_CD")
	private String roleCd;

	@Column(name = "REF_LEVEL_ID")
	private Integer levelId;


	public WfwRefLevelRolePk() {
	}


	public WfwRefLevelRolePk(Integer levelId, String roleCd) {
		this.levelId = levelId;
		this.roleCd = roleCd;
	}


	/**
	 * @return the roleCd
	 */
	public String getRoleCd() {
		return roleCd;
	}


	/**
	 * @param roleCd
	 *             the roleCd to set
	 */
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
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

}
