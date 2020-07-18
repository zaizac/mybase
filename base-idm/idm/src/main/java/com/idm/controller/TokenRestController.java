package com.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.idm.constants.IdmTxnCodeConstants;
import com.idm.constants.Oauth2Constants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmProfile;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.Token;
import com.idm.sdk.model.UserProfile;
import com.idm.service.IdmProfileService;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.TOKENS)
public class TokenRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenRestController.class);

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private ConsumerTokenServices tokenServices;

	@Autowired
	@Qualifier("defaultAuthorizationServerTokenServices")
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
	@DeleteMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Boolean revokeToken(HttpServletRequest request) {
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
	@GetMapping(value = "/clients", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Token> listTokensForClient(HttpServletRequest request,
			@RequestParam(value = "embededUser", required = false) boolean embededUser,
			@RequestParam(value = "embededRole", required = false) boolean embededRole) {

		List<Token> tokens = new ArrayList<>();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String clientId = basicAuthCredentials.getClient();

		Collection<OAuth2AccessToken> listTokensForClient = enhance(tokenStore.findTokensByClientId(clientId));
		if (listTokensForClient.isEmpty()) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		for (OAuth2AccessToken oAuth2AccessToken : listTokensForClient) {
			Token tokenDto = new Token();
			tokenDto.setAccessToken(oAuth2AccessToken.getValue());
			tokenDto.setExpiresIn(String.valueOf(oAuth2AccessToken.getExpiresIn()));
			tokenDto.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
			tokenDto.setScope(String.valueOf(oAuth2AccessToken.getScope()));
			tokenDto.setTokenType(oAuth2AccessToken.getTokenType());

			getUserProfile(embededUser, oAuth2AccessToken, tokenDto);

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
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Token generatedAccessToken(HttpServletRequest request, HttpServletResponse header) {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_TOKEN_NEW);
		Token tokenDto = new Token();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String token = basicAuthCredentials.getToken();
		String client = basicAuthCredentials.getClient();

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

		if (accessToken == null) {
			LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I404IDM113.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM113);
		}

		if (accessToken.getRefreshToken() == null) {
			LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I404IDM107.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM107, new String[] { token });
		}

		OAuth2Authentication authentication = tokenStore
				.readAuthenticationForRefreshToken(accessToken.getRefreshToken());

		if (isExpired(accessToken.getRefreshToken())) {
			LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I400IDM109.getMessage());
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
	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Token findAccessToken(HttpServletRequest request,
			@RequestParam(value = "embededUser", required = false) String embededUser,
			@RequestParam(value = "embededRole", required = false) boolean embededRole) {
		Token tokenDto = new Token();

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String token = basicAuthCredentials.getToken();

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

		if (accessToken == null) {
			LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I404IDM115.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM115, new String[] { token });
		}

		if (accessToken.isExpired()) {
			LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I404IDM111.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I404IDM111, new String[] { token });
		}

		tokenDto.setAccessToken(accessToken.getValue());
		tokenDto.setExpiresIn(String.valueOf(accessToken.getExpiresIn()));
		tokenDto.setRefreshToken(accessToken.getRefreshToken().getValue());
		tokenDto.setScope(accessToken.getScope().toString());
		tokenDto.setTokenType(accessToken.getTokenType());

		getUserProfile(true, accessToken, tokenDto);

		return tokenDto;
	}


	/**
	 * Validate Access Token
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/validate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Token validateAccessToken(HttpServletRequest request) {
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
				IdmProfile user = idmUserProfileService.findByUserId(userId);
				if (!BaseUtil.isObjNull(user)) {
					UserProfile userProfile = new UserProfile();
					userProfile.setCntryCd(user.getCntryCd());
					userProfile.setCreateDt(user.getCreateDt());
					userProfile.setCreateId(user.getCreateId());
					userProfile.setDob(user.getDob());
					userProfile.setEmail(user.getEmail());
					userProfile.setFirstName(user.getFirstName());
					userProfile.setLastName(user.getLastName());
					userProfile.setGender(user.getGender());
					userProfile.setContactNo(user.getContactNo());
					userProfile.setPassword(user.getPassword());
					userProfile.setRole(user.getRole());
					userProfile.setUpdateDt(user.getUpdateDt());
					userProfile.setUpdateId(user.getUpdateId());
					userProfile.setUserId(user.getUserId());
					userProfile.setAuthOption(user.getAuthOption());
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
		getUserProfile(true, accessToken, tokenDto);
		return tokenDto;
	}


	private Token getUserProfile(boolean embededUser, OAuth2AccessToken accessToken, Token tokenDto) {
		if (embededUser) {
			OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
			IdmProfile user = idmUserProfileService.findByUserId(oAuth2Authentication.getName());
			if (user == null) {
				LOGGER.info(BaseConstants.LOG_IDM_EXCEPTION, IdmErrorCodeEnum.I404IDM102.getMessage());
				throw new IdmException(IdmErrorCodeEnum.I404IDM102,
						new String[] { oAuth2Authentication.getName() });
			}

			UserProfile userProfile = new UserProfile();
			userProfile.setCntryCd(user.getCntryCd());
			userProfile.setCreateDt(user.getCreateDt());
			userProfile.setCreateId(user.getCreateId());
			userProfile.setDob(user.getDob());
			userProfile.setEmail(user.getEmail());
			userProfile.setFirstName(user.getFirstName());
			userProfile.setLastName(user.getLastName());
			userProfile.setGender(user.getGender());
			userProfile.setContactNo(user.getContactNo());
			userProfile.setPassword(user.getPassword());
			userProfile.setRole(user.getRole());
			userProfile.setUpdateDt(user.getUpdateDt());
			userProfile.setUpdateId(user.getUpdateId());
			userProfile.setUserId(user.getUserId());
			userProfile.setAuthOption(user.getAuthOption());
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