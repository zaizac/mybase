/*
 * Copyright 2012 SURFnet bv, The Netherlands
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baseboot.idm.sdk.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


/**
 * Holder and parser for the credential pair expected in a Basic and Bearer Auth
 * header.
 */
public class AuthCredentials {

	private static final char SEMI_COLON = ':';

	private static final int BASIC_AUTH_PREFIX_LENGTH = "Basic ".length();

	public static final String TOKEN_TYPE_BASIC = "Basic";

	public static final String TOKEN_TYPE_STATIC = "Static";

	private String client;

	private String token;

	private String secKey;


	private static class NullAuthCredentials extends AuthCredentials {

		private NullAuthCredentials() {
			super(null, null, null);
		}


		@Override
		public boolean isValid() {
			return true;
		}


		@Override
		public boolean isNull() {
			return true;
		}
	}


	public static AuthCredentials createCredentialsFromHeader(final String authorizationHeader, String type) {
		if (authorizationHeader == null) {
			return new NullAuthCredentials();
		}

		if (authorizationHeader.length() < BASIC_AUTH_PREFIX_LENGTH) {
			return new AuthCredentials(null, null, null);
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


	public AuthCredentials(String client, String token, String secKey) {
		super();
		this.client = client;
		this.token = token;
		this.secKey = secKey;
	}


	/**
	 * @return {@code true} if this is a valid credential
	 */
	public boolean isValid() {
		return !StringUtils.isBlank(client) && (!StringUtils.isBlank(token) || !StringUtils.isBlank(secKey));
	}


	public boolean isNull() {
		return false;
	}


	public String getClient() {
		return client;
	}


	public String getToken() {
		return token;
	}


	public String getSecKey() {
		return secKey;
	}


	@Override
	public String toString() {
		return "Clients [client=" + client + "]";
	}


	public static String authorizationBasic(String clientId, String secKey) {
		String concatted = clientId + ":" + secKey;
		return "Basic " + new String(Base64.encodeBase64(concatted.getBytes()));
	}

}