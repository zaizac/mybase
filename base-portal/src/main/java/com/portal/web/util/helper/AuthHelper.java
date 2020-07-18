/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.util.helper;


import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class AuthHelper {

	private AuthHelper() {
		throw new IllegalStateException("AuthHelper class");
	}


	public static boolean checkUserAuthorities(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			for (Object name : authorities) {
				GrantedAuthority grantedAuthority = (GrantedAuthority) name;

				if (StringUtils.hasText(role) && StringUtils.hasText(grantedAuthority.getAuthority())
						&& StringUtils.pathEquals(role, grantedAuthority.getAuthority())) {
					return true;
				}

			}
		}
		return false;
	}

}