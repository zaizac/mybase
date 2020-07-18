/**
 * Copyright 2019. 
 */
package com.notify.sdk.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonDateDeserializer;
import com.util.serializer.JsonDateSerializer;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;

/**
 * The Class Notification.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonInclude(Include.NON_NULL)
public class Notification {

	/** The id. */
	private Integer id;
	
	/** The notify to. */
	private String notifyTo;

	/** The notify cc. */
	private String notifyCc;

	/** The notify bcc. */
	private String notifyBcc;

	/** The notify type. */
	private String notifyType;
	
	/** The subject. */
	private String subject;

	/** The subject param. */
	private List<String> subjectParam;

	/** The meta data. */
	private String metaData;
	
	/** The template code. */
	private String templateCode;

	/** The attachments. */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
	private List<Attachment> attachments;

	/** The create dt. */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	/** The create dt. */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;
	
	private String content;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(pattern = BaseConstants.DT_YYYY_MM_DD_SLASH)
	private Date updateDtFrom;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonFormat(pattern = BaseConstants.DT_YYYY_MM_DD_SLASH)
	private Date updateDtTo;

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
	 * Gets the subject param.
	 *
	 * @return the subject param
	 */
	public List<String> getSubjectParam() {
		return subjectParam;
	}


	/**
	 * Sets the subject param.
	 *
	 * @param subjectParam
	 *             the new subject param
	 */
	public void setSubjectParam(List<String> subjectParam) {
		this.subjectParam = subjectParam;
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
	 * @param notifyTo
	 *             the new notify to
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
	 * @param notifyCc
	 *             the new notify cc
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
	 * @param notifyBcc
	 *             the new notify bcc
	 */
	public void setNotifyBcc(String notifyBcc) {
		this.notifyBcc = notifyBcc;
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
	 * @param notifyType
	 *             the new notify type
	 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
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
	 * @param metaData
	 *             the new meta data
	 */
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}


	/**
	 * Sets the attachments.
	 *
	 * @param attachments
	 *             the new attachments
	 */
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	/**
	 * Gets the creates the dt.
	 *
	 * @return the creates the dt
	 */
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * Sets the creates the dt.
	 *
	 * @param createDt
	 *             the new creates the dt
	 */
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	/**
	 * @return the updateDt
	 */
	public Timestamp getUpdateDt() {
		return updateDt;
	}

	/**
	 * @param updateDt the updateDt to set
	 */
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
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