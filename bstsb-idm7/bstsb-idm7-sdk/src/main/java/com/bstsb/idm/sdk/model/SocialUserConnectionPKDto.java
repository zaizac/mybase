/**
 * 
 */
package com.bstsb.idm.sdk.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author muhammad.hafidz
 *
 */
@JsonInclude(Include.NON_NULL)
public class SocialUserConnectionPKDto implements Serializable{

	private static final long serialVersionUID = 6775143898163091206L;

	private String userId;

	private String providerId;

	private String providerUserId;


	public SocialUserConnectionPKDto() {
	}
	


	public SocialUserConnectionPKDto(String userId, String providerId, String providerUserId) {
		this.userId = userId;
		this.providerId = providerId;
		this.providerUserId = providerUserId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getProviderId() {
		return providerId;
	}


	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}


	public String getProviderUserId() {
		return providerUserId;
	}


	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}


}
