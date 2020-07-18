/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.core;


import java.sql.Timestamp;

import org.springframework.util.StringUtils;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractEntity {

	// set the create id & time stamp details for every transaction @each
	// entity
	public abstract String getCreateId();


	public abstract void setCreateId(String createId);


	public abstract Timestamp getCreateDt();


	public abstract void setCreateDt(Timestamp createDt);


	// set the update id & time stamp details for every transaction @each
	// entity
	public abstract String getUpdateId();


	public abstract void setUpdateId(String updateId);


	public abstract Timestamp getUpdateDt();


	public abstract void setUpdateDt(Timestamp updateDt);


	public String toUpper(String value) {
		return StringUtils.hasText(value) ? org.apache.commons.lang3.StringUtils.upperCase(value).trim() : null;
	}

}