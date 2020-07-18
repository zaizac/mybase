/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.Oauth2Constants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.util.AuthCredentials;
import com.baseboot.idm.sdk.util.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.STATIC)
public class StaticRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticRestController.class);

	@Autowired
	private ClientDetailsService clientDetailsService;

	private OAuth2RequestFactory oAuth2RequestFactory;


	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Boolean staticPage(HttpServletRequest request) throws Exception {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null) {
			LOGGER.info("IdmException: {}", IdmErrorCodeEnum.I400IDM152.getMessage());
			throw new IdmException(IdmErrorCodeEnum.I400IDM152);
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_STATIC);

		String clientId = authCredentials.getClient();
		String secKey = authCredentials.getSecKey();

		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

		if (secKey.equals(client.getClientSecret())) {
			return true;
		} else {
			return false;
		}

	}


	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}


	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}


	public OAuth2RequestFactory getoAuth2RequestFactory() {
		return oAuth2RequestFactory;
	}


	public void setoAuth2RequestFactory(OAuth2RequestFactory oAuth2RequestFactory) {
		this.oAuth2RequestFactory = oAuth2RequestFactory;
	}
}
