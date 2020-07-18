/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRDefaultScriptlet;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ConvertToWord extends JRDefaultScriptlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertToWord.class);

	private static final String[] ones = { "", " one", " two", " three", " four", " five", " six", " seven", " eight",
			" nine" };

	static String numberInWord = "";

	static String inpstr = "";

	static int inputLength;

	static String temp = "";


	public ConvertToWord() {
	}


	public static String readNumber(double xDouble) {
		long x = (long) xDouble;
		inpstr = ("" + x);
		inpstr = inpstr.trim();
		inputLength = inpstr.length();
		numberInWord = "";

		if (inpstr.substring(0, 1).trim().equalsIgnoreCase("-")) {
			inpstr = inpstr.substring(1);
			inputLength -= 1;
		}

		getDigits(inputLength);
		return numberInWord;
	}


	public static void getDigits(int x) {
		if (x == 1) {
			ones(inpstr);

		} else if (x == 2) {
			tens(inpstr);

		} else if (x == 3) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " hundred";
			}
			tens(inpstr.substring(1, 3));

		} else if (x == 4) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " thousand";
			}
			ones(inpstr.substring(1, 2));
			if (!"0".equals(inpstr.substring(1, 2))) {
				numberInWord += " hundred";
			}
			tens(inpstr.substring(2, 4));

		} else if (x == 5) {
			tens(inpstr.substring(0, 2));
			if (!"00".equals(inpstr.substring(0, 2))) {
				numberInWord += " thousand";
			}
			ones(inpstr.substring(2, 3));
			if (!"0".equals(inpstr.substring(2, 3))) {
				numberInWord += " hundred";
			}
			tens(inpstr.substring(3, 5));

		} else if (x == 6) {
			tens(inpstr.substring(0, 3));
			if (!"000".equals(inpstr.substring(0, 3))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(3, 6));

		} else if (x == 7) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(1, 4));
			if (!"000".equals(inpstr.substring(1, 4))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(4, 7));

		} else if (x == 8) {
			tens(inpstr.substring(0, 2));
			if (!"00".equals(inpstr.substring(0, 2))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(2, 5));
			if (!"000".equals(inpstr.substring(2, 5))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(5, 8));

		} else if (x == 9) {
			tens(inpstr.substring(0, 3));
			if (!"000".equals(inpstr.substring(0, 3))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(3, 6));
			if (!"000".equals(inpstr.substring(3, 6))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(6, 9));

		} else if (x == 10) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(1, 4));
			if (!"000".equals(inpstr.substring(1, 4))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(4, 7));
			if (!"000".equals(inpstr.substring(4, 7))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(7, 10));

		} else if (x == 11) {
			tens(inpstr.substring(0, 2));
			if (!"00".equals(inpstr.substring(0, 2))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(2, 5));
			if (!"00".equals(inpstr.substring(2, 4))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(5, 8));
			if (!"00".equals(inpstr.substring(5, 7))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(8, 11));

		} else if (x == 12) {
			tens(inpstr.substring(0, 3));
			if (!"000".equals(inpstr.substring(0, 3))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(3, 6));
			if (!"000".equals(inpstr.substring(3, 6))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(6, 9));
			if (!"000".equals(inpstr.substring(6, 9))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(9, 12));

		} else if (x == 13) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " trillion";
			}
			tens(inpstr.substring(1, 4));
			if (!"000".equals(inpstr.substring(1, 4))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(4, 7));
			if (!"000".equals(inpstr.substring(4, 7))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(7, 10));
			if (!"000".equals(inpstr.substring(7, 10))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(10, 13));

		} else if (x == 14) {
			tens(inpstr.substring(0, 2));
			if (!"00".equals(inpstr.substring(0, 2))) {
				numberInWord += " trillion";
			}
			tens(inpstr.substring(2, 5));
			if (!"000".equals(inpstr.substring(2, 5))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(5, 8));
			if (!"000".equals(inpstr.substring(5, 8))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(8, 11));
			if (!"000".equals(inpstr.substring(8, 11))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(11, 14));

		} else if (x == 15) {
			tens(inpstr.substring(0, 3));
			if (!"000".equals(inpstr.substring(0, 3))) {
				numberInWord += " trillion";
			}
			tens(inpstr.substring(3, 6));
			if (!"000".equals(inpstr.substring(3, 6))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(6, 9));
			if (!"000".equals(inpstr.substring(6, 9))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(9, 12));
			if (!"000".equals(inpstr.substring(9, 12))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(12, 15));

		} else if (x == 16) {
			ones(inpstr.substring(0, 1));
			if (!"0".equals(inpstr.substring(0, 1))) {
				numberInWord += " quadrillion";
			}
			tens(inpstr.substring(1, 4));
			if (!"000".equals(inpstr.substring(1, 4))) {
				numberInWord += " trillion";
			}
			tens(inpstr.substring(4, 7));
			if (!"000".equals(inpstr.substring(4, 7))) {
				numberInWord += " billion";
			}
			tens(inpstr.substring(7, 10));
			if (!"000".equals(inpstr.substring(7, 10))) {
				numberInWord += " million";
			}
			tens(inpstr.substring(10, 13));
			if (!"000".equals(inpstr.substring(10, 13))) {
				numberInWord += " thousand";
			}
			tens(inpstr.substring(13, 16));
		} else {
			LOGGER.error("  Your number is too big for me to spell ");
		}
	}


	public static void tens2(String x) {
		temp = x;

		if ((x.length() > 2) && (x.charAt(0) != '0')) {
			ones(x.substring(0, 1));
			numberInWord += " hundred";
			tens(x.substring(1, 3));

			x = "";
			temp = "   ";

		} else if ((x.length() > 2) && (x.charAt(0) == '0')) {
			tens(x.substring(1, 3));

			x = "";
			temp = "   ";

		} else {
			x = x.substring(0, 1);
		}

		if ("2".equals(x)) {
			numberInWord += " twenty";
		} else if ("3".equals(x)) {
			numberInWord += " thirty";
		} else if ("4".equals(x)) {
			numberInWord += " fourty";
		} else if ("5".equals(x)) {
			numberInWord += " fifty";
		} else if ("6".equals(x)) {
			numberInWord += " sixty";
		} else if ("7".equals(x)) {
			numberInWord += " seventy";
		} else if ("8".equals(x)) {
			numberInWord += " eighty";
		} else if ("9".equals(x)) {
			numberInWord += " ninety";
		}

		ones(temp.substring(1, 2));
	}


	public static void tens(String x) {
		if ("10".equals(x)) {
			numberInWord += " ten";
		} else if ("20".equals(x)) {
			numberInWord += " twenty";
		} else if ("30".equals(x)) {
			numberInWord += " thirty";
		} else if ("40".equals(x)) {
			numberInWord += " fourty";
		} else if ("50".equals(x)) {
			numberInWord += " fifty";
		} else if ("60".equals(x)) {
			numberInWord += " sixty";
		} else if ("70".equals(x)) {
			numberInWord += " seventy";
		} else if ("80".equals(x)) {
			numberInWord += " eighty";
		} else if ("90".equals(x)) {
			numberInWord += " ninty";
		} else if ("11".equals(x)) {
			numberInWord += " eleven";
		} else if ("12".equals(x)) {
			numberInWord += " twelve";
		} else if ("13".equals(x)) {
			numberInWord += " thirteen";
		} else if ("14".equals(x)) {
			numberInWord += " fourteen";
		} else if ("15".equals(x)) {
			numberInWord += " fifteen";
		} else if ("16".equals(x)) {
			numberInWord += " sixteen";
		} else if ("17".equals(x)) {
			numberInWord += " seventeen";
		} else if ("18".equals(x)) {
			numberInWord += " eighteen";
		} else if ("19".equals(x)) {
			numberInWord += " ninteen";
		} else {
			tens2(x);
		}
	}


	public static void ones(String x) {
		numberInWord += ones[Integer.valueOf(x)];
	}

}
