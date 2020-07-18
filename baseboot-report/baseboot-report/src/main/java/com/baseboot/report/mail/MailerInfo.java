/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.mail;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonInclude(Include.NON_NULL)
public class MailerInfo implements Serializable {

	private static final long serialVersionUID = -1890612250084619253L;

	private String ccEmail;

	private String toEmail;

	private String mailSubject;

	private List<MailAttachment> attachments;


	public String getCcEmail() {
		return ccEmail;
	}


	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}


	public String getToEmail() {
		return toEmail;
	}


	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}


	public String getMailSubject() {
		return mailSubject;
	}


	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}


	public List<MailAttachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<MailAttachment> attachments) {
		this.attachments = attachments;
	}

}
