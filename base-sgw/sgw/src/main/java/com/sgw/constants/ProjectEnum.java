/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.constants;


/**
 * @author Nurul Naimma
 * @since July 4, 2018
 */
public enum ProjectEnum {

	URPBGD("urpbgd", "URPBGD"),

	;

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


	public static ProjectEnum findByName(String name) {
		for (ProjectEnum v : ProjectEnum.values()) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

}
