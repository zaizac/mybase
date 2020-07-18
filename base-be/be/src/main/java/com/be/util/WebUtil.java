/**
 * Copyright 2019
 */
package com.be.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
public class WebUtil {

	private WebUtil() {
		throw new IllegalStateException("Utility class");
	}


	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);


	public static String getStackTrace(final Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		String[] lines = writer.toString().split("\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(lines.length, 10); i++) {
			sb.append(lines[i]).append("\n");
		}
		return sb.toString();
	}


	public static String getRequestBody(final InputStream is) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = is;
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage());
		}
		return stringBuilder.toString();
	}


	public static String genChksumDigit(String trxnCd, String ctrlGenDt, Integer trxnId) {
		StringBuilder sb = new StringBuilder();
		sb.append(trxnCd);
		sb.append(ctrlGenDt);
		sb.append("-");
		String str = String.valueOf(trxnId);
		String newTrxnId = ("000000" + str).substring(str.length());
		String calcSum = ctrlGenDt + newTrxnId;
		if (calcSum.length() != 14) {
			return null;
		}

		char[] a = calcSum.toCharArray();
		int i;
		int len = a.length;
		int mul;
		int sum;
		int m10;

		if (len != 14) {
			return null;
		}

		mul = 2;
		sum = 0;
		for (i = len - 2; i >= 0; i--) {
			if ((Integer.valueOf(a[i]) * mul) >= 10) {
				sum += ((Integer.valueOf(a[i]) * mul) / 10) + ((Integer.valueOf(a[i]) * mul) % 10);
			} else {
				sum += Integer.valueOf(a[i]) * mul;
			}

			if (mul == 2) {
				mul = 1;
			} else {
				mul = 2;
			}
		}

		LOGGER.info("sum: {}", sum);
		m10 = sum % 10;
		LOGGER.info("m10: {}", sum);
		if (m10 > 10) {
			m10 = 10 - m10;
		}
		LOGGER.info("m10: {}", m10);
		sb.append(m10);

		sb.append(newTrxnId);
		return sb.toString();
	}
}