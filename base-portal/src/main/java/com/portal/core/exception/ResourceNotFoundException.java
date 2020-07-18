package com.portal.core.exception;

public class ResourceNotFoundException extends IllegalArgumentException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3399614565522290419L;

	public ResourceNotFoundException() {
		super("Permission error");
	}
	
}
