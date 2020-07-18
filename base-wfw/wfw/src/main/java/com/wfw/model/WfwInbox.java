package com.wfw.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * WfwInbox entity. @author Kamruzzaman
 */
@Entity
@Table(name = "WFW_INBOX")
public class WfwInbox extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "INBOX_ID")
	private String inboxId;

	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "REF_NO")
	private String refNo;

	@Column(name = "OFFICER_ID")
	private String officerId;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwInbox() {
	}


	public String getInboxId() {
		return inboxId;
	}


	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
	}


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


	public String getOfficerId() {
		return officerId;
	}


	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}


	@Override
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}

}