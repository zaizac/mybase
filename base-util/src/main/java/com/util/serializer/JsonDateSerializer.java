/**
 * Copyright 2019. 
 */
package com.util.serializer;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.util.constants.BaseConstants;


/**
 * The Class JsonDateSerializer.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 3, 2015
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
		gen.writeString(dateFormat.format(date));
	}

}
