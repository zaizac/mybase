package com.be.sdk.builder;

import com.be.sdk.client.BeRestTemplate;

public class ReferenceService extends AbstractService {

	private BeRestTemplate restTemplate;

	private String url;


	public ReferenceService(BeRestTemplate restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}


	@Override
	public BeRestTemplate restTemplate() {
		return restTemplate;
	}


	@Override
	public String url() {
		return url;
	}

}
