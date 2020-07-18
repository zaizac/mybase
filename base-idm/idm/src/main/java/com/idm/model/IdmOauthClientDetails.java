package com.idm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idm.core.AbstractEntity;
import com.util.model.IQfCriteria;


/**
 * The persistent class for the IDM_OAUTH_CLIENT_DETAILS database table.
 * 
 * @author Hafidz Malik
 * @since Dec 5, 2019
 */
@Entity
@Table(name="IDM_OAUTH_CLIENT_DETAILS")
@NamedQuery(name="IdmOauthClientDetails.findAll", query="SELECT i FROM IdmOauthClientDetails i")
public class IdmOauthClientDetails extends AbstractEntity implements Serializable, IQfCriteria<IdmOauthClientDetails> {
	
	private static final long serialVersionUID = 8745820277667200791L;

	@Id
	@Column(name="client_id", unique = true, nullable = false)
	private String clientId;

	@Column(name="access_token_validity")
	private Integer accessTokenValidity;

	@Column(name="additional_information")
	private String additionalInformation;

	@Column(name="authorities")
	private String authorities;

	@Column(name="authorized_grant_types")
	private String authorizedGrantTypes;

	@Column(name="autoapprove")
	private String autoapprove;

	@Column(name="client_secret")
	private String clientSecret;

	@Column(name="refresh_token_validity")
	private Integer refreshTokenValidity;

	@Column(name="resource_ids")
	private String resourceIds;

	@Column(name="scope")
	private String scope;

	@Column(name="web_server_redirect_uri")
	private String webServerRedirectUri;

	// required by AbstractEntity, though columns below does not exist in table.
	@Transient
	private Timestamp createDt;
	@Transient
	private String createId;
	@Transient
	private Timestamp updateDt;
	@Transient
	private String updateId;

	
	public IdmOauthClientDetails() {
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

	@Override
	public String getCreateId() {
		return this.createId;
	}

	@Override
	public void setCreateId(String crtUsrId) {
		this.createId = crtUsrId;
	}

	@Override
	public Timestamp getCreateDt() {
		return this.createDt;
	}

	@Override
	public void setCreateDt(Timestamp crtTs) {
		this.createDt = crtTs;
	}

	@Override
	public String getUpdateId() {
		return this.updateId;
	}

	@Override
	public void setUpdateId(String modUsrId) {
		this.updateId = modUsrId;
	}

	@Override
	public Timestamp getUpdateDt() {
		return this.updateDt;
	}

	@Override
	public void setUpdateDt(Timestamp modTs) {
		this.updateDt = modTs;
	}

}