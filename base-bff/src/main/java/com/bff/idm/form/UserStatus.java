/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.form;

import org.apache.commons.lang3.StringUtils;

/**
 * @author manjunath.dudami
 *
 */
public enum UserStatus {
	ACTIVE("A", "ACTIVE"),
	INACTIVE("I", "INACTIVE"),
	PENDING_ACTIVATION("F", "PENDING ACTIVATION");

	private String status;

	private String statusDescription;

	/**
	 * @param status
	 * @param statusDescription
	 */
	private UserStatus(String status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}


	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public static String getUserStatusDescription(String status) {

		for (UserStatus cStatus : UserStatus.values()) {
			if (StringUtils.equals(cStatus.getStatus(), status)) {
				return cStatus.getStatusDescription();
			}
		}
		return null;
	}


}
