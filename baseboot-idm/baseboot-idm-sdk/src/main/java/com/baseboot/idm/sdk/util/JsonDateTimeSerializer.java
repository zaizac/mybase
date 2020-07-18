/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.sdk.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class JsonDateTimeSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S);


	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(dateFormat.format(date));
	}

}
