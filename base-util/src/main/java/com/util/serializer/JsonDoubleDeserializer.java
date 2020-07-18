/**
 *
 */
package com.util.serializer;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.util.constants.BaseConstants;


/**
 * @author Jhayne
 *
 */
public class JsonDoubleDeserializer extends JsonDeserializer<Double> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonDoubleDeserializer.class);

	private static final NumberFormat formatter = new DecimalFormat(BaseConstants.AMOUNT_FORMAT);


	@Override
	public Double deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String amount = jp.getText().trim();
		try {
			return formatter.parse(amount).doubleValue();
		} catch (ParseException e1) {
			LOGGER.error("Unable to parse timestamp: {}", amount);
			return null;
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to deserialize timestamp: {}", amount);
			return null;
		}
	}

}