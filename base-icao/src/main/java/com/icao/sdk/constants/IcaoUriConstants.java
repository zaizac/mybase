/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 7, 2018
 */
public class IcaoUriConstants {

	private IcaoUriConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String SERVICE = "/service";

	public static final String API_SERVICES = SERVICE + "/api";

	public static final String USER_LOGIN = API_SERVICES + "/user/login";

	public static final String USER_LOGOUT = API_SERVICES + "/user/logout";

	public static final String PERSON_CREATE = API_SERVICES + "/person/create";

	public static final String PERSON_FACE_INFO = API_SERVICES + "/person/face-info";

	public static final String FACE_IMG_VERIFY = API_SERVICES + "/verify";

}