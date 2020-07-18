/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.report.sdk.builder;


import java.util.Properties;

import com.report.sdk.client.ReportRestTemplate;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ReportService extends AbstractService {

	private ReportRestTemplate restTemplate;

	private Properties prop;

	private String url;


	public ReportService(ReportRestTemplate restTemplate, Properties prop, String url) {
		this.restTemplate = restTemplate;
		this.prop = prop;
		this.url = url;
	}
	
	@Override
	public ReportRestTemplate restTemplate() {
		return restTemplate;
	}


	@Override
	public Properties prop() {
		return prop;
	}


	@Override
	public String url() {
		return url;
	}



}
