package com.be.sdk.util;


/**
 * @author Naem Othman
 * @since Oct 21 , 2015
 */

public enum ProjectEnum {

	EQUOTA("equota", "EQM"), EEMBASSY("eembassy", "EMB"), URPBGD("urpbgd", "URPBGD");

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
