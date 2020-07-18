/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class SgwTxnCodeConstants {

	private SgwTxnCodeConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String HEADER_MESSAGE_ID = "X-Message-Id";

	public static final String HEADER_AUTHORIZATION = "Authorization";

	public static final String SGW_TEST_URL = "SGW_TEST_URL";

}