package com.wfw.util;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.wfw.config.iam.CustomUserDetails;


public class SessionData {

	private SessionData() {
		throw new IllegalStateException("SessionData Utility class");
	}


	public static String getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}


	public static CustomUserDetails getCurrentUserProfile() {
		CustomUserDetails userProfile = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			userProfile = (CustomUserDetails) auth.getPrincipal();
		}
		return userProfile;
	}


	public static String getCurrentUserFullName() {
		CustomUserDetails userProfile = getCurrentUserProfile();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!BaseUtil.isObjNull(userProfile)) {
			userProfile = (CustomUserDetails) auth.getPrincipal();
			return userProfile.getName();
		}
		return BaseConstants.EMPTY_STRING;
	}


	public static List<String> getUserRoles() {
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		List<String> roleList = new ArrayList<>();
		for (GrantedAuthority prRole : authorities) {
			roleList.add(prRole.getAuthority());
		}
		return roleList;
	}
}
