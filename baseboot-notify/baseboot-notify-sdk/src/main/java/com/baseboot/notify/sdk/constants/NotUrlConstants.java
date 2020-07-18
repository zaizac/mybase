/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class NotUrlConstants {

	private NotUrlConstants() {
		// Block Initialization
	}


	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String NOTIFY = "/notifications";

	public static final String NOTIFY_SEND_ALL = NOTIFY + "/sendAll";

	public static final String NOTIFY_MAIL = NOTIFY + "/mail";

	public static final String NOTIFY_MAIL_SEND = NOTIFY + "/mail/send";

	public static final String NOTIFY_SMS = NOTIFY + "/sms";

	public static final String NOTIFY_SMS_SEND = NOTIFY + "/sms/send";

	public static final String MAIL_TEMPLATE = NOTIFY + "/mail/templates";

	public static final String UPDATE_MAIL_TEMPLATE = NOTIFY + "/mail/template/update";

	public static final String NOTIFY_SEND_ALL_TEST = NOTIFY + "/sendAllTest";

	public static final String NOTIFY_CONFIG = NOTIFY + "/config";

	public static final String GET_CONFIG_VAL_BY_CONFIG_CODE = NOTIFY + "getConfiValByConfigCode";

}