/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.util;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum ProjectEnum {

	FDHP("baseboot", "B2P");

	private final String name;

	private final String prefix;


	ProjectEnum(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}


	public String getName() {
		return name;
	}


	public String getPrefix() {
		return prefix;
	}

}
