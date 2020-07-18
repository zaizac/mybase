/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config.iam;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.idm.sdk.exception.IdmException;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwUser;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.WebUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Component("userService")
public class CustomUserDetailsService extends AbstractController implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);


	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			WfwUser wu = getCommonService().findWfUserByUsername(CmnUtil.getStr(username));
			CustomUserDetails up = new CustomUserDetails();
			if (!CmnUtil.isObjNull(wu)) {
				LOGGER.info("Username: {} - Role: {} - User Status : {}", wu.getUserName(), wu.getUserRole(),
						wu.getIsActive());
				up.setName(wu.getName());
				up.setPassword(wu.getPassword());
				up.setUserName(wu.getUserName());
				up.setUserRole(wu.getUserRole());
				up.setIsActive(wu.getIsActive());
				up.setWfUserId(wu.getUpdateId());
				up.populateAdditionalFields(enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
						getAuthorities(up));
			}

			return up;

		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}


	private static List<GrantedAuthority> getAuthorities(CustomUserDetails user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
		return authorities;
	}


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}
}