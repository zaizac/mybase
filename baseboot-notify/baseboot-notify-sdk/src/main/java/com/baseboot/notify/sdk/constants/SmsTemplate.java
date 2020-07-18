/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum SmsTemplate {

	SMS_REALTIME(null),
	EQM_INTRVW_SCH(
			"RM0.00 SPPA(eQuota) interview details:\nDate: {0}\nTime: 9:00am-5:00pm \nVenue: OSC, KDN, Putrajaya.Thank you."),

	;

	private String content;


	private SmsTemplate(String content) {
		this.content = content;
	}


	public String getContent() {
		return content;
	}

}