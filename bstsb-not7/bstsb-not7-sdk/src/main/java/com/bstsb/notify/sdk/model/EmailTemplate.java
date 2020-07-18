/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class EmailTemplate.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class EmailTemplate implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The template type. */
	private String templateType;

	/** The template code. */
	private String templateCode;

	/** The template desc. */
	private String templateDesc;

	/** The email subject. */
	private String emailSubject;

	/** The email content. */
	private String emailContent;

	/** The email variable. */
	private String emailVariable;


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
	 * @param id
	 *             the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Gets the template type.
	 *
	 * @return the template type
	 */
	public String getTemplateType() {
		return templateType;
	}


	/**
	 * Sets the template type.
	 *
	 * @param templateType
	 *             the new template type
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}


	/**
	 * Gets the template desc.
	 *
	 * @return the template desc
	 */
	public String getTemplateDesc() {
		return templateDesc;
	}


	/**
	 * Sets the template desc.
	 *
	 * @param templateDesc
	 *             the new template desc
	 */
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}


	/**
	 * Gets the email subject.
	 *
	 * @return the email subject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}


	/**
	 * Sets the email subject.
	 *
	 * @param emailSubject
	 *             the new email subject
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}


	/**
	 * Gets the email content.
	 *
	 * @return the email content
	 */
	public String getEmailContent() {
		return emailContent;
	}


	/**
	 * Sets the email content.
	 *
	 * @param emailContent
	 *             the new email content
	 */
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}


	/**
	 * Gets the email variable.
	 *
	 * @return the email variable
	 */
	public String getEmailVariable() {
		return emailVariable;
	}


	/**
	 * Sets the email variable.
	 *
	 * @param emailVariable
	 *             the new email variable
	 */
	public void setEmailVariable(String emailVariable) {
		this.emailVariable = emailVariable;
	}


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

}