/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class CustomPrimitiveFormatEditor extends PropertyEditorSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomPrimitiveFormatEditor.class);


	@Override
	public void setValue(Object value) {
		LOGGER.debug("BEFORE CustomPrimitiveFormatEditor>>> {}", value);
		setAsText(String.valueOf(value));
		LOGGER.debug("AFTER CustomPrimitiveFormatEditor>>> {}", value);
		super.setValue(value);
	}


	@Override
	public String getAsText() {
		Long d = (Long) super.getValue();
		LOGGER.debug("getAsText: {}", d);
		return d.toString();
	}


	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == "" || text == null) {
			LOGGER.debug("setAsText: {}", 0);
			setValue(0);
			return;
		} else {
			setValue(Long.parseLong(text));
			LOGGER.debug("setAsText: long");
			return;
		}
	}

}