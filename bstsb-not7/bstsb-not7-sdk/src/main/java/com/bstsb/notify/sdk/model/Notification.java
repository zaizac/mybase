/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.model;


import java.sql.Timestamp;
import java.util.List;

import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.serializer.JsonTimestampDeserializer;
import com.bstsb.util.serializer.JsonTimestampSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * The Class Notification.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonInclude(Include.NON_NULL)
public class Notification {

	/** The notify to. */
	private String notifyTo;

	/** The notify cc. */
	private String notifyCc;

	/** The notify bcc. */
	private String notifyBcc;

	/** The notify type. */
	private String notifyType;

	/** The subject param. */
	private List<String> subjectParam;

	/** The meta data. */
	private String metaData;

	/** The attachments. */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
	private List<Attachment> attachments;

	/** The create dt. */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	private String mimeMessage;

	private String emailContent;

	private String subject;

	private String templateCode;

	private String emailSubject;


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


	public String getMimeMessage() {
		return mimeMessage;
	}


	public void setMimeMessage(String mimeMessage) {
		this.mimeMessage = mimeMessage;
	}


	public String getEmailContent() {
		return emailContent;
	}


	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

}