package com.wfw.form;

import java.util.List;

public class SearchForm {
	
	private String taskId;
	private String applicationId;
	private String officerId;
	
	private String error;
	private String msg;
	
	private List<String> checkTaskId;

	public List<String> getCheckTaskId() {
		return checkTaskId;
	}
	public void setCheckTaskId(List<String> checkTaskId) {
		this.checkTaskId = checkTaskId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getOfficerId() {
		return officerId;
	}
	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
