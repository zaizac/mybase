package com.wfw.sdk.model;


import java.io.Serializable;
import java.util.Date;


@Deprecated
public class TaskRemark implements Serializable {

	private static final long serialVersionUID = 962313095694577933L;

	private String taskId;

	private String refNo;

	private String userAction;

	private String applStsCode;

	private String applStsCodeNext;

	private String remarks;

	private Date remarkTime;

	private String remarkBy;

	private String remarkByFullName;


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getApplStsCode() {
		return applStsCode;
	}


	public void setApplStsCode(String applStsCode) {
		this.applStsCode = applStsCode;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Date getRemarkTime() {
		return remarkTime;
	}


	public void setRemarkTime(Date remarkTime) {
		this.remarkTime = remarkTime;
	}


	public String getRemarkBy() {
		return remarkBy;
	}


	public void setRemarkBy(String remarkBy) {
		this.remarkBy = remarkBy;
	}


	public String getApplStsCodeNext() {
		return applStsCodeNext;
	}


	public void setApplStsCodeNext(String applStsCodeNext) {
		this.applStsCodeNext = applStsCodeNext;
	}


	public String getUserAction() {
		return userAction;
	}


	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}


	public String getRemarkByFullName() {
		return remarkByFullName;
	}


	public void setRemarkByFullName(String remarkByFullName) {
		this.remarkByFullName = remarkByFullName;
	}

}
