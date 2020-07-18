/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public enum ProjectEnum {

	URP("urp", "URP"),
	URPBGD("urpbgd", "URPBGD"),
	EQUOTA("equota", "EQM"),
	EVDRSECURITY("security", "ESEC"),
	EEMBASSY("eembassy", "EMB"),
	NNS("NNS", "NNS");

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