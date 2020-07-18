/**
 * Copyright 2019. 
 */
package com.notify.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.notify.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonDateDeserializer;
import com.util.serializer.JsonDateSerializer;
import com.util.serializer.JsonTimestampSerializer;

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
public class NotPayload extends AbstractEntity implements Serializable, IQfCriteria<NotPayload> {

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

	@Column(name = "SYSTEM_TYPE")
	private String systemType;
	
	@Column(name = "CONTENT")
	private String content;

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
	
	@Transient
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date updateDtFrom;

	@Transient
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH)
	private Date updateDtTo;
	
	@Transient
	private Integer length;

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
	 * Gets the notify bcc.
	 *
	 * @return the notify bcc
	 */
	public String getNotifyBcc() {
		return notifyBcc;
	}


	/**
	 * Sets the notify bcc.
	 *
	 * @param notifyCc the new notify bcc
	 */
	public void setNotifyBcc(String notifyBcc) {
		this.notifyBcc = notifyBcc;
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
	 * @see com.notify.core.AbstractEntity#getCreateDt()
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateDt(java.sql.Timestamp)
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getCreateId()
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setCreateId(java.lang.String)
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateDt()
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateDt(java.sql.Timestamp)
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#getUpdateId()
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractEntity#setUpdateId(java.lang.String)
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
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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

	/**
	 * @return the updateDtFrom
	 */
	public Date getUpdateDtFrom() {
		return updateDtFrom;
	}

	/**
	 * @param updateDtFrom the updateDtFrom to set
	 */
	public void setUpdateDtFrom(Date updateDtFrom) {
		this.updateDtFrom = updateDtFrom;
	}

	/**
	 * @return the updateDtTo
	 */
	public Date getUpdateDtTo() {
		return updateDtTo;
	}

	/**
	 * @param updateDtTo the updateDtTo to set
	 */
	public void setUpdateDtTo(Date updateDtTo) {
		this.updateDtTo = updateDtTo;
	}

	/**
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

}