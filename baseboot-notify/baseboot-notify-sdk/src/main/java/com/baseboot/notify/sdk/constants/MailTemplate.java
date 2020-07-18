/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum MailTemplate {

	// COMMONS
	CMN_OTP("OTP", "cmn/mail-otp-email", false),

	// IDM
	IDM_USER_CREATE("User Account Activation", "idm/mail-user-create", false),
	IDM_USER_ACTIVATE("User Account Activation", "idm/mail-activate-user", false),
	IDM_USER_DEACTIVATE("User Account Deactivation", "idm/mail-deactivate-user", false),
	IDM_FORGOT_PWORD("Password Reset", "idm/mail-forgot-password", false),
	IDM_ACCOUNT_ACTIVATE("Account Activation", "mail-layout", false),
	IDM_ACTIVATE_SUCCESS("Account Activation", "mail-layout", false),
	IDM_ACC_ACTIVATE_RSN("User Account Activation Key", "idm/mail-resend-activation", false),

	// BANK
	BANK_NOTIFY_BMET("BMET notification [{0}]", "bank/mail-notification-bmet", false),
	BANK_NOTIFY_BRA("BRA notification [{0}]", "bank/mail-notification-bra", false),
	BANK_NOTIFY_BANK_ADMIN("BANK ADMIN notification [{0}]", "bank/mail-notification-bank-admin", false),

	PAYMENT_RELEASE_BMET("Payment Release [{0}]", "bank/mail-notification-bmet", false),
	PAYMENT_RELEASE_BRA("Payment Release [{0}]", "bank/mail-notification-bra", false),
	PAYMENT_RELEASE_BANK_ADMIN("Request Payment Release [{0}]", "bank/mail-notification-bank-admin", false),
	BANK_NOTIFY_WITHDRAWAL_BRA("Withdrawal Refund [{0}]", "bank/mail-notification-payment-withdrawal-bra", false),
	PAYMENT_DEPOSIT_BRA("Payment Deposit [{0}]", "bank/mail-notification-deposit-bra", false),
	PAYMENT_PENALTY_BRA("Payment Penalty [{0}]", "bank/mail-notification-payment-penalty-bra", false),;

	private final String subject;

	private final String location;

	private final boolean attachment;


	private MailTemplate(String subject, String location, boolean attachment) {
		this.subject = subject;
		this.location = location;
		this.attachment = attachment;
	}


	public String getLocation() {
		return location;
	}


	public String getSubject() {
		return subject;
	}


	public boolean hasAttachment() {
		return attachment;
	}


	public static MailTemplate findByName(String name) {
		for (MailTemplate v : MailTemplate.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}

}