/**
 * Copyright 2019
 */
package com.be.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 16, 2018
 */
public class TxnCodeConstants {

	private TxnCodeConstants() {
		throw new IllegalStateException(TxnCodeConstants.class.getName());
	}


	public static final String HEADER_MESSAGE_ID = "X-Message-Id";

	public static final String HEADER_AUTHORIZATION = "Authorization";

}