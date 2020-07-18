/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.constants;


/**
 * The Class SmsConstants.
 *
 * @author Shukri
 */
public final class SmsConstants {

	/**
	 * Instantiates a new sms constants.
	 */
	private SmsConstants() {
		throw new IllegalStateException("SmsConstants Utility class");
	}


	/** The Constant ICE_GATEWAY_USER_KEY. */
	public static final String ICE_GATEWAY_USER_KEY = "gw-username";

	/** The Constant ICE_GATEWAY_PWORD_KEY. */
	public static final String ICE_GATEWAY_PWORD_KEY = "gw-password";

	/** The Constant ICE_GATEWAY_TO_CONTACT_KEY. */
	public static final String ICE_GATEWAY_TO_CONTACT_KEY = "gw-to";

	/** The Constant ICE_GATEWAY_FROM_CONTACT_KEY. */
	public static final String ICE_GATEWAY_FROM_CONTACT_KEY = "gw-from";

	/** The Constant ICE_GATEWAY_CODING_KEY. */
	public static final String ICE_GATEWAY_CODING_KEY = "gw-coding";

	/** The Constant ICE_GATEWAY_CODING_VAL. */
	public static final String ICE_GATEWAY_CODING_VAL = "1";

	/** The Constant ICE_GATEWAY_MSG_KEY. */
	public static final String ICE_GATEWAY_MSG_KEY = "gw-text";

	/** The Constant CHARACTER_ENCODE_FORMAT_UTF. */
	public static final String CHARACTER_ENCODE_FORMAT_UTF = "UTF-8";

	/** The Constant ICE_GATEWAY_FROM_VAL. */
	public static final String ICE_GATEWAY_FROM_VAL = "SPPA";

	/** The Constant AMPERSAND_STRING_SEPERATOR. */
	public static final String AMPERSAND_STRING_SEPERATOR = "&";

	/** The Constant EQUALS_STRING_SEPERATOR. */
	public static final String EQUALS_STRING_SEPERATOR = "=";

	/** The Constant STATUS_PREFIX. */
	public static final String STATUS_PREFIX = "STATUS_";

	/** The Constant OTP_SUCCESS_STATUS. */
	public static final String OTP_SUCCESS_STATUS = "SMS Sent Successfully";

}