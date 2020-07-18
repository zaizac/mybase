/**
 * 
 */
package com.bstsb.idm.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @author mary.jane
 *
 */
@Embeddable
public class IdmUserConnectionPK implements Serializable {

	private static final long serialVersionUID = -2158494419790145617L;

	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "PROVIDER_ID", nullable = false)
	private String providerId;

	@Column(name = "PROVIDER_USER_ID", nullable = false)
	private String providerUserId;


	public IdmUserConnectionPK() {
	}


	public IdmUserConnectionPK(String userId, String providerId, String providerUserId) {
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


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IdmUserConnectionPK)) {
			return false;
		}
		IdmUserConnectionPK castOther = (IdmUserConnectionPK) other;
		return this.userId.equals(castOther.userId) && (this.providerId == castOther.providerId)
				&& (this.providerUserId == castOther.providerUserId);
	}


	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.providerId.hashCode();
		hash = hash * prime + this.providerUserId.hashCode();
		return hash;
	}

}
