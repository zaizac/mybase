/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.helper;


import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class AuthHelper {

	protected static Logger LOGGER = LoggerFactory.getLogger(AuthHelper.class);


	public static boolean checkUserAuthorities(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			for (Iterator<?> iterator = authorities.iterator(); iterator.hasNext();) {
				GrantedAuthority grantedAuthority = (GrantedAuthority) iterator.next();

				if (StringUtils.hasText(role) && StringUtils.hasText(grantedAuthority.getAuthority())
						&& StringUtils.pathEquals(role, grantedAuthority.getAuthority())) {
					return true;
				}

			}
		}
		return false;
	}

}