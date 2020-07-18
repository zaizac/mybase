/**
 * 
 */
package com.bstsb.idm.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bstsb.idm.core.AbstractEntity;


/**
 * @author mary.jane
 *
 */
@Entity
@Table(name = "IDM_USER_CONNECTION")
public class IdmUserConnection extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1350022082509145571L;

	@EmbeddedId
	private IdmUserConnectionPK userConnectionPK;

	@Column(name = "RANK", nullable = false)
	private Integer rank;

	@Column(name = "NAME", nullable = true)
	private String name;

	@Column(name = "PROFILE_URL", nullable = true)
	private String profileUrl;

	@Column(name = "IMAGE_URL", nullable = true)
	private String imageUrl;

	@Column(name = "ACCESS_TOKEN", nullable = false)
	private String accessToken;

	@Column(name = "SECRET", nullable = true)
	private String secret;

	@Column(name = "REFRESH_TOKEN", nullable = true)
	private String refreshToken;

	@Column(name = "EXPIRE_TIME", nullable = true)
	private Long expireTime;


	public IdmUserConnectionPK getUserConnectionPK() {
		return userConnectionPK;
	}


	public void setUserConnectionPK(IdmUserConnectionPK userConnectionPK) {
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


	public void setDisplayName(String name) {
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


	@Override
	public String getCreateId() {
		return null;
	}


	@Override
	public void setCreateId(String crtUsrId) {

	}


	@Override
	public Timestamp getCreateDt() {
		return null;
	}


	@Override
	public void setCreateDt(Timestamp crtTs) {

	}


	@Override
	public String getUpdateId() {
		return null;
	}


	@Override
	public void setUpdateId(String modUsrId) {

	}


	@Override
	public Timestamp getUpdateDt() {
		return null;
	}


	@Override
	public void setUpdateDt(Timestamp modTs) {

	}

}
