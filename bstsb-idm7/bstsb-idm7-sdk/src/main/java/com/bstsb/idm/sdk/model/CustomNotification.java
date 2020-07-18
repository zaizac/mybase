/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.util.Map;


/**
 * @author mary.jane
 * @since Dec 14, 2018
 */
public class CustomNotification implements Serializable {

	private static final long serialVersionUID = 4725306827807021450L;

	private String templateCode;

	private transient Map<String, Object> templateParams;


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	public Map<String, Object> getTemplateParams() {
		return templateParams;
	}


	public void setTemplateParams(Map<String, Object> templateParams) {
		this.templateParams = templateParams;
	}

}