/**
 * Copyright 2019. 
 */
package com.notify.sdk.constants;


/**
 * The Enum FcmTemplate.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum FcmTemplate {

	/** The fcm submission dq. */
	FCM_SUBMISSION_DQ("Amendment submission to Job Lottery Data Quality"),

	/** The fcm interview invitation. */
	FCM_INTERVIEW_INVITATION("Interview Invitation"),

	/** The fcm interview pass. */
	FCM_INTERVIEW_PASS("Interview Result"),

	/** The fcm interview fail. */
	FCM_INTERVIEW_FAIL("Interview Result"),

	/** The fcm hired. */
	FCM_HIRED("Hired"),

	/** The fcm dq approve. */
	FCM_DQ_APPROVE("Amendment of Profile Information has been Verified"),

	/** The fcm dq reject. */
	FCM_DQ_REJECT("Amendment of Profile Information has been Rejected"),

	/** The fcm prof view. */
	FCM_PROF_VIEW("Profile viewed");

	/** The content. */
	private String content;


	/**
	 * Instantiates a new fcm template.
	 *
	 * @param content
	 *             the content
	 */
	private FcmTemplate(String content) {
		this.content = content;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
}