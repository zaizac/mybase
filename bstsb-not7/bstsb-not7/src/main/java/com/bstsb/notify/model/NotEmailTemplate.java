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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



/**
 * The Class NotEmailTemplate.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "NOT_EMAIL_TEMPLATE")
public class NotEmailTemplate extends AbstractEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_ID")
	private Integer id;

	/** The template type. */
	@Column(name = "TEMPLATE_TYPE")
	private String templateType;

	/** The template code. */
	@Column(name = "TEMPLATE_CODE")
	private String templateCode;

	/** The template desc. */
	@Column(name = "TEMPLATE_DESC")
	private String templateDesc;

	/** The email subject. */
	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;

	/** The email content. */
	@Column(name = "EMAIL_CONTENT")
	private String emailContent;

	/** The email variable. */
	@Column(name = "EMAIL_VARIABLE")
	private String emailVariable;

	/** The create id. */
	@Column(name = "CREATE_ID")
	private String createId;

	/** The create dt. */
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	/** The update id. */
	@Column(name = "UPDATE_ID")
	private String updateId;

	/** The update dt. */
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


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
	 * @param templateType the new template type
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
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
	 * @param emailSubject the new email subject
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
	 * @param emailContent the new email content
	 */
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
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
	 * @param templateDesc the new template desc
	 */
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
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
	 * @param emailVariable the new email variable
	 */
	public void setEmailVariable(String emailVariable) {
		this.emailVariable = emailVariable;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Template Type: " + templateType);
		sb.append(" - Code: " + templateCode);
		return sb.toString();
	}

}
