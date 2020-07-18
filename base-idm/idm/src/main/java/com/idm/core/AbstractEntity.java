package com.idm.core;


import java.sql.Timestamp;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractEntity {

	// set the create id & time stamp details for every transaction @each
	// entity
	public abstract String getCreateId();


	public abstract void setCreateId(String crtUsrId);


	public abstract Timestamp getCreateDt();


	public abstract void setCreateDt(Timestamp crtTs);


	// set the update id & time stamp details for every transaction @each
	// entity
	public abstract String getUpdateId();


	public abstract void setUpdateId(String modUsrId);


	public abstract Timestamp getUpdateDt();


	public abstract void setUpdateDt(Timestamp modTs);


	public static final String CLOB_CLASS = "org.hibernate.type.StringClobType";

}