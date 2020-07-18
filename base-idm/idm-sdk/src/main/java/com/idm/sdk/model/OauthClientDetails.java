package com.idm.sdk.model;

import java.io.Serializable;


/**
 * The DTO class for the IDM_OAUTH_CLIENT_DETAILS database table.
 * 
 * @author Hafidz Malik
 * @since Dec 5, 2019
 */
public class OauthClientDetails implements Serializable {
	

	private static final long serialVersionUID = -6474824992620987069L;

	private String clientId;

	private Integer accessTokenValidity;

	private String additionalInformation;

	private String authorities;

	private String authorizedGrantTypes;

	private String autoapprove;

	private String clientSecret;

	private Integer refreshTokenValidity;

	private String resourceIds;

	private String scope;

	private String webServerRedirectUri;

	
	public OauthClientDetails() {
		// do nothing
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getAccessTokenValidity() {
		return this.accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public String getAdditionalInformation() {
		return this.additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getAuthorizedGrantTypes() {
		return this.authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getAutoapprove() {
		return this.autoapprove;
	}

	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Integer getRefreshTokenValidity() {
		return this.refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getResourceIds() {
		return this.resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getWebServerRedirectUri() {
		return this.webServerRedirectUri;
	}

	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}

}