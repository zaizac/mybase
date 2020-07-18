/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baseboot.notify.util;
/**
 *
 * @author Shukri
 */
public enum SMSNotificationStatusType {
	
	STATUS_1("ACCOUNT_DEACTIVATED"), 
	STATUS_2("INSUFFICIENT_CREDIT"),
	STATUS_3("UNAUTHORIZED_DESTINATION"),
	STATUS_4("RECIEVER_NOT_WHITELISTED_CUSTOMER_ACCOUNT"),
	STATUS_5("MSISDN_BLACKLISTED_IN_SYSTEM"),
	STATUS_24("MSISDN_PROBLEM"),
	STATUS_30("SENDER_BLACKLISTED_SYSTEM"),
	STATUS_31("CONTENT_KEYWORD_BLACKLISTED"),
	STATUS_32("UNSUPPORTED_NETWORK");
	
	private String value;
	
	/**
	 * 
	 * <p>
	 * </p>
	 *
	 * @param value
	 */
	private SMSNotificationStatusType(String type) {
		this.value = type;
	}
	
	/**
	 * 
	 * <p>
	 * Returns the String value for the DIentifier for the given Enum
	 * </p>
	 * @param status
	 * @return
	 */
	public String getSMSNotificationStatusType() {
		return this.value;
	}
	
	/**
	 * 
	 * <p>
	 * validates if the SMS notification status type is one listed in enum
	 * </p>
	 * 
	 * @param searchWorkerAction
	 * @return
	 */
	public static boolean validSMSNotificationStatusType(String smsNotificationStatusType) {

		for(SMSNotificationStatusType statusType : SMSNotificationStatusType.values()) {
			if (statusType.name().equalsIgnoreCase(smsNotificationStatusType)) {
				return true;
			}
		}
		return false;
	}
}
