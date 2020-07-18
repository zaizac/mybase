/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.model;


import java.io.Serializable;


/**
 * @author michelle.angela
 * @since 20 Sep 2019
 */
public class RefLevelRole implements Serializable {

	private static final long serialVersionUID = -8365516003544110458L;

	private String roleCd;

	private Integer levelId;


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
