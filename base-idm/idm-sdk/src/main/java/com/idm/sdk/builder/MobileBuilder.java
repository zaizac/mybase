/**
 * Copyright 2019.
 */
package com.idm.sdk.builder;


import com.idm.sdk.client.IdmRestTemplate;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.model.MobileAccessToken;


/**
 * @author mary.jane
 * @since Jan 3, 2019
 */
public class MobileBuilder extends AbstractBuilder {

	private IdmRestTemplate restTemplate;

	private String url;


	public MobileBuilder(IdmRestTemplate restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}


	@Override
	public IdmRestTemplate restTemplate() {
		return restTemplate;
	}


	@Override
	public String url() {
		return url;
	}


	public MobileAccessToken accessToken(MobileAccessToken accessToken) {
		StringBuilder sb = new StringBuilder();
		sb.append(getServiceURI(IdmUrlConstants.MOBILE_ACCESS_TOKEN));
		return restTemplate().postForObject(url, accessToken, MobileAccessToken.class);
	}

}
