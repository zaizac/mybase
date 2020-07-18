/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.util;


import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.LangDesc;


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
			myPojo = new LangDesc();
			myPojo.setEn(dbData);
		}

		return myPojo;
	}

}
