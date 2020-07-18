/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.esb.common;

import java.util.UUID;

import org.joda.time.DateTime;

import com.esb.util.BaseUtil;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class UidGenerator {

	private static String monthsCd = "ABCDEFGHIJKL";
	
	public static String getMessageId() {
		return String.valueOf(UUID.randomUUID());
	}
	
	public static String generateDocRefNo() {
		String txnNo = "DOC";
		DateTime dt = new DateTime();
		String dom = BaseUtil.getStr(dt.getDayOfMonth());
		if(dom.length() == 1) dom = "0" + dom;
		txnNo = dt.getYear() + txnNo + monthsCd.substring(dt.getMonthOfYear()-1,dt.getMonthOfYear()) + dom + dt.getMillisOfDay();
		return txnNo;
	}
	
	// Added Nigel 20160704
	public static String generateSpcRefNo() {
		String txnNo = "SPC";
		DateTime dt = new DateTime();
		String dom = BaseUtil.getStr(dt.getDayOfMonth());
		if(dom.length() == 1) dom = "0" + dom;
		txnNo = dt.getYear() + txnNo + monthsCd.substring(dt.getMonthOfYear()-1,dt.getMonthOfYear()) + dom + dt.getMillisOfDay();
		return txnNo;
	}
}
