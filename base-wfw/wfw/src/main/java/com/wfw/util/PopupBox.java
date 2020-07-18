/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.util;


import java.util.HashMap;
import java.util.Map;

import com.wfw.sdk.util.CmnUtil;


public class PopupBox {

	private PopupBox() {
		throw new IllegalStateException("PopupBox Utility class");
	}


	private static final String SUCCESS = "success";

	private static final String INFO = "info";

	private static final String ERROR = "error";

	private static final String WARN = "warn";

	private static final String QUESTION = "question";


	public static Map<String, Object> question(String id, Object content) {
		return question(id, null, content);
	}


	public static Map<String, Object> question(String id, String title, Object content) {
		return getPopup(id, QUESTION, title, content);
	}


	public static Map<String, Object> success(String id, Object content) {
		return success(id, null, content);
	}


	public static Map<String, Object> success(String id, String title, Object content) {
		return getPopup(id, SUCCESS, title, content);
	}


	public static Map<String, Object> info(String id, Object content) {
		return info(id, null, content);
	}


	public static Map<String, Object> info(String id, String title, Object content) {
		return getPopup(id, INFO, title, content);
	}


	public static Map<String, Object> warn(String id, Object content) {
		return warn(id, null, content);
	}


	public static Map<String, Object> warn(String id, String title, Object content) {
		return getPopup(id, WARN, title, content);
	}


	public static Map<String, Object> error(String id, Object content) {
		return error(id, null, content);
	}


	public static Map<String, Object> error(String id, String title, Object content) {
		return getPopup(id, ERROR, title, content);
	}


	private static Map<String, Object> getPopup(String id, String type, String title, Object content) {
		Map<String, Object> map = new HashMap<>();
		map.put("mainPopupId", id);
		map.put("mainPopupType", type);
		if (!CmnUtil.isObjNull(title)) {
			map.put("mainPopupTitle", title);
		}
		map.put("mainPopupContent", content);
		return map;
	}

}