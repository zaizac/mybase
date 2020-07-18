package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
public class UserTypePortal implements Serializable, IQfCriteria<UserTypePortal> {

	private static final long serialVersionUID = 1722807447675764705L;

	private Integer userPortalId;

	private UserType userType;

	private PortalType portalType;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	private String updateId;


	/**
	 * @return the userPortalId
	 */
	public Integer getUserPortalId() {
		return userPortalId;
	}


	/**
	 * @param userPortalId
	 *             the userPortalId to set
	 */
	public void setUserPortalId(Integer userPortalId) {
		this.userPortalId = userPortalId;
	}


	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}


	/**
	 * @param userType
	 *             the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	/**
	 * @return the portalType
	 */
	public PortalType getPortalType() {
		return portalType;
	}


	/**
	 * @param portalType
	 *             the portalType to set
	 */
	public void setPortalType(PortalType portalType) {
		this.portalType = portalType;
	}


	/**
	 * @return the createDt
	 */
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the updateDt
	 */
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the updateId
	 */
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}
