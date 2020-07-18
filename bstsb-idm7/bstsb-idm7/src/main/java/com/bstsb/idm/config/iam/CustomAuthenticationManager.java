/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.config.iam;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.ldap.IdmUserLdap;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.constants.IdmConstants;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.IdmRoleService;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmUserProfileService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_ROLE_SERVICE)
	private IdmRoleService idmRoleService;


	@Override
	public Authentication authenticate(Authentication auth) {
		boolean isSocialLogin = false;
		
		if(auth != null && auth.getDetails() != null) {
			@SuppressWarnings("unchecked")
			Map<String, String> params = (LinkedHashMap<String, String>) auth.getDetails();
			for(String paramKey : params.keySet()) {
				//if param contains "SOCIAL_LOGIN_ENABLE":"true"
				if(BaseUtil.isEquals(paramKey, IdmConstants.SOCIAL_LOGIN_ENABLE) && BaseUtil.isEqualsCaseIgnore(params.get(paramKey), "true")) {
					isSocialLogin = true;
				}
			}
		}
		
		IdmProfile user = idmUserProfileService.findProfileByUserId(auth.getName());
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(auth.getName());
		if (!BaseUtil.isObjNull(user)) {
			if(!isSocialLogin && ldapEntryBean != null && auth != null
						&& BaseUtil.isEquals(auth.getCredentials().toString(), ldapEntryBean.getUserPassword())) {
					CustomUserDetails customUserDetails = new CustomUserDetails(user);
					return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
							getAuthorities(user));				
			} else {
				CustomUserDetails customUserDetails = new CustomUserDetails(user);
				return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
						getAuthorities(user));
			}
		}
		return auth;
	}


	public Collection<GrantedAuthority> getAuthorities(IdmProfile user) {
		// Create a list of grants for this user
		// Get roles from UserProfile object
		// Return list of granted authorities
		return new ArrayList<>();
	}

}