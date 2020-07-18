/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.util;


import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.DateUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class UidGenerator implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(UidGenerator.class);

	private Random random;

	private static final String intChar = "123456789";

	private static String monthsCd = "ABCDEFGHIJKL";

	private Integer uidLength;

	private Integer txnIdLength;


	public UidGenerator() {
	}


	public UidGenerator(Integer uidLength, Integer txnIdLength) {
		this.uidLength = uidLength;
		this.txnIdLength = txnIdLength;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		random = SecureRandom.getInstance("SHA1PRNG", "SUN");
	}


	/**
	 * Generate password that contains [a-z], [A-Z], [0-9], [!@#$%^&*]
	 * 
	 * @return
	 */
	public String getGeneratedPwd() {
		StringBuilder uid = new StringBuilder();
		for (int i = 0; i < uidLength; i++) {
			int spot = random.nextInt(intChar.length());
			uid.append(intChar.charAt(spot));
		}
		return uid.toString();
	}


	public String getTransactionId() {

		String randomNum = new Integer(random.nextInt()).toString();
		String id = Long.toString(System.nanoTime());

		StringBuilder sb = new StringBuilder();
		sb.append(randomNum);
		sb.append(id);

		String val = StringUtils.trimAllWhitespace(sb.toString()).replaceAll("-", "");

		String txnCode = "";

		LOGGER.debug("[org.apache.commons.lang.StringUtils.length(val): {} ]",
				org.apache.commons.lang3.StringUtils.length(val));

		if (StringUtils.hasText(val) && org.apache.commons.lang3.StringUtils.isNumeric(val)) {
			if (org.apache.commons.lang3.StringUtils.length(val) > this.getTxnIdLength()) {
				txnCode = org.apache.commons.lang3.StringUtils.substring(val, 0, this.getTxnIdLength());
			} else {
				txnCode = val;
			}
		}

		return txnCode;
	}


	public String generateTxnNo(String module) {
		String txnNo = BaseConstants.EMPTY_STRING;

		DateTime dt = new DateTime();

		if (!BaseUtil.isObjNull(module)) {
			txnNo = module;
		}

		String dom = BaseUtil.getStr(dt.getDayOfMonth());

		if (dom.length() == 1)
			dom = "0" + dom;

		txnNo = dt.getYear() + txnNo + monthsCd.substring(dt.getMonthOfYear() - 1, dt.getMonthOfYear()) + dom
				+ dt.getMillisOfDay();

		LOGGER.debug("generateTxnNo: {}", txnNo);
		return txnNo;
	}


	public String generatePasscode() {
		Integer code = (100000 + random.nextInt(900000));
		return code.toString();
	}


	public String generateOtp() {
		long millSecond = DateUtil.getSQLTimestamp().getTime();
		String millSecondStr = String.valueOf(millSecond);
		String tac = millSecondStr.substring(millSecondStr.length() - 6);
		LOGGER.debug("tac: {}", tac);
		return tac;
	}


	public static String getMessageId() {
		return String.valueOf(UUID.randomUUID());
	}


	public Integer getUidLength() {
		return uidLength;
	}


	public void setUidLength(Integer uidLength) {
		this.uidLength = uidLength;
	}


	public Integer getTxnIdLength() {
		return txnIdLength;
	}


	public void setTxnIdLength(Integer txnIdLength) {
		this.txnIdLength = txnIdLength;
	}


	public Random getRandom() {
		return random;
	}

}