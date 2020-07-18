/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class WebUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);


	private WebUtil() {
		throw new IllegalStateException("Utility class");
	}


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


	public static boolean checkTokenError(Exception e) {
		if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), ex.getInternalErrorCode())
					|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(),
							ex.getInternalErrorCode())) {
				LOGGER.info("checkTokenError: IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
				return true;
			}
		}
		return false;
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
			throw ex;
		}
		return stringBuilder.toString();
	}


	public static int checkDigit(String refNo) {
		if (refNo.length() != 20) {
			return -1;
		}

		String newRefNo = refNo.substring(4, 20);
		String prfx = newRefNo.substring(0, 8);
		String sufx = newRefNo.substring(10, 16);
		int chksum = BaseUtil.getInt(newRefNo.substring(9, 10));
		LOGGER.debug("chksums : {}", chksum);

		String calcSum = prfx + sufx;

		char[] a = calcSum.toCharArray();

		int i;
		int len = a.length;
		int mul;
		int sum;

		int m10;

		if (len != 14) {
			return -1;
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
			LOGGER.debug("{} - {} - {}", a[i], sum, mul);
		}

		m10 = sum % 10;

		if (m10 > 10) {
			m10 = 10 - m10;
		}

		if (m10 == chksum) {
			return 1;
		}

		return 0;

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

		m10 = sum % 10;
		if (m10 > 10) {
			m10 = 10 - m10;
		}
		sb.append(m10);
		sb.append(newTrxnId);
		return sb.toString();
	}

}