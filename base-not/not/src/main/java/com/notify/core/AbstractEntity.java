/**
 * Copyright 2019. 
 */
package com.notify.core;


import java.sql.Timestamp;

import org.springframework.util.StringUtils;



/**
 * The Class AbstractEntity.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractEntity {

	// set the create id & time stamp details for every transaction @each
	/**
	 * Gets the creates the id.
	 *
	 * @return the creates the id
	 */
	// entity
	public abstract String getCreateId();


	/**
	 * Sets the creates the id.
	 *
	 * @param createId the new creates the id
	 */
	public abstract void setCreateId(String createId);


	/**
	 * Gets the creates the dt.
	 *
	 * @return the creates the dt
	 */
	public abstract Timestamp getCreateDt();


	/**
	 * Sets the creates the dt.
	 *
	 * @param createDt the new creates the dt
	 */
	public abstract void setCreateDt(Timestamp createDt);


	// set the update id & time stamp details for every transaction @each
	/**
	 * Gets the update id.
	 *
	 * @return the update id
	 */
	// entity
	public abstract String getUpdateId();


	/**
	 * Sets the update id.
	 *
	 * @param updateId the new update id
	 */
	public abstract void setUpdateId(String updateId);


	/**
	 * Gets the update dt.
	 *
	 * @return the update dt
	 */
	public abstract Timestamp getUpdateDt();


	/**
	 * Sets the update dt.
	 *
	 * @param updateDt the new update dt
	 */
	public abstract void setUpdateDt(Timestamp updateDt);


	/**
	 * To upper.
	 *
	 * @param value the value
	 * @return the string
	 */
	public String toUpper(String value) {
		return StringUtils.hasText(value) ? org.apache.commons.lang3.StringUtils.upperCase(value).trim() : null;
	}

}