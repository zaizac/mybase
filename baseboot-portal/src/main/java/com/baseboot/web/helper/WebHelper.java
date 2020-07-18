/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.helper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baseboot.web.config.iam.CustomUserDetails;
import com.baseboot.web.constants.PageConstants;
import com.be.sdk.constants.IdmRoleConstants;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class WebHelper {

	@Autowired
	private HttpServletRequest request;


	public boolean isOn() {
		return true;
	}


	public List<UserMenu> getMenuList() {
		List<UserMenu> menuList = new ArrayList<UserMenu>();
		UserProfile userProfile = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			userProfile = cud.getProfile();
			menuList = userProfile.getMenus();
		}
		return menuList;
	}


	public String baseUrl() {
		String host = request.getHeader("X-Forwarded-Host");
		return "//" + (!BaseUtil.isObjNull(host) ? host : request.getHeader("Host"));
	}


	public String redirectUrl() {
		String url = PageConstants.PAGE_HOME;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (BaseUtil.isObjNull(auth)) {
				return PageConstants.PAGE_SRC;
			}
			if (auth.isAuthenticated()) {
				CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
				UserProfile userProfile = cud.getProfile();
				if (!BaseUtil.isObjNull(userProfile)) {
					url = redirectPageByRole(userProfile.getRoles());
				}
			}
		} catch (Exception e) {
		}
		return url;
	}


	public static String redirectPageByRole(List<UserRole> roleLst) {
		if (!BaseUtil.isListNull(roleLst)) {
			for (UserRole ur : roleLst) {
				if (PAGE_REDIRECT_MAP.containsKey(ur.getRoleCode())) {
					return PAGE_REDIRECT_MAP.get(ur.getRoleCode()).toString();
				}
			}
		}
		return null;
	}


	public static final Map<String, Object> PAGE_REDIRECT_MAP = Collections
			.unmodifiableMap(new HashMap<String, Object>() {
				private static final long serialVersionUID = -8923858369800247186L;

				{
					put(IdmRoleConstants.SYSTEM_ADMIN, PageConstants.PAGE_IDM_USR_LST);
				}
			});

}