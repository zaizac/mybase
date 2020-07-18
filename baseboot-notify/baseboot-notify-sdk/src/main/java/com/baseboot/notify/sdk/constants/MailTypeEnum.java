/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum MailTypeEnum {

	MAIL("mail"), SMS("sms"),;

	private final String type;


	private MailTypeEnum(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}

}
