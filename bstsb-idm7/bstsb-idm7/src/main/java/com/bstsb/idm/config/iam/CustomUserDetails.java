/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.config.iam;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bstsb.idm.model.IdmProfile;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 6125597533213354951L;

	IdmProfile user;

	Boolean enabled = true;

	Boolean accountNonExpired = true;

	Boolean credentialsNonExpired = true;

	Boolean accountNonLocked = true;

	private List<GrantedAuthority> authorities;


	public CustomUserDetails(IdmProfile user) {
		this.user = user;
	}


	public CustomUserDetails(IdmProfile user, Boolean enabled, Boolean accountNonExpired,
			Boolean credentialsNonExpired, Boolean accountNonLocked, List<GrantedAuthority> authorities) {
		this.user = user;
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
		return !BaseUtil.isObjNull(user) ? user.getPassword() : null;
	}


	@Override
	public String getUsername() {
		return !BaseUtil.isObjNull(user) ? user.getUserId() : null;
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


	public IdmProfile getUser() {
		return user;
	}


	public void setUser(IdmProfile user) {
		this.user = user;
	}

}