/**
 * Copyright 2019. 
 */
package com.util.serializer;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * The Class JsonDateDeserializer.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonDateDeserializer.class);


	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext paramDeserializationContext) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
		String value = jp.getText().trim();
		Date date = null;
		if (!BaseUtil.isObjNull(value)) {
			try {
				date = dateFormat.parse(value);
			} catch (ParseException ex) {
				LOGGER.error("Invalid date format: {}", BaseConstants.DT_DD_MM_YYYY_SLASH);
			}
		}
		return date;
	}

}
