package com.baseboot.notify.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baseboot.notify.core.AbstractEntity;


@Entity
@Table(name = "NOT_ANNOUNCEMENT")
public class NotAnnouncement extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 2270425911125487517L;

	@Id
	@Column(name = "ANNOUNCE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int anncId;

	@Column(name = "ANNOUNCE_TITLE")
	private String anncTitle;

	@Column(name = "ANNOUNCE_MESSAGE")
	private String anncMessage;

	@Column(name = "ANNOUNCE_DATE")
	private Timestamp anncDate;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	public int getAnncId() {
		return anncId;
	}

	public void setAnncId(int anncId) {
		this.anncId = anncId;
	}

	public String getAnncTitle() {
		return anncTitle;
	}

	public void setAnncTitle(String anncTitle) {
		this.anncTitle = anncTitle;
	}

	public String getAnncMessage() {
		return anncMessage;
	}

	public void setAnncMessage(String anncMessage) {
		this.anncMessage = anncMessage;
	}

	public Timestamp getAnncDate() {
		return anncDate;
	}

	public void setAnncDate(Timestamp anncDate) {
		this.anncDate = anncDate;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Timestamp getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


}
