/**
 * Copyright 2019. 
 */
package com.util.serializer;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.util.constants.BaseConstants;


/**
 * The Class JsonTimestampDeserializer.
 *
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class JsonTimestampDeserializer extends JsonDeserializer<Timestamp> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonTimestampDeserializer.class);


	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A);
		String timestamp = jp.getText().trim();
		try {
			Date dt = dateFormat.parse(timestamp);
			return new Timestamp(dt.getTime());
		} catch (ParseException e1) {
			LOGGER.error("Unable to parse timestamp: {}", timestamp);
			return null;
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to deserialize timestamp: {}", timestamp);
			return null;
		}
	}

}
