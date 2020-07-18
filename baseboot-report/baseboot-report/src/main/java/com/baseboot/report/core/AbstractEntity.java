/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public abstract class AbstractEntity {

	// set the auto increment id for every transaction @each entity
	public abstract Integer getId();


	public abstract void setId(Integer id);


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

}