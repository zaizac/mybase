/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.Oauth2Constants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.Token;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.util.AuthCredentials;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmProfileService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.TOKENS)
public class TokenRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenRestController.class);

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private ConsumerTokenServices tokenServices;

	@Autowired
	private DefaultTokenServices defaultTokenServices;

	@Autowired
	private ClientDetailsService clientDetailsService;

	private TokenEnhancer accessTokenEnhancer;

	@Autowired
	private IdmProfileService idmUserProfileService;


	/**
	 * Delete/Revoke the Token
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Boolean revokeToken(HttpServletRequest request) throws Exception {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String token = basicAuthCredentials.getToken();

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_TOKEN_DEL);
		if (tokenServices.revokeToken(token)) {
			LOGGER.info(IdmErrorCodeEnum.I200IDM116.getMessage());
			return true;
		} else {
			LOGGER.info(IdmErrorCodeEnum.I404IDM115.getMessage());
			return false;
		}
	}


	/**
	 * Find Token by Client Id
	 *
	 * @param request
	 * @param embededUser
	 * @param embededRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<Token> listTokensForClient(HttpServletRequest request,
			@RequestParam(value = "embededUser", required = false) boolean embededUser,
			@RequestParam(value = "embededRole", required = false) boolean embededRole) throws Exception {

		List<Token> tokens = new ArrayList<>();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String clientId = basicAuthCredentials.getClient();

		Collection<OAuth2AccessToken> listTokensForClient = enhance(tokenStore.findTokensByClientId(clientId));
		if (listTokensForClient == null) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		if (listTokensForClient.size() == 0) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		for (OAuth2AccessToken oAuth2AccessToken : listTokensForClient) {
			Token tokenDto = new Token();
			tokenDto.setAccessToken(oAuth2AccessToken.getValue());
			tokenDto.setExpiresIn(String.valueOf(oAuth2AccessToken.getExpiresIn()));
			tokenDto.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
			tokenDto.setScope(String.valueOf(oAuth2AccessToken.getScope()));
			tokenDto.setTokenType(oAuth2AccessToken.getTokenType());

			getUserProfile(embededUser, embededRole, oAuth2AccessToken, tokenDto);

			tokens.add(tokenDto);
		}

		return tokens;
	}


	/**
	 * Generate Access Token
	 *
	 * @param request
	 * @param header
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Token generatedAccessToken(HttpServletRequest request, HttpServletResponse header)
			throws JsonParseException, JsonMappingException, IOException {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_TOKEN_NEW);
		Token tokenDto = new Token();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String token = basicAuthCredentials.getToken();
		String client = basicAuthCredentials.getClient();

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

		if (accessToken == null) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I404IDM113.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		if (accessToken.getRefreshToken() == null) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I404IDM107.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM107, new String[] { token });
		}

		OAuth2Authentication authentication = tokenStore
				.readAuthenticationForRefreshToken(accessToken.getRefreshToken());

		if (isExpired(accessToken.getRefreshToken())) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I400IDM109.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I400IDM109,
					new String[] { accessToken.getRefreshToken().getValue() });
		}

		if (accessToken.isExpired()) {
			// create access token
			accessToken = defaultTokenServices.createAccessToken(authentication);
			header.setHeader(HttpHeaders.AUTHORIZATION,
					AuthCredentials.authorizationBasic(client, accessToken.getValue()));
			header.setHeader("Content-Type", "application/json");
		}

		tokenDto.setAccessToken(accessToken.getValue());
		tokenDto.setExpiresIn(String.valueOf(accessToken.getExpiresIn()));
		tokenDto.setRefreshToken(accessToken.getRefreshToken().getValue());
		tokenDto.setScope(accessToken.getScope().toString());
		tokenDto.setTokenType(accessToken.getTokenType());
		return tokenDto;
	}


	/**
	 * Find by access token
	 *
	 * @param request
	 * @param embededUser
	 * @param embededRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Token findAccessToken(HttpServletRequest request,
			@RequestParam(value = "embededUser", required = false) String embededUser,
			@RequestParam(value = "embededRole", required = false) boolean embededRole) throws Exception {
		Token tokenDto = new Token();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String token = basicAuthCredentials.getToken();

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

		if (accessToken == null) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I404IDM115.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM115, new String[] { token });
		}

		if (accessToken.isExpired()) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I404IDM111.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM111, new String[] { token });
		}

		tokenDto.setAccessToken(accessToken.getValue());
		tokenDto.setExpiresIn(String.valueOf(accessToken.getExpiresIn()));
		tokenDto.setRefreshToken(accessToken.getRefreshToken().getValue());
		tokenDto.setScope(accessToken.getScope().toString());
		tokenDto.setTokenType(accessToken.getTokenType());

		getUserProfile(true, embededRole, accessToken, tokenDto);

		return tokenDto;
	}


	/**
	 * Validate Access Token
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Token validateAccessToken(HttpServletRequest request) throws Exception {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_TOKEN_VALIDATE);
		Token tokenDto = new Token();

		String token = null;
		OAuth2AccessToken accessToken = null;
		String clientId = (String) request.getAttribute("clientId");
		if (clientId != null) {
			ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
			try {
				Map<String, Object> map = client.getAdditionalInformation();
				String userId = map.get("userId").toString();
				IdmProfile user = idmUserProfileService.findProfileByUserId(userId);
				if (!BaseUtil.isObjNull(user)) {
					UserProfile userProfile = new UserProfile();
					userProfile.setCntry(user.getCntry());
					userProfile.setCreateDt(user.getCreateDt());
					userProfile.setCreateId(user.getCreateId());
					userProfile.setDob(user.getDob());
					userProfile.setEmail(user.getEmail());
					userProfile.setFullName(user.getFullName());
					userProfile.setGender(user.getGender());
					userProfile.setMobPhoneNo(user.getMobPhoneNo());
					userProfile.setPassword(user.getPassword());
					userProfile.setRole(user.getRole());
					userProfile.setUpdateDt(user.getUpdateDt());
					userProfile.setUpdateId(user.getUpdateId());
					userProfile.setUserId(user.getUserId());
					tokenDto.setUser(userProfile);
				}
			} catch (Exception e) {
				LOGGER.info("no user id inside client id");
			}
			return tokenDto;
		}
		try {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			token = AuthCredentials
					.createCredentialsFromHeader(authorizationHeader, Oauth2Constants.TOKEN_TYPE_BASIC).getToken();
			accessToken = tokenStore.readAccessToken(token);
		} catch (Exception e) {
			LOGGER.info(IdmErrorCodeEnum.I404IDM113.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		if (accessToken == null) {
			LOGGER.info(IdmErrorCodeEnum.I404IDM113.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM113, new String[] { token });
		}

		if (accessToken.isExpired()) {
			LOGGER.info(IdmErrorCodeEnum.I404IDM111.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM111, new String[] { token });
		}

		tokenDto.setAccessToken(accessToken.getValue());
		tokenDto.setExpiresIn(String.valueOf(accessToken.getExpiresIn()));
		tokenDto.setRefreshToken(accessToken.getRefreshToken().getValue());
		tokenDto.setScope(accessToken.getScope().toString());
		tokenDto.setTokenType(accessToken.getTokenType());
		getUserProfile(true, true, accessToken, tokenDto);
		return tokenDto;
	}


	private Token getUserProfile(boolean embededUser, boolean embededRole, OAuth2AccessToken accessToken,
			Token tokenDto) {
		if (embededUser) {
			OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
			IdmProfile user = idmUserProfileService.findProfileByUserId(oAuth2Authentication.getName());
			if (user == null) {
				LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I404IDM102.getMessage());
				throw new IdmException(IdmErrorCodeEnum.I404IDM102,
						new String[] { oAuth2Authentication.getName() });
			}

			UserProfile userProfile = new UserProfile();
			userProfile.setCntry(user.getCntry());
			userProfile.setCreateDt(user.getCreateDt());
			userProfile.setCreateId(user.getCreateId());
			userProfile.setDob(user.getDob());
			userProfile.setEmail(user.getEmail());
			userProfile.setFullName(user.getFullName());
			userProfile.setGender(user.getGender());
			userProfile.setMobPhoneNo(user.getMobPhoneNo());
			userProfile.setPassword(user.getPassword());
			userProfile.setRole(user.getRole());
			userProfile.setUpdateDt(user.getUpdateDt());
			userProfile.setUpdateId(user.getUpdateId());
			userProfile.setUserId(user.getUserId());

			tokenDto.setUser(userProfile);
		}

		return tokenDto;
	}


	protected boolean isExpired(OAuth2RefreshToken refreshToken) {
		if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
			ExpiringOAuth2RefreshToken expiringToken = (ExpiringOAuth2RefreshToken) refreshToken;
			return expiringToken.getExpiration() == null
					|| System.currentTimeMillis() > expiringToken.getExpiration().getTime();
		}
		return false;
	}


	private Collection<OAuth2AccessToken> enhance(Collection<OAuth2AccessToken> tokens) {
		Collection<OAuth2AccessToken> result = new ArrayList<>();
		for (OAuth2AccessToken prototype : tokens) {
			DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(prototype);
			OAuth2Authentication authentication = tokenStore.readAuthentication(token);
			if (authentication == null) {
				continue;
			}
			String clientId = authentication.getOAuth2Request().getClientId();
			if (clientId != null) {
				Map<String, Object> map = new HashMap<>(token.getAdditionalInformation());
				map.put("client_id", clientId);
				token.setAdditionalInformation(map);
				result.add(token);
			}
		}
		return result;
	}


	/*
	 * FIXME: To be determine for token validity protected int
	 * getAccessTokenValiditySeconds(OAuth2Request clientAuth) { if
	 * (clientDetailsService != null) { ClientDetails client =
	 * clientDetailsService.loadClientByClientId(clientAuth.getClientId());
	 * Integer validity = client.getAccessTokenValiditySeconds(); if (validity
	 * != null) { return validity; } } return accessTokenValiditySeconds; }
	 *
	 *
	 * public int getAccessTokenValiditySeconds() { return
	 * accessTokenValiditySeconds; }
	 *
	 *
	 * public void setAccessTokenValiditySeconds(int
	 * accessTokenValiditySeconds) { this.accessTokenValiditySeconds =
	 * accessTokenValiditySeconds; }
	 */

	public TokenStore getTokenStore() {
		return tokenStore;
	}


	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}


	public ConsumerTokenServices getTokenServices() {
		return tokenServices;
	}


	public void setTokenServices(ConsumerTokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}


	public DefaultTokenServices getDefaultTokenServices() {
		return defaultTokenServices;
	}


	public void setDefaultTokenServices(DefaultTokenServices defaultTokenServices) {
		this.defaultTokenServices = defaultTokenServices;
	}


	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}


	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}


	public TokenEnhancer getAccessTokenEnhancer() {
		return accessTokenEnhancer;
	}


	public void setAccessTokenEnhancer(TokenEnhancer accessTokenEnhancer) {
		this.accessTokenEnhancer = accessTokenEnhancer;
	}

}