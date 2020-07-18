/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config.iam;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dialect.iam.CustomUserDetails;
import com.dialect.iam.CustomUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.LoginDto;
import com.idm.sdk.model.Token;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.portal.config.audit.AuditActionPolicy;
import com.portal.constants.MessageConstants;
import com.portal.core.AbstractController;
import com.portal.web.util.WebUtil;
import com.util.BaseUtil;
import com.util.SSHA;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class CustomAuthenticationManager extends AbstractController implements AuthenticationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationManager.class);


	@Override
	public Authentication authenticate(Authentication auth) {
		LOGGER.debug("Performing custom authentication");
		LOGGER.debug("Username: {} - Password: {}", auth.getName(), auth.getCredentials());
		LOGGER.debug("eKey: {}", messageService.getMessage(BaseConfigConstants.SVC_IDM_EKEY));

		String userId = auth.getName();
		String pword = SSHA.getLDAPSSHAHash(String.valueOf(auth.getCredentials()), getPwordEkey());
		LOGGER.debug(pword);

		LoginDto login = new LoginDto();
		login.setUserId(userId);
		login.setPassword(pword);
		login.setUserType(messageService.getMessage("app.portal.type"));
		LOGGER.info("Portal Type: {}", login.getUserType());

		String reqMsg = "";
		try {
			reqMsg = new ObjectMapper().writeValueAsString(login);
		} catch (JsonProcessingException e1) {
			// ignore
		}

		if ("gov".equalsIgnoreCase(login.getUserType())) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String timeStr = sdf.format(now);
			try {
				Time timeNow = new Time(sdf.parse(timeStr).getTime());
				Time timeFrom = new Time(sdf.parse(messageService.getMessage("gov.portal.access.from")).getTime());
				Time timeTo = new Time(sdf.parse(messageService.getMessage("gov.portal.access.to")).getTime());
				LOGGER.debug(
						"\n\t[Time NOW: {} \n\tTime From: {} \n\tTime To: {} \n\tDate From: {} \n\tDate To: {} \n]",
						timeNow, timeFrom, timeTo, timeNow.before(timeFrom), timeNow.after(timeTo));
				if (timeNow.before(timeFrom) || timeNow.after(timeTo)) {
					if ((timeNow.after(timeFrom) && timeNow.before(timeTo))) {
						// DO NOTHING
					} else {
						throw new BadCredentialsException(messageService.getMessage("msg.sys.off.acc"));
					}
				}

			} catch (ParseException e) {
				// IGNORE
			}

		}

		try {
			login = getIdmService().login(login);
		} catch (IdmException e) {
			LOGGER.error("IdmException: {}", e.getMessage());

			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			String errorCode = e.getInternalErrorCode();
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.E503IDM000.name(), errorCode)) {
				throw new BadCredentialsException("System unavailable. Please contact administrator. <br/>"
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			}
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM157.name(), errorCode)) {
				throw new BadCredentialsException(messageService.getMessage("lbl.err.inv.usr")
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			} else {
				throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD)
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			}
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		if (login == null || login.getToken() == null) {
			throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		Token token = login.getToken();
		// here to view upload profile picture
		UserProfile userProfile = null;

		try {
			userProfile = getIdmService(login.getClientId(), token.getAccessToken()).getUserProfileById(userId, true,
					true);
			LOGGER.debug("userProfile: {}", new ObjectMapper().valueToTree(userProfile));
		} catch (IdmException e) {
			LOGGER.error("IdmException: {}", e.getMessage());
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			String errorCode = e.getInternalErrorCode();

			throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD)
					+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		LOGGER.debug("userProfile: {}", userProfile);
		if (BaseUtil.isObjNull(userProfile) || BaseUtil.isObjNull(userProfile.getUserId())) {
			try {
				staticData.addUserAction(AuditActionPolicy.LOGIN_FAILED, auth.getName(), reqMsg);
			} catch (Exception ex) {
				LOGGER.error("IDM-AuditAction Response Error: {}", ex.getMessage());
			}

			throw new BadCredentialsException(messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		LOGGER.info("UserType: {}", userProfile.getUserType());
		CustomUserProfile profile = getProfileByUserType(
				userProfile.getUserType() != null ? userProfile.getUserType().getUserTypeCode() : null, userProfile.getProfId(),
				userProfile.getBranchId());
		CustomUserDetails customUserDetails = null;

		if (!BaseUtil.isObjNull(profile)) {
			customUserDetails = new CustomUserDetails(userProfile, profile, token);
		} else {
			customUserDetails = new CustomUserDetails(userProfile, token);
		}

		try {
			staticData.addUserAction(AuditActionPolicy.LOGIN, auth.getName(), reqMsg);
		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}

		return new UsernamePasswordAuthenticationToken(customUserDetails, auth.getCredentials(),
				getAuthorities(userProfile));

	}


	public Collection<GrantedAuthority> getAuthorities(UserProfile userProfile) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<>();
		LOGGER.debug("userProfile: {}", !BaseUtil.isObjNull(userProfile));

		// Get roles from UserProfile object
		if (!BaseUtil.isObjNull(userProfile) && !BaseUtil.isListNull(userProfile.getRoles())) {
			LOGGER.debug("Role Size: {}", userProfile.getRoles().size());
			for (UserRole role : userProfile.getRoles()) {
				LOGGER.debug("Role: {}", role.getRoleCode());
				authList.add(new SimpleGrantedAuthority(role.getRoleCode()));
			}

		}

		// Return list of granted authorities
		return authList;
	}


	private CustomUserProfile getProfileByUserType(String userType, Integer profId, Integer branchId) {
		CustomUserProfile profile = null;
		LOGGER.info("getProfileByUserType>>> {} - {} - {}", userType, profId, branchId);

		return profile;
	}

}