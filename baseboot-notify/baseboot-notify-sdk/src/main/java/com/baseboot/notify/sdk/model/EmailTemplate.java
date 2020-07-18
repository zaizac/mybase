/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class EmailTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String templateType;

	private String templateDesc;

	private String emailSubject;

	private String emailContent;

	private String emailVariable;


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


	public String getTemplateDesc() {
		return templateDesc;
	}


	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
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


	public String getEmailVariable() {
		return emailVariable;
	}


	public void setEmailVariable(String emailVariable) {
		this.emailVariable = emailVariable;
	}

}