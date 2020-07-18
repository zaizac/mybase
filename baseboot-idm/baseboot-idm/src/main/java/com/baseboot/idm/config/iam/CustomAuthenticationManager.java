/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.config.iam;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.ldap.IdmUserDao;
import com.baseboot.idm.ldap.IdmUserLdap;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.service.IdmRoleService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class CustomAuthenticationManager implements AuthenticationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationManager.class);

	@Lazy
	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmUserProfileService;

	@Autowired
	@Qualifier(QualifierConstants.IDMROLE_SERVICE)
	private IdmRoleService idmRoleService;


	@Override
	public Authentication authenticate(Authentication auth) {
		IdmProfile user = idmUserProfileService.findProfileByUserId(auth.getName());
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(auth.getName());
		if (!BaseUtil.isObjNull(user)) {
			if (ldapEntryBean != null && auth != null
					&& BaseUtil.isEquals(auth.getCredentials().toString(), ldapEntryBean.getUserPassword())) {
				CustomUserDetails customUserDetails = new CustomUserDetails(user);
				return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
						getAuthorities(user));
			}
		}
		return auth;
	}


	public Collection<GrantedAuthority> getAuthorities(IdmProfile user) {
		// Create a list of grants for this user
		List<GrantedAuthority> roles = new ArrayList<>();

		// Get roles from UserProfile object

		// Return list of granted authorities
		return roles;
	}

}