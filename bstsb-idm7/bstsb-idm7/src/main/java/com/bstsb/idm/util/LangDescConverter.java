package com.bstsb.idm.util;


import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstsb.idm.sdk.model.LangDesc;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * @author mary.jane
 *
 */
@Convert
public class LangDescConverter implements AttributeConverter<LangDesc, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LangDescConverter.class);


	@Override
	public String convertToDatabaseColumn(LangDesc attribute) {
		if (attribute == null) {
			attribute = new LangDesc();
		}
		String json = "";
		try {
			json = JsonUtil.objectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException jpe) {
			LOGGER.error(jpe.getMessage());
		}
		return json;
	}


	@Override
	public LangDesc convertToEntityAttribute(String dbData) {
		LOGGER.info("convertToEntityAttribute: {}", dbData);
		if (BaseUtil.isObjNull(dbData)) {
			return null;
		}

		LangDesc myPojo = null;
		try {
			myPojo = JsonUtil.objectMapper().readValue(dbData, LangDesc.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			myPojo = new LangDesc();
			myPojo.setEn(dbData);
		}

		return myPojo;
	}

}
