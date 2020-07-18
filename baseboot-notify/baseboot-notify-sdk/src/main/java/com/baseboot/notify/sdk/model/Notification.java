/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.model;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subject;

	private String notifyTo;

	private String notifyCc;

	private String notifyType;

	private String metaData;

	private List<MailAttachment> attachment;


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


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public String getMetaData() {
		return metaData;
	}


	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}


	public List<MailAttachment> getAttachment() {
		return attachment;
	}


	public void setAttachment(List<MailAttachment> attachment) {
		this.attachment = attachment;
	}

}