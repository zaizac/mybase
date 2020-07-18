/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.constants.Oauth2Constants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.ldap.IdmUserLdap;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.constants.IdmConstants;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.LoginDto;
import com.bstsb.idm.sdk.model.Token;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.util.ClientOauth2;
import com.bstsb.util.AuthCredentials;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.DateUtil;
import com.bstsb.util.MediaType;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.model.MessageResponse;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class AuthRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

	@Autowired
	ClientDetailsService clientDetailsService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	ConsumerTokenServices tokenServices;

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;


	@PostMapping(value = IdmUrlConstants.LOGIN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public LoginDto login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
			HttpServletResponse response) {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String clientId = authCredentials.getClient();

		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

		if (client == null) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
		}

		loginDto.setClientId(clientId);
		loginDto.setClientSecret(client.getClientSecret());

		if (loginDto.getClientId() == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// Find user in IdmProfile
		IdmProfile idmProfile = idmProfileService.findProfileByUserId(loginDto.getUserId());

		// If user not found in IdmProfile, throw error
		if (BaseUtil.isObjNull(idmProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
					new String[] { loginDto.getUserId() });
			LOGGER.error("IdmException (IdmProfile): {}", idmEx.getMessage());
			throw idmEx;
		}

		// Check User Type
		if (!BaseUtil.isObjNull(idmProfile) && !BaseUtil.isEqualsCaseIgnore("SYSA", idmProfile.getUserType())
				&& !BaseUtil.isEqualsCaseIgnore("ALL", loginDto.getUserType())) {
			LOGGER.info("User Type: {}", idmProfile.getUserType());
			LOGGER.info("User Type: {} - User Id: {}", loginDto.getUserType(), loginDto.getUserId());
			idmProfile = idmProfileService.findUserProfileByUserIdAndUserType(loginDto.getUserId(),
					loginDto.getUserType());
			LOGGER.info("idmProfile: {}", BaseUtil.isObjNull(idmProfile));

			if (BaseUtil.isObjNull(idmProfile)) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM157,
						new String[] { loginDto.getUserType() });
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}
		}

		// Search user in LDAP
		IdmUserLdap ldapEntryBean = null;
		try {
			ldapEntryBean = idmUserDao.searchEntry(loginDto.getUserId());
		} catch (Exception e) {
			throw new IdmException(IdmErrorCodeEnum.E500IDM903, new String[] { e.getMessage() });
		}

		// If user not found in LDAP, throw error
		if (ldapEntryBean == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
					new String[] { loginDto.getUserId() });
			LOGGER.error("IdmException (LDAP): {}", idmEx.getMessage());
			throw idmEx;
		}

		// checking whether user status is inactive
		if (BaseConstants.USER_INACTIVE.equalsIgnoreCase(idmProfile.getStatus())) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM119);
		} else if (BaseConstants.USER_PENDING_ACTIVATION.equalsIgnoreCase(idmProfile.getStatus())) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM121);
		}

		loginDto.setStatus(idmProfile.getStatus());
		loginDto.setUserRoleGroupCode(idmProfile.getUserRoleGroupCode());
		loginDto.setGender(idmProfile.getGender());
		loginDto.setFullName(idmProfile.getFullName());
		loginDto.setProfId(idmProfile.getProfId());
		loginDto.setUserType(idmProfile.getUserType());

		ldapEntryBean = new IdmUserLdap();
		ldapEntryBean.setUserName(loginDto.getUserId());
		ldapEntryBean.setUserPassword(loginDto.getPassword());
		boolean auth = idmUserDao.authenticate(ldapEntryBean);

		if (!auth) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM103,
					new String[] { loginDto.getUserId() });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// remove first the existing token
		Collection<OAuth2AccessToken> listTokens = tokenStore.findTokensByClientIdAndUserName(clientId,
				loginDto.getUserId());
		for (OAuth2AccessToken a : listTokens) {
			tokenServices.revokeToken(a.getValue());
		}

		// generated token and refresh token
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", loginDto.getClientId()));
		params.add(new BasicNameValuePair("client_secret", loginDto.getClientSecret()));
		params.add(new BasicNameValuePair("grant_type", Oauth2Constants.GRANT_TYPE_PWORD));
		params.add(new BasicNameValuePair("username", loginDto.getUserId()));
		params.add(new BasicNameValuePair("password", loginDto.getPassword()));

		Token token = new ClientOauth2().generateAccessToken(params, request);
		loginDto.setToken(token);

		response.setHeader(HttpHeaders.AUTHORIZATION,
				AuthCredentials.authorizationBasic(loginDto.getClientId(), token.getAccessToken()));
		response.setHeader("Content-Type", "application/json");
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGIN);
		request.setAttribute("currUserId", loginDto.getUserId());
		loginDto.setUserType(idmProfile.getUserType());
		loginDto.setStatus(idmProfile.getStatus());
		return loginDto;
	}


	// For social login - remove LDAP login
	@PostMapping(value = IdmUrlConstants.SOCIAL_LOGIN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public LoginDto loginSocial(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
			HttpServletResponse response) {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String clientId = authCredentials.getClient();

		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

		if (client == null) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
		}

		loginDto.setClientId(clientId);
		loginDto.setClientSecret(client.getClientSecret());

		IdmProfile idmProfile = idmProfileService.findProfileByUserId(loginDto.getUserId());

		// checking whether user status is active or not
		// if not then throw error, status is inactive.
		if (!BaseUtil.isObjNull(idmProfile) && idmProfile.getStatus().equalsIgnoreCase("i")) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM119);
		}

		if (BaseUtil.isObjNull(idmProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM157,
					new String[] { loginDto.getUserType() });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		loginDto.setStatus(idmProfile.getStatus());
		loginDto.setUserRoleGroupCode(idmProfile.getUserRoleGroupCode());
		loginDto.setGender(idmProfile.getGender());
		loginDto.setFullName(idmProfile.getFullName());
		loginDto.setProfId(idmProfile.getProfId());

		if (loginDto.getClientId() == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		// remove first the existing token
		Collection<OAuth2AccessToken> listTokens = tokenStore.findTokensByClientIdAndUserName(clientId,
				loginDto.getUserId());
		for (OAuth2AccessToken a : listTokens) {
			tokenServices.revokeToken(a.getValue());
		}

		// generated token and refresh token
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", loginDto.getClientId()));
		params.add(new BasicNameValuePair("client_secret", loginDto.getClientSecret()));
		params.add(new BasicNameValuePair("grant_type", Oauth2Constants.GRANT_TYPE_PWORD));
		params.add(new BasicNameValuePair("username", loginDto.getUserId()));
		params.add(new BasicNameValuePair("password", loginDto.getPassword()));
		params.add(new BasicNameValuePair(IdmConstants.SOCIAL_LOGIN_ENABLE, "true"));

		Token token = new ClientOauth2().generateAccessToken(params, request);
		loginDto.setToken(token);

		response.setHeader(HttpHeaders.AUTHORIZATION,
				AuthCredentials.authorizationBasic(loginDto.getClientId(), token.getAccessToken()));
		response.setHeader("Content-Type", "application/json");
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGIN);
		request.setAttribute("currUserId", loginDto.getUserId());
		loginDto.setUserType(idmProfile.getUserType());
		return loginDto;
	}


	@PostMapping(value = IdmUrlConstants.LOGOUT, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public MessageResponse logout(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGOUT);
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		LOGGER.info("logout: {}", authorizationHeader);

		String token = basicAuthCredentials.getToken();

		if (tokenServices.revokeToken(token)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
			ctxLogOut.logout(request, response, auth);
			return new MessageResponse(DateUtil.getSQLTimestamp(), 200, IdmErrorCodeEnum.I200C000IDM.name(),
					IdmErrorCodeEnum.I200C000IDM.getMessage(), request.getRequestURI());
		} else {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I500IDM105, new String[] { token });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
	}


	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}


	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

}
