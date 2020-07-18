/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bstsb.notify.constants;



/**
 * The Enum SmsStatusTypeEnum.
 *
 * @author Shukri
 */
public enum SmsStatusTypeEnum {

	/** The status 1. */
	STATUS_1("ACCOUNT_DEACTIVATED"),
	
	/** The status 2. */
	STATUS_2("INSUFFICIENT_CREDIT"),
	
	/** The status 3. */
	STATUS_3("UNAUTHORIZED_DESTINATION"),
	
	/** The status 4. */
	STATUS_4("RECIEVER_NOT_WHITELISTED_CUSTOMER_ACCOUNT"),
	
	/** The status 5. */
	STATUS_5("MSISDN_BLACKLISTED_IN_SYSTEM"),
	
	/** The status 24. */
	STATUS_24("MSISDN_PROBLEM"),
	
	/** The status 30. */
	STATUS_30("SENDER_BLACKLISTED_SYSTEM"),
	
	/** The status 31. */
	STATUS_31("CONTENT_KEYWORD_BLACKLISTED"),
	
	/** The status 32. */
	STATUS_32("UNSUPPORTED_NETWORK");

	/** The value. */
	private String value;


	/**
	 * <p>
	 * </p>.
	 *
	 * @param type the type
	 */
	private SmsStatusTypeEnum(String type) {
		value = type;
	}


	/**
	 * <p>
	 * Returns the String value for the DIentifier for the given Enum
	 * </p>.
	 *
	 * @return the SMS notification status type
	 */
	public String getSMSNotificationStatusType() {
		return value;
	}


	/**
	 * <p>
	 * validates if the SMS notification status type is one listed in enum
	 * </p>.
	 *
	 * @param smsNotificationStatusType the sms notification status type
	 * @return true, if successful
	 */
	public static boolean validSMSNotificationStatusType(String smsNotificationStatusType) {

		for (SmsStatusTypeEnum statusType : SmsStatusTypeEnum.values()) {
			if (statusType.name().equalsIgnoreCase(smsNotificationStatusType)) {
				return true;
			}
		}
		return false;
	}
}
