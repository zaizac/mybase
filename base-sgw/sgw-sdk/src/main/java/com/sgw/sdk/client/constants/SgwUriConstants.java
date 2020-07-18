/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwUriConstants {

	private SgwUriConstants() {
		throw new IllegalStateException("SgwUriConstants class");
	}


	public static final String SERVICE = "/service";

	public static final String GET_LOGIN = SERVICE + "/login";

	public static final String GET_MOBILE_NOTIFY = SERVICE + "/notification/mobile";
	
}