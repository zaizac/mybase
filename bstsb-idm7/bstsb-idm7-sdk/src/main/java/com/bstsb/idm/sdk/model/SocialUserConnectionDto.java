/**
 *
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author mary.jane
 * @author muhammad.hafidz
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialUserConnectionDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2529078601352840658L;

	private SocialUserConnectionPKDto userConnectionPK;

	private Integer rank;

	private String name;

	private String profileUrl;

	private String imageUrl;

	private String accessToken;

	private String secret;

	private String refreshToken;

	private Long expireTime;


	public SocialUserConnectionDto() {
		userConnectionPK = new SocialUserConnectionPKDto();
	}


	public SocialUserConnectionDto(String userId, String providerId, String providerUserId) {
		userConnectionPK = new SocialUserConnectionPKDto();
		userConnectionPK.setProviderId(providerId);
		userConnectionPK.setProviderUserId(providerUserId);
		userConnectionPK.setUserId(userId);
	}


	public SocialUserConnectionPKDto getUserConnectionPK() {
		return userConnectionPK;
	}


	public void setUserConnectionPK(SocialUserConnectionPKDto userConnectionPK) {
		this.userConnectionPK = userConnectionPK;
	}


	public Integer getRank() {
		return rank;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProfileUrl() {
		return profileUrl;
	}


	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	public Long getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

}
