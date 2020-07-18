/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.util.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.be.sdk.constants.IdmRoleConstants;
import com.dialect.iam.CustomUserDetails;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.portal.constants.PageConstants;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class WebHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebHelper.class);

	@Autowired
	private HttpServletRequest request;

	protected static final Map<String, Object> PAGE_REDIRECT_MAP = new HashMap<>();

	static {
		PAGE_REDIRECT_MAP.put(IdmRoleConstants.SYSTEM_ADMIN, PageConstants.PAGE_IDM_USR_LST);
	}


	public boolean isOn() {
		return true;
	}


	public List<UserMenu> getMenuList() {
		List<UserMenu> menuList = new ArrayList<>();
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
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
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

}