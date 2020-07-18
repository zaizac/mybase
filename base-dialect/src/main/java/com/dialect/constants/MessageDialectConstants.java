/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.dialect.constants;


/**
 * Constant file for Locale Messages. Variable must be available in
 * locale_*.properties
 *
 * @author mary.jane
 * @since Jan 3, 2019
 */
public class MessageDialectConstants {

	private MessageDialectConstants() {
		throw new IllegalStateException(MessageDialectConstants.class.getName());
	}


	public static final String SLCT_FILE = "lbl.slct.file";

	public static final String CHNG_FILE = "lbl.chng.file";

	public static final String RMV_FILE = "lbl.file.rmv";

	public static final String CAPTCHA = "lbl.cptch";

	public static final String BTN_VERIFY = "btn.cmn.ver";

	public static final String OTP_RESET = "lbl.otp.reset";

	public static final String OTP_VERIFY = "lbl.otp.vrfy";

	public static final String OTP_VERIFIED = "lbl.otp.vrfd";
}
