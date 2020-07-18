/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.constants;


/**
 * The Class NotUrlConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotUrlConstants {

	/**
	 * Instantiates a new not url constants.
	 */
	private NotUrlConstants() {
		// Block Initialization
	}


	/** The Constant SERVICE_CHECK. */
	public static final String SERVICE_CHECK = "/serviceCheck";

	/** The Constant NOTIFICATIONS. */
	public static final String NOTIFICATIONS = "/notifications";

	/** The Constant NOTIFY_SEND_ALL. */
	public static final String NOTIFY_SEND_ALL = NOTIFICATIONS + "/sendAll";

	/** The Constant NOTIFY_NEW. */
	public static final String NOTIFY_NEW = NOTIFICATIONS + "/new";

	/** The Constant NOTIFY_MAIL. */
	public static final String NOTIFY_MAIL = NOTIFICATIONS + "/mail";

	/** The Constant NOTIFY_MAIL_SEND. */
	public static final String NOTIFY_MAIL_SEND = NOTIFICATIONS + "/mail/send";

	/** The Constant NOTIFY_SMS. */
	public static final String NOTIFY_SMS = NOTIFICATIONS + "/sms";

	/** The Constant NOTIFY_SMS_SEND. */
	public static final String NOTIFY_SMS_SEND = NOTIFICATIONS + "/sms/send";

	/** The Constant MAIL_TEMPLATE. */
	public static final String MAIL_TEMPLATE = NOTIFICATIONS + "/mail/templates";

	/** The Constant UPDATE_MAIL_TEMPLATE. */
	public static final String UPDATE_MAIL_TEMPLATE = NOTIFICATIONS + "/mail/template/update";

	/** The Constant NOTIFY_SEND_ALL_TEST. */
	public static final String NOTIFY_SEND_ALL_TEST = NOTIFICATIONS + "/sendAllTest";

	/** The Constant NOTIFY_CONFIG. */
	public static final String NOTIFY_CONFIG = NOTIFICATIONS + "/config";

	/** The Constant GET_CONFIG_VAL_BY_CONFIG_CODE. */
	public static final String GET_CONFIG_VAL_BY_CONFIG_CODE = NOTIFICATIONS + "getConfiValByConfigCode";

	/** The Constant MAIL_NOTIFICATIONS. */
	public static final String MAIL_NOTIFICATIONS = NOTIFICATIONS + "/wrkrEmails";

	/** The Constant FCM_MAIL_NOTIFICATIONS. */
	public static final String FCM_MAIL_NOTIFICATIONS = NOTIFICATIONS + "/jlsWrkrFcmEmails";

}