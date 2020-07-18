/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.bff.cmn.form.CustomMultipartFile;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class MultiPartPropertyEditor extends PropertyEditorSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiPartPropertyEditor.class);


	@Override
	public void setValue(Object value) {
		CustomMultipartFile fu = null;
		if (value instanceof MultipartFile) {
			MultipartFile multipartFile = (MultipartFile) value;
			if (multipartFile.getSize() > 0) {
				try {
					fu = new CustomMultipartFile();
					if (multipartFile.getSize() <= 2000000) {
						fu.setFilename(multipartFile.getOriginalFilename());
						fu.setSize(multipartFile.getSize());
						fu.setContentType(multipartFile.getContentType());
						fu.setContent(multipartFile.getBytes());
					}
				} catch (IOException e) {
					LOGGER.error("IOException: {}", e.getMessage());
				}
			}
		} else if (value instanceof String) {
			value = null;
		} else if (value instanceof CustomMultipartFile) {
			fu = (CustomMultipartFile) value;
		} else {
			fu = new CustomMultipartFile();
		}
		super.setValue(fu);
	}

}
