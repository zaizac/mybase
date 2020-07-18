/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstsb.notify.core.AbstractEntity;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.serializer.JsonTimestampSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



/**
 * The Class NotPayload.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "NOT_PAYLOAD")
public class NotPayload extends AbstractEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTIFY_ID")
	private Integer id;

	/** The notify type. */
	@Column(name = "NOTIFY_TYPE")
	private String notifyType;

	/** The notify to. */
	@Column(name = "NOTIFY_TO")
	private String notifyTo;

	/** The notify cc. */
	@Column(name = "NOTIFY_CC")
	private String notifyCc;

	/** The notify bcc. */
	@Column(name = "NOTIFY_BCC")
	private String notifyBcc;

	/** The subject. */
	@Column(name = "SUBJECT")
	private String subject;

	/** The meta data. */
	@Column(name = "META_DATA")
	private String metaData;

	/** The template code. */
	@Column(name = "TEMPLATE_CODE")
	private String templateCode;

	/** The status. */
	@Column(name = "STATUS_CD")
	private String status;

	/** The rtry count. */
	@Column(name = "RTRY_COUNT")
	private int rtryCount;

	/** The attachment. */
	@Column(name = "ATTACHMENT")
	private String attachment;

	/** The create dt. */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	/** The create id. */
	@Column(name = "CREATE_ID")
	private String createId;

	/** The update dt. */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	/** The update id. */
	@Column(name = "UPDATE_ID")
	private String updateId;


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * Gets the notify to.
	 *
	 * @return the notify to
	 */
	public String getNotifyTo() {
		return notifyTo;
	}


	/**
	 * Sets the notify to.
	 *
	 * @param notifyTo the new notify to
	 */
	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}


	/**
	 * Gets the notify cc.
	 *
	 * @return the notify cc
	 */
	public String getNotifyCc() {
		return notifyCc;
	}


	/**
	 * Sets the notify cc.
	 *
	 * @param notifyCc the new notify cc
	 */
	public void setNotifyCc(String notifyCc) {
		this.notifyCc = notifyCc;
	}


	/**
	 * Gets the meta data.
	 *
	 * @return the meta data
	 */
	public String getMetaData() {
		return metaData;
	}


	/**
	 * Sets the meta data.
	 *
	 * @param metaData the new meta data
	 */
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}


	/**
	 * Gets the template code.
	 *
	 * @return the template code
	 */
	public String getTemplateCode() {
		return templateCode;
	}


	/**
	 * Sets the template code.
	 *
	 * @param templateCode the new template code
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * Gets the rtry count.
	 *
	 * @return the rtry count
	 */
	public int getRtryCount() {
		return rtryCount;
	}


	/**
	 * Sets the rtry count.
	 *
	 * @param rtryCount the new rtry count
	 */
	public void setRtryCount(int rtryCount) {
		this.rtryCount = rtryCount;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#getCreateDt()
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#setCreateDt(java.sql.Timestamp)
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#getCreateId()
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#setCreateId(java.lang.String)
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#getUpdateDt()
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#setUpdateDt(java.sql.Timestamp)
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#getUpdateId()
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractEntity#setUpdateId(java.lang.String)
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * Gets the attachment.
	 *
	 * @return the attachment
	 */
	public String getAttachment() {
		return attachment;
	}


	/**
	 * Sets the attachment.
	 *
	 * @param attachment the new attachment
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	/**
	 * Gets the notify type.
	 *
	 * @return the notify type
	 */
	public String getNotifyType() {
		return notifyType;
	}


	/**
	 * Sets the notify type.
	 *
	 * @param notifyType the new notify type
	 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

}