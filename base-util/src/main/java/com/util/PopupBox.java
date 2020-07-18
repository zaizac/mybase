/**
 * Copyright 2019. 
 */
package com.util;


import java.util.HashMap;
import java.util.Map;


/**
 * The Class PopupBox.
 *
 * @author Mary Jane Buenaventura
 * @since Jan 23, 2015
 */
public class PopupBox {

	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";

	/** The Constant INFO. */
	private static final String INFO = "info";

	/** The Constant ERROR. */
	private static final String ERROR = "error";

	/** The Constant WARN. */
	private static final String WARN = "warn";

	/** The Constant CONFIRM. */
	private static final String CONFIRM = "confirm";

	/** The Constant QUESTION. */
	private static final String QUESTION = "question";


	/**
	 * Instantiates a new popup box.
	 */
	private PopupBox() {
		// DO NOTHING
	}


	/**
	 * Question.
	 *
	 * @param id the id
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> question(String id, Object content) {
		return question(id, null, content);
	}


	/**
	 * Question.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> question(String id, String title, Object content) {
		return getPopup(id, QUESTION, title, content);
	}


	/**
	 * Success.
	 *
	 * @param id the id
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> success(String id, Object content) {
		return success(id, null, content);
	}


	/**
	 * Success.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> success(String id, String title, Object content) {
		return getPopup(id, SUCCESS, title, content);
	}


	/**
	 * Success.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the map
	 */
	public static Map<String, Object> success(String id, String title, Object content, String redirectLink) {
		return getPopup(id, SUCCESS, title, content, redirectLink);
	}


	/**
	 * Info.
	 *
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> info(Object content) {
		return info(null, null, content);
	}


	/**
	 * Info.
	 *
	 * @param id the id
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> info(String id, Object content) {
		return info(id, null, content);
	}


	/**
	 * Info.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> info(String id, String title, Object content) {
		return getPopup(id, INFO, title, content);
	}


	/**
	 * Info.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the map
	 */
	public static Map<String, Object> info(String id, String title, Object content, String redirectLink) {
		return getPopup(id, INFO, title, content, redirectLink);
	}


	/**
	 * Warn.
	 *
	 * @param id the id
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> warn(String id, Object content) {
		return warn(id, null, content);
	}


	/**
	 * Warn.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> warn(String id, String title, Object content) {
		return getPopup(id, WARN, title, content);
	}


	/**
	 * Warn.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the map
	 */
	public static Map<String, Object> warn(String id, String title, Object content, String redirectLink) {
		return getPopup(id, WARN, title, content, redirectLink);
	}


	/**
	 * Confirm.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the map
	 */
	public static Map<String, Object> confirm(String id, String title, Object content, String redirectLink) {
		return getPopup(id, CONFIRM, title, content, redirectLink);
	}


	/**
	 * Error.
	 *
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> error(Object content) {
		return error(null, null, content);
	}


	/**
	 * Error.
	 *
	 * @param id the id
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> error(String id, Object content) {
		return error(id, ERROR, content);
	}


	/**
	 * Error.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @return the map
	 */
	public static Map<String, Object> error(String id, String title, Object content) {
		return getPopup(id, ERROR, title, content);
	}


	/**
	 * Error.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the map
	 */
	public static Map<String, Object> error(String id, String title, Object content, String redirectLink) {
		return getPopup(id, ERROR, title, content, redirectLink);
	}


	/**
	 * Gets the popup.
	 *
	 * @param id the id
	 * @param type the type
	 * @param title the title
	 * @param content the content
	 * @return the popup
	 */
	private static Map<String, Object> getPopup(String id, String type, String title, Object content) {
		return getPopup(id, type, title, content, null);
	}


	/**
	 * Gets the popup.
	 *
	 * @param id the id
	 * @param type the type
	 * @param title the title
	 * @param content the content
	 * @param redirectLink the redirect link
	 * @return the popup
	 */
	private static Map<String, Object> getPopup(String id, String type, String title, Object content,
			String redirectLink) {
		Map<String, Object> map = new HashMap<>();
		map.put("mainPopupId", id);
		map.put("mainMessageType", type);
		if (!BaseUtil.isObjNull(title)) {
			map.put("mainPopupTitle", title);
		}
		if (!BaseUtil.isObjNull(redirectLink)) {
			map.put("redirectLink", redirectLink);
		}
		map.put("mainMessage", content);
		return map;
	}

}