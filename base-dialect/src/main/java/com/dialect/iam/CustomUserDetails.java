/**
 *
 */
package com.dialect.iam;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.idm.sdk.model.Token;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;


/**
 * @author Jhayne
 * @since 25/08/2015
 */
@JsonInclude(Include.NON_NULL)
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 6125597533213354951L;

	CustomUserProfile userProfile;

	UserProfile profile;

	Token authToken;

	Boolean enabled = true;

	Boolean accountNonExpired = true;

	Boolean credentialsNonExpired = true;

	Boolean accountNonLocked = true;

	Boolean forceLogout = false;

	String errorCode;

	private List<GrantedAuthority> authorities;


	public CustomUserDetails(UserProfile profile, Token authToken) {
		this.profile = profile;
		this.authToken = authToken;
	}


	public CustomUserDetails(UserProfile profile, CustomUserProfile userProfile, Token authToken) {
		this.profile = profile;
		this.userProfile = userProfile;
		this.authToken = authToken;
	}


	public CustomUserDetails(UserProfile profile, Token authToken, Boolean enabled, Boolean accountNonExpired,
			Boolean credentialsNonExpired, Boolean accountNonLocked, List<GrantedAuthority> authorities) {
		this.profile = profile;
		this.authToken = authToken;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = authorities;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	@Override
	public String getPassword() {
		return !BaseUtil.isObjNull(profile) ? profile.getPassword() : null;
	}


	@Override
	public String getUsername() {
		return !BaseUtil.isObjNull(profile) ? profile.getUserId() : null;
	}


	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}


	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}


	@Override
	public boolean isEnabled() {
		return enabled;
	}


	public UserProfile getProfile() {
		return profile;
	}


	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}


	public void setAuthToken(Token authToken) {
		this.authToken = authToken;
	}


	public Token getAuthToken() {
		return authToken;
	}


	public void setForceLogout(Boolean forceLogout) {
		this.forceLogout = forceLogout;
	}


	public Boolean getForceLogout() {
		return forceLogout;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public CustomUserProfile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(CustomUserProfile userProfile) {
		this.userProfile = userProfile;
	}

}