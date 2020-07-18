/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


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
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.config.oauth.ClientOauth2;
import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.Oauth2Constants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.ldap.IdmUserDao;
import com.baseboot.idm.ldap.IdmUserLdap;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.LoginDto;
import com.baseboot.idm.sdk.model.Token;
import com.baseboot.idm.sdk.util.AuthCredentials;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmProfileService;


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

	@Lazy
	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;


	@RequestMapping(value = IdmUrlConstrants.LOGIN, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public LoginDto login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
			HttpServletResponse response) throws IdmException {

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

		loginDto.setStatus(idmProfile.getStatus());
		loginDto.setUserRoleGroupCode(idmProfile.getUserRoleGroupCode());
		loginDto.setGender(idmProfile.getGender());
		loginDto.setFullName(idmProfile.getFullName());
		loginDto.setProfId(idmProfile.getProfId());

		if (!BaseUtil.isObjNull(idmProfile) && !BaseUtil.isEqualsCaseIgnore("SYSA", idmProfile.getUserType())
				&& !BaseUtil.isEqualsCaseIgnore("ALL", loginDto.getUserType())) {
			LOGGER.debug("User Type: {}", idmProfile.getUserType());
			idmProfile = idmProfileService.findUserProfileByUserIdAndUserType(loginDto.getUserId(),
					loginDto.getUserType());
		}

		if (BaseUtil.isObjNull(idmProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM157,
					new String[] { loginDto.getUserType() });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		IdmUserLdap ldapEntryBean = null;
		try {
			ldapEntryBean = idmUserDao.searchEntry(loginDto.getUserId());
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new IdmException(IdmErrorCodeEnum.E500IDM903, new String[] { e.getMessage() });
		}

		if (loginDto.getClientId() == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		boolean auth = false;
		if (ldapEntryBean == null) {// no user name
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
					new String[] { loginDto.getUserId() });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		} else {
			ldapEntryBean = new IdmUserLdap();
			ldapEntryBean.setUserName(loginDto.getUserId());
			ldapEntryBean.setUserPassword(loginDto.getPassword());
			auth = idmUserDao.authenticate(ldapEntryBean);
		}

		if (!auth) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM103,
					new String[] { loginDto.getUserId() });
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
		params.add(new BasicNameValuePair("grant_type", Oauth2Constants.GRANT_TYPE_PASSWORD));
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
		return loginDto;
	}


	@RequestMapping(value = IdmUrlConstrants.LOGOUT, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Boolean logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGIN);
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		LOGGER.debug("logout: {}", authorizationHeader);

		String token = basicAuthCredentials.getToken();

		if (tokenServices.revokeToken(token)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
			ctxLogOut.logout(request, response, auth);
			return true;
		} else {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I500IDM105, new String[] { token });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
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
