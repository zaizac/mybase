/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.sdk.constants;


/**
 * @author mary.jane
 *
 */
public class ReferenceConstants {

	private ReferenceConstants() {
		throw new IllegalStateException(ReferenceConstants.class.getName());
	}
	
	private static final String CREATE = "/create";
	
	private static final String UPDATE = "/update";
	
	private static final String SEARCH = "/search";
	

	public static final String REF_TYP_CITY = "/cities";

	public static final String CREATE_CITY = REF_TYP_CITY + CREATE;
	
	public static final String UPDATE_CITY = REF_TYP_CITY + UPDATE;
	
	public static final String SEARCH_CITY = REF_TYP_CITY + SEARCH;
	
	public static final String REF_TYP_STATE = "/states";

	public static final String CREATE_STATE = REF_TYP_STATE + CREATE;
	
	public static final String UPDATE_STATE = REF_TYP_STATE + UPDATE;
	
	public static final String SEARCH_STATE = REF_TYP_STATE + SEARCH;

	public static final String REF_TYP_COUNTRY = "/countries";
	
	public static final String CREATE_COUNTRY = REF_TYP_COUNTRY + CREATE;
	
	public static final String UPDATE_COUNTRY = REF_TYP_COUNTRY + UPDATE;
	
	public static final String SEARCH_COUNTRY = REF_TYP_COUNTRY + SEARCH;
	

	public static final String REF_TYP_DOCUMENT = "documents";

	public static final String CREATE_DOCUMENT = REF_TYP_DOCUMENT + CREATE;
	
	public static final String UPDATE_DOCUMENT = REF_TYP_DOCUMENT + UPDATE;

	public static final String REF_TYP_METADATA = "metadata";

	public static final String CREATE_METADATA = REF_TYP_METADATA + CREATE;
	
	public static final String UPDATE_METADATA = REF_TYP_METADATA + UPDATE;

	public static final String REF_TYP_REASON = "reasons";


	public static final String CREATE_REASON = REF_TYP_REASON + CREATE;
	
	public static final String UPDATE_REASON = REF_TYP_REASON + UPDATE;

	public static final String REF_TYP_STATUS = "status";
	
	public static final String CREATE_STATUS = REF_TYP_STATUS + CREATE;
	
	public static final String UPDATE_STATUS = REF_TYP_STATUS + UPDATE;


}