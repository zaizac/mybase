/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config.iam;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 2324399716541300200L;

	private String wfUserId;

	private String name;

	private String username;

	private String password;

	private String userRole;

	private String isActive;

	Boolean enabled = true;

	Boolean accountNonExpired = true;

	Boolean credentialsNonExpired = true;

	Boolean accountNonLocked = true;

	private List<GrantedAuthority> authorities;


	public CustomUserDetails() {
	}


	public CustomUserDetails(String username, String password, String userRole, String isActive) {
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.isActive = isActive;
	}


	public CustomUserDetails(String username, String password, Boolean enabled, Boolean accountNonExpired,
			Boolean credentialsNonExpired, Boolean accountNonLocked, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
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
		return password;
	}


	@Override
	public String getUsername() {
		return username;
	}


	public void setUserName(String username) {
		this.username = username;
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


	public String getWfUserId() {
		return wfUserId;
	}


	public void setWfUserId(String wfUserId) {
		this.wfUserId = wfUserId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void populateAdditionalFields(Boolean enabled, Boolean accountNonExpired, Boolean credentialsNonExpired,
			Boolean accountNonLocked, List<GrantedAuthority> authorities) {
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = authorities;
	}

}
