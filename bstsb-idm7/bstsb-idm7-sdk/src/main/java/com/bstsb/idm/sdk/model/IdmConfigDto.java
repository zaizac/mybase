/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;


/**
 * @author mary.jane
 * @since Feb 20, 2019
 */
public class IdmConfigDto implements Serializable {

	private static final long serialVersionUID = 1687384409891732894L;

	private String configCode;

	private String configDesc;

	private String configVal;


	public IdmConfigDto() {
		// DO NOTHING
	}


	public IdmConfigDto(String configCode, String configDesc, String configVal) {
		this.configCode = configCode;
		this.configDesc = configDesc;
		this.configVal = configVal;
	}


	public String getConfigCode() {
		return configCode;
	}


	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}


	public String getConfigDesc() {
		return configDesc;
	}


	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}


	public String getConfigVal() {
		return configVal;
	}


	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}

}
