/**
 * 
 */
package com.bstsb.idm.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstsb.idm.core.AbstractEntity;


/**
 * @author mary.jane
 * @since 06 Jul 2018
 */
@Entity
@Table(name = "IDM_SOCIAL")
public class IdmSocial extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2972464437039893458L;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	private String userId;

	@Column(name = "NAME", nullable = true, length = 32)
	private String name;

	@Column(name = "PASSWORD", nullable = false, length = 64)
	private String password;

	@Column(name = "EMAIL_ID", nullable = true, length = 128)
	private String email;

	@Column(name = "ACTIVE", nullable = false, length = 1)
	private Integer active;

	@Column(name = "PROVIDER", nullable = false, length = 32)
	private String provider;

	@Column(name = "PROVIDER_USER_ID", nullable = false, length = 255)
	private String providerUserId;

	public IdmSocial() {
	}


	public IdmSocial(final String userId, final String name, final String password, final String email,
			final Integer active, final String provider, final String providerUserId) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.active = active;
		this.provider = provider;
		this.providerUserId = providerUserId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}

	
	public String getProviderUserId() {
		return providerUserId;
	}


	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
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
