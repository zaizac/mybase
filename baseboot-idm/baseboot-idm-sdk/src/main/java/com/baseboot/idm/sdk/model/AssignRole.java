/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;
import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class AssignRole implements Serializable {

	private static final long serialVersionUID = 5609738521802126240L;

	private UserRole userRole;

	private List<UserProfile> profiles;

	private List<UserMenu> menuList;


	public UserRole getUserRole() {
		return userRole;
	}


	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


	public List<UserProfile> getProfiles() {
		return profiles;
	}


	public void setProfiles(List<UserProfile> profiles) {
		this.profiles = profiles;
	}


	public List<UserMenu> getMenuList() {
		return menuList;
	}


	public void setMenuList(List<UserMenu> menuList) {
		this.menuList = menuList;
	}

}
