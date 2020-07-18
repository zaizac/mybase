/**
 * Copyright 2019. 
 */
package com.notify.sdk.constants;


/**
 * The Enum SmsTemplate.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum SmsTemplate {

	/** The sms realtime. */
	SMS_REALTIME(null),

	/** The eqm intrvw sch. */
	EQM_INTRVW_SCH(
			"RM0.00 SPPA(eQuota) interview details:\nDate: {0}\nTime: 9:00am-5:00pm \nVenue: OSC, KDN, Putrajaya.Thank you."),

	;

	/** The content. */
	private String content;


	/**
	 * Instantiates a new sms template.
	 *
	 * @param content
	 *             the content
	 */
	private SmsTemplate(String content) {
		this.content = content;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

}