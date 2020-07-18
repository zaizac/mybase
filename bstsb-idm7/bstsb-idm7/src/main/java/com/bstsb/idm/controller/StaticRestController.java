/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.Oauth2Constants;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.util.AuthCredentials;
import com.bstsb.util.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.STATIC)
public class StaticRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticRestController.class);

	@Autowired
	private ClientDetailsService clientDetailsService;

	private OAuth2RequestFactory oAuth2RequestFactory;


	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Boolean staticPage(HttpServletRequest request) {

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
		boolean staticPage = false;
		if (secKey.equals(client.getClientSecret())) {
			staticPage = true;
		}
		return staticPage;
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