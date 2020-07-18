package com.bff.cmn.constants;


/**
 * @author Ilhomjon Abdu
 * @since 31 Oct 2019
 */
public class BffUrlConstants {

	private BffUrlConstants() {
		throw new IllegalStateException("Utility class");
	}


	private static final String CREATE = "/create";

	private static final String UPDATE = "/update";
	
	private static final String DELETE = "/delete";

	private static final String SEARCH = "/search";

	public static final String REFERENCE = "/references";

	public static final String GET_DOCUMENT_LST = "/getAllDocuments";

	public static final String CITIES = "/cities";

	public static final String CITIES_CREATE = CITIES + CREATE;

	public static final String CITIES_UPDATE = CITIES + UPDATE;

	public static final String CITIES_SEARCH = CITIES + SEARCH;

	public static final String STATES = "/states";

	public static final String STATES_CREATE = STATES + CREATE;

	public static final String STATES_UPDATE = STATES + UPDATE;

	public static final String STATES_SEARCH = STATES + SEARCH;

	public static final String COUNTRIES = "/countries";

	public static final String COUNTRIES_CREATE = COUNTRIES + CREATE;

	public static final String COUNTRIES_UPDATE = COUNTRIES + UPDATE;

	public static final String COUNTRIES_SEARCH = COUNTRIES + SEARCH;

	public static final String METADATA = "/metadata";

	public static final String METADATA_CREATE = METADATA + CREATE;

	public static final String METADATA_UPDATE = METADATA + UPDATE;

	public static final String METADATA_SEARCH = METADATA + SEARCH;

	public static final String REASON = "/reason";

	public static final String REASON_CREATE = REASON + CREATE;

	public static final String REASON_UPDATE = REASON + UPDATE;

	public static final String REASON_SEARCH = REASON + SEARCH;

	public static final String STATUS = "/status";

	public static final String STATUS_CREATE = STATUS + CREATE;

	public static final String STATUS_UPDATE = STATUS + UPDATE;

	public static final String STATUS_SEARCH = STATUS + SEARCH;
	
	public static final String PORTAL_TYPE = "/portalType";
	
	public static final String PORTAL_TYPE_CREATE = PORTAL_TYPE + CREATE;
	
	public static final String PORTAL_TYPE_UPDATE = PORTAL_TYPE + UPDATE;
	
	public static final String PORTAL_TYPE_DELETE = PORTAL_TYPE + DELETE;

	public static final String USER_GROUP_BRANCH = "/usergroupbranch";
	
	public static final String USER_GROUP_BRANCH_CREATE = USER_GROUP_BRANCH + CREATE;
	
	public static final String USER_GROUP_BRANCH_UPDATE = USER_GROUP_BRANCH + UPDATE;
	
	public static final String USER_GROUP_BRANCH_DELETE = USER_GROUP_BRANCH + DELETE;
	
	public static final String USER_GROUP_BRANCH_SEARCH = USER_GROUP_BRANCH + SEARCH;
}
