/**
 * Copyright 2019. 
 */
package com.util.serializer;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.util.constants.BaseConstants;


/**
 * The Class JsonTimestampSerializer.
 *
 * @author Mary Jane Buenaventura
 * @since Dec 6, 2016
 */
public class JsonTimestampSerializer extends JsonSerializer<Timestamp> {

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Timestamp timestamp, JsonGenerator gen, SerializerProvider provider) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A);
		gen.writeString(dateFormat.format(timestamp));
	}

}
