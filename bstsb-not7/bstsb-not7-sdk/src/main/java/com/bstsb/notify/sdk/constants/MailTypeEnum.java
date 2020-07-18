/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.constants;


/**
 * The Enum MailTypeEnum.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum MailTypeEnum {

	/** The mail. */
	MAIL("mail"),
	/** The sms. */
	SMS("sms"),
	/** The fcm. */
	FCM("fcm"),;

	/** The type. */
	private final String type;


	/**
	 * Instantiates a new mail type enum.
	 *
	 * @param type
	 *             the type
	 */
	private MailTypeEnum(String type) {
		this.type = type;
	}


	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
