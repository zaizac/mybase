/**
 *
 */
package com.baseboot.idm.config.oauth;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


/**
 * @author mary.jane
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;


	@Autowired
	public AuthorizationServerConfig(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	@Bean
	public TokenStore tokenStore() {
		JdbcTokenStore token = new JdbcTokenStore(dataSource);
		token.setInsertAccessTokenSql(IdmOauthSql.DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT);
		token.setSelectAccessTokenSql(IdmOauthSql.DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT);
		token.setSelectAccessTokenAuthenticationSql(IdmOauthSql.DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT);
		token.setSelectAccessTokenFromAuthenticationSql(
				IdmOauthSql.DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT);
		token.setSelectAccessTokensFromUserNameAndClientIdSql(
				IdmOauthSql.DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT);
		token.setSelectAccessTokensFromUserNameSql(IdmOauthSql.DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT);
		token.setSelectAccessTokensFromClientIdSql(IdmOauthSql.DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT);
		token.setDeleteAccessTokenSql(IdmOauthSql.DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT);
		token.setInsertRefreshTokenSql(IdmOauthSql.DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT);
		token.setSelectRefreshTokenSql(IdmOauthSql.DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT);
		token.setSelectRefreshTokenAuthenticationSql(
				IdmOauthSql.DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT);
		token.setDeleteRefreshTokenSql(IdmOauthSql.DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT);
		token.setDeleteAccessTokenFromRefreshTokenSql(
				IdmOauthSql.DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT);
		return token;
	}


	@Bean
	public ApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}


	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {
		JdbcAuthorizationCodeServices authCode = new JdbcAuthorizationCodeServices(dataSource);
		authCode.setDeleteAuthenticationSql(IdmOauthSql.DEFAULT_DELETE_AUTH_CODE_STATEMENT);
		authCode.setInsertAuthenticationSql(IdmOauthSql.DEFAULT_INSERT_AUTH_CODE_STATEMENT);
		authCode.setSelectAuthenticationSql(IdmOauthSql.DEFAULT_SELECT_AUTH_CODE_STATEMENT);
		return authCode;
	}


	@Bean
	public ClientTokenServices clientTokenServices() {
		JdbcClientTokenServices clientToken = new JdbcClientTokenServices(dataSource);
		clientToken.setSelectAccessTokenSql(IdmOauthSql.DEFAULT_CLIENT_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT);
		clientToken.setDeleteAccessTokenSql(IdmOauthSql.DEFAULT_CLIENT_TOKEN_DELETE_STATEMENT);
		clientToken.setInsertAccessTokenSql(IdmOauthSql.DEFAULT_CLIENT_TOKEN_INSERT_STATEMENT);
		return clientToken;
	}


	@Bean
	public ClientDetailsService clientDetailsService() {
		JdbcClientDetailsService clientDtl = new JdbcClientDetailsService(dataSource);
		clientDtl.setDeleteClientDetailsSql(IdmOauthSql.DEFAULT_DELETE_STATEMENT);
		clientDtl.setFindClientDetailsSql(IdmOauthSql.DEFAULT_FIND_STATEMENT);
		clientDtl.setUpdateClientDetailsSql(IdmOauthSql.DEFAULT_UPDATE_STATEMENT);
		clientDtl.setUpdateClientSecretSql(IdmOauthSql.DEFAULT_UPDATE_SECRET_STATEMENT);
		clientDtl.setInsertClientDetailsSql(IdmOauthSql.DEFAULT_INSERT_STATEMENT);
		clientDtl.setSelectClientDetailsSql(IdmOauthSql.DEFAULT_SELECT_STATEMENT);
		return clientDtl;
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.pathMapping("/oauth/authorize", "/idm/authorize").pathMapping("/oauth/token", "/idm/token")
				.pathMapping("/oauth/check_token", "/idm/check_token")
				.authorizationCodeServices(authorizationCodeServices()).authenticationManager(authenticationManager)
				.tokenStore(tokenStore()).tokenServices(defaultTokenServices()).approvalStoreDisabled();
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}


	@Bean
	@Primary
	public DefaultTokenServices defaultTokenServices() {
		MyTokenService tokenService = new MyTokenService();
		tokenService.setTokenStore(tokenStore());
		tokenService.setSupportRefreshToken(true);
		tokenService.setClientDetailsService(clientDetailsService());
		// tokenService.setTokenEnhancer(tokenEnhancer());
		return tokenService;
	}


	class MyTokenService extends DefaultTokenServices {

		private final Logger LOG = LoggerFactory.getLogger(AuthorizationServerConfig.MyTokenService.class);


		public MyTokenService() {
			// Do Nothing
		}


		@Override
		public OAuth2AccessToken readAccessToken(String accessToken) {
			LOG.info("readAccessToken");
			return super.readAccessToken(accessToken);
		}


		@Override
		public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication)
				throws AuthenticationException {
			LOG.info("createAccessToken");
			OAuth2AccessToken token = super.createAccessToken(authentication);
			// This is where I will add my logic when it hits the breakpoint.
			return token;
		}


		@Override
		public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
				throws AuthenticationException {
			LOG.info("refreshAccessToken");
			return super.refreshAccessToken(refreshTokenValue, tokenRequest);
		}
	}

}