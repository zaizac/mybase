/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "NOT_PAYLOAD")
public class NotPayload extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTIFY_ID")
	private Integer id;

	@Column(name = "NOTIFY_TYPE")
	private String notifyType;

	@Column(name = "NOTIFY_TO")
	private String notifyTo;

	@Column(name = "NOTIFY_CC")
	private String notifyCc;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "META_DATA")
	private String metaData;

	@Column(name = "TEMPLATE_CODE")
	private String templateCode;

	@Column(name = "STATUS_CD")
	private String status;

	@Column(name = "RTRY_COUNT")
	private int rtryCount;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "ATTACHMENT")
	private String attachment;


	public NotPayload() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getNotifyTo() {
		return notifyTo;
	}


	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}


	public String getNotifyCc() {
		return notifyCc;
	}


	public void setNotifyCc(String notifyCc) {
		this.notifyCc = notifyCc;
	}


	public String getMetaData() {
		return metaData;
	}


	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getRtryCount() {
		return rtryCount;
	}


	public void setRtryCount(int rtryCount) {
		this.rtryCount = rtryCount;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

}