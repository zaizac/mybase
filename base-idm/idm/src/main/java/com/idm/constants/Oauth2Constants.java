package com.idm.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class Oauth2Constants {

	private Oauth2Constants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String GRANT_TYPE_PWORD = "password";

	public static final String GRANT_TYPE_CLIENT = "client_credentials";

	public static final String TOKEN_TYPE_BASIC = "Basic";

	public static final String TOKEN_TYPE_STATIC = "Static";

}