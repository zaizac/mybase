/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwUriConstants {

	private SgwUriConstants() {
		throw new IllegalStateException("SgwUriConstants class");
	}


	public static final String SERVICE = "/service";

	public static final String GET_MC_LOGIN = SERVICE + "/login";

}