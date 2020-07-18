/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baseboot.report.sdk.constants.BaseConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class JsonDateSerializer extends JsonSerializer<Date>{
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_Slash);
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		gen.writeString(dateFormat.format(date));
	}

}
