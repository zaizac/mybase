/**
 *
 */
package com.util.serializer;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.util.constants.BaseConstants;


/**
 * @author Jhayne
 *
 */
public class JsonDoubleSerializer extends JsonSerializer<Double> {

	private static final NumberFormat formatter = new DecimalFormat(BaseConstants.AMOUNT_FORMAT);


	@Override
	public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(formatter.format(value));
	}

}
