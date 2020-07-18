/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config.iam;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Component
public class AuthProvider implements AuthenticationProvider {

	public AuthProvider() {
		super();
	}


	@Override
	public Authentication authenticate(Authentication auth) {
		String name = auth.getName();
		String password = auth.getCredentials().toString();
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
	}


	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
