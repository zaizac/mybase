package com.wfw.sdk.model;


import java.io.Serializable;


@Deprecated
public class TaskStatus implements Serializable {

	private static final long serialVersionUID = 138792763297381501L;

	private String statusId;

	private String description;

	private String applStatus;


	public String getStatusId() {
		return statusId;
	}


	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getApplStatus() {
		return applStatus;
	}


	public void setApplStatus(String applStatus) {
		this.applStatus = applStatus;
	}

}
