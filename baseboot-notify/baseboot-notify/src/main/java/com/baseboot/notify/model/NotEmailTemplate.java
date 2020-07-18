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
@Table(name = "NOT_EMAIL_TEMPLATE")
public class NotEmailTemplate extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_ID")
	private Integer id;

	@Column(name = "TEMPLATE_TYPE")
	private String templateType;

	@Column(name = "TEMPLATE_DESC")
	private String templateDesc;

	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;

	@Column(name = "EMAIL_CONTENT")
	private String emailContent;

	@Column(name = "EMAIL_VARIABLE")
	private String emailVariable;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public NotEmailTemplate() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTemplateType() {
		return templateType;
	}


	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}


	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}


	public String getEmailContent() {
		return emailContent;
	}


	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}


	@Override
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getTemplateDesc() {
		return templateDesc;
	}


	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}


	public String getEmailVariable() {
		return emailVariable;
	}


	public void setEmailVariable(String emailVariable) {
		this.emailVariable = emailVariable;
	}

}
