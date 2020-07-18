/**
 * Copyright 2019. 
 */
package com.util;


import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;


/**
 * The Class UidGenerator.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public class UidGenerator {

	/** The months cd. */
	private static String monthsCd = "ABCDEFGHIJKL";


	/**
	 * Instantiates a new uid generator.
	 */
	private UidGenerator() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Return UUID random string.
	 *
	 * @return the message id
	 */
	public static String getMessageId() {
		return String.valueOf(UUID.randomUUID());
	}


	/**
	 * Generate UID String concatenated with prefix param value.
	 *
	 * @param prefix the prefix
	 * @return the string
	 */
	public static String generateUid(String prefix) {
		DateTime dt = new DateTime();
		String dom = BaseUtil.getStr(dt.getDayOfMonth());
		if (dom.length() == 1) {
			dom = "0" + dom;
		}
		return prefix + monthsCd.substring(dt.getMonthOfYear() - 1, dt.getMonthOfYear()) + dom + dt.getMillisOfDay();
	}


	/**
	 * Generates random 6 digit number in String.
	 *
	 * @return the string
	 */
	public static String generateRandomNoInStr() {
		return BaseUtil.getStr(new Random().nextInt(899999) + 100000);
	}

}
