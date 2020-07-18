/*
 * Copyright 2019. 
 */
package com.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


/**
 * Holder and parser for the credential pair expected in a Basic and Bearer Auth
 * header.
 */
public class AuthCredentials {

	/** The Constant SEMI_COLON. */
	private static final char SEMI_COLON = ':';

	/** The Constant BASIC_AUTH_PREFIX_LENGTH. */
	private static final int BASIC_AUTH_PREFIX_LENGTH = "Basic ".length();

	/** The Constant TOKEN_TYPE_BASIC. */
	public static final String TOKEN_TYPE_BASIC = "Basic";

	/** The Constant TOKEN_TYPE_STATIC. */
	public static final String TOKEN_TYPE_STATIC = "Static";

	/** The client. */
	private String client;

	/** The token. */
	private String token;

	/** The sec key. */
	private String secKey;


	/**
	 * The Class NullAuthCredentials.
	 */
	private static class NullAuthCredentials extends AuthCredentials {

		/**
		 * Instantiates a new null auth credentials.
		 */
		private NullAuthCredentials() {
			super(null, null, null);
		}


		/* (non-Javadoc)
		 * @see com.util.AuthCredentials#isValid()
		 */
		@Override
		public boolean isValid() {
			return true;
		}


		/* (non-Javadoc)
		 * @see com.util.AuthCredentials#isNull()
		 */
		@Override
		public boolean isNull() {
			return true;
		}
	}


	/** The Constant INVALID_CREDENTIALS. */
	private static final AuthCredentials INVALID_CREDENTIALS = new AuthCredentials(null, null, null);


	/**
	 * Creates the credentials from header.
	 *
	 * @param authorizationHeader the authorization header
	 * @param type the type
	 * @return the auth credentials
	 */
	public static AuthCredentials createCredentialsFromHeader(final String authorizationHeader, String type) {
		if (authorizationHeader == null) {
			return new NullAuthCredentials();
		}

		if (authorizationHeader.length() < BASIC_AUTH_PREFIX_LENGTH) {
			return INVALID_CREDENTIALS;
		}

		String authPart = authorizationHeader.substring(BASIC_AUTH_PREFIX_LENGTH);
		String clientToken = new String(Base64.decodeBase64(authPart.getBytes()));
		int index = clientToken.indexOf(SEMI_COLON);

		String client = clientToken.substring(0, index);
		String token = null;
		String secKey = null;

		if (type == TOKEN_TYPE_BASIC) {
			token = clientToken.substring(index + 1);
		} else {
			secKey = clientToken.substring(index + 1);
		}

		return new AuthCredentials(client, token, secKey);
	}


	/**
	 * Instantiates a new auth credentials.
	 *
	 * @param client the client
	 * @param token the token
	 * @param secKey the sec key
	 */
	public AuthCredentials(String client, String token, String secKey) {
		super();
		this.client = client;
		this.token = token;
		this.secKey = secKey;
	}


	/**
	 * Checks if is valid.
	 *
	 * @return {@code true} if this is a valid credential
	 */
	public boolean isValid() {
		return !StringUtils.isBlank(client) && (!StringUtils.isBlank(token) || !StringUtils.isBlank(secKey));
	}


	/**
	 * Checks if is null.
	 *
	 * @return true, if is null
	 */
	public boolean isNull() {
		return false;
	}


	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public String getClient() {
		return client;
	}


	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}


	/**
	 * Gets the sec key.
	 *
	 * @return the sec key
	 */
	public String getSecKey() {
		return secKey;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Clients [client=" + client + "]";
	}


	/**
	 * Authorization basic.
	 *
	 * @param clientId the client id
	 * @param secKey the sec key
	 * @return the string
	 */
	public static String authorizationBasic(String clientId, String secKey) {
		String concatted = clientId + ":" + secKey;
		return "Basic " + new String(Base64.encodeBase64(concatted.getBytes()));
	}

}