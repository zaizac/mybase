package com.idm.config.iam;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.idm.constants.QualifierConstants;
import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserLdap;
import com.idm.model.IdmProfile;
import com.idm.model.IdmUserType;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmRoleService;
import com.util.BaseUtil;


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
		IdmProfile user = idmUserProfileService.findByUserId(auth.getName());
		if(!BaseUtil.isObjNull(user)) {
			IdmUserType userType = user.getUserType();
			
			if(!BaseUtil.isObjNull(userType) && userType.isBypassPword()) {
				CustomUserDetails customUserDetails = new CustomUserDetails(user);
				return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
						getAuthorities());
			} else {				
				IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(auth.getName());
				if (!BaseUtil.isObjNull(user) && ldapEntryBean != null
						&& BaseUtil.isEquals(auth.getCredentials().toString(), ldapEntryBean.getUserPassword())) {
					CustomUserDetails customUserDetails = new CustomUserDetails(user);
					return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
							getAuthorities());
				}
			}
		}
		return auth;
	}


	public Collection<GrantedAuthority> getAuthorities() {
		// Create a list of grants for this user
		// Get roles from UserProfile object
		// Return list of granted authorities
		return new ArrayList<>();
	}

}