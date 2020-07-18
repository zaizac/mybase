/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.model;


import java.io.Serializable;
import java.util.List;


/**
 * @author mary.jane
 * @since Nov 26, 2018
 */
public class IcaoRequest implements Serializable {

	private static final long serialVersionUID = -2408867404969242274L;

	private String photo;

	private List<IcaoInfo> config;


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public void setConfig(List<IcaoInfo> config) {
		this.config = config;
	}


	public List<IcaoInfo> getConfig() {
		return config;
	}

}
