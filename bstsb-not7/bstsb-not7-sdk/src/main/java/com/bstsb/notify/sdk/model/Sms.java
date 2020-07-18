/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class Sms.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Sms implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7379854086494302387L;

	/** The mobile no. */
	private String mobileNo;

	/** The content. */
	private String content;

	/** The template code. */
	private String templateCode;


	/**
	 * Instantiates a new sms.
	 *
	 * @param mobileNo
	 *             the mobile no
	 * @param content
	 *             the content
	 * @param templateCode
	 *             the template code
	 */
	public Sms(String mobileNo, String content, String templateCode) {
		this.mobileNo = mobileNo;
		this.content = content;
		this.templateCode = templateCode;
	}


	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}


	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo
	 *             the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * Sets the content.
	 *
	 * @param content
	 *             the new content
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @param templateCode
	 *             the new template code
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

}