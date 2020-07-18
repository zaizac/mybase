package com.idm.config;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class IdmOauthSql {

	private IdmOauthSql() {
		throw new IllegalStateException("Utility class");
	}


	private static final String SQL_SELECT_TOKENID = "select token_id, token from ";

	private static final String SQL_INSERT_INTO = "insert into ";

	private static final String SQL_WHERE_TOKENID = " where token_id = ?";

	private static final String SQL_WHERE_AUTHENTICATION_ID = " where authentication_id = ?";

	private static final String SQL_WHERE_CLIENT_ID = " where client_id = ?";

	private static final String SQL_DELETE_FROM = "delete from ";

	private static final String IDM_OAUTH_CLIENT_DETAILS = "IDM_OAUTH_CLIENT_DETAILS";

	private static final String IDM_OAUTH_ACCESS_TOKEN = "IDM_OAUTH_ACCESS_TOKEN";

	private static final String IDM_OAUTH_REFRESH_TOKEN = "IDM_OAUTH_REFRESH_TOKEN";

	private static final String IDM_OAUTH_CODE = "IDM_OAUTH_CODE";

	private static final String IDM_OAUTH_CLIENT_TOKEN = "IDM_OAUTH_CLIENT_TOKEN";

	public static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = SQL_INSERT_INTO + IDM_OAUTH_ACCESS_TOKEN
			+ " (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

	public static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = SQL_SELECT_TOKENID + IDM_OAUTH_ACCESS_TOKEN
			+ SQL_WHERE_TOKENID;

	public static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from "
			+ IDM_OAUTH_ACCESS_TOKEN + SQL_WHERE_TOKENID;

	public static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = SQL_SELECT_TOKENID
			+ IDM_OAUTH_ACCESS_TOKEN + SQL_WHERE_AUTHENTICATION_ID;

	public static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT = SQL_SELECT_TOKENID
			+ IDM_OAUTH_ACCESS_TOKEN + " where user_name = ? and client_id = ?";

	public static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = SQL_SELECT_TOKENID
			+ IDM_OAUTH_ACCESS_TOKEN + " where user_name = ?";

	public static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = SQL_SELECT_TOKENID
			+ IDM_OAUTH_ACCESS_TOKEN + SQL_WHERE_CLIENT_ID;

	public static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = SQL_DELETE_FROM + IDM_OAUTH_ACCESS_TOKEN
			+ SQL_WHERE_TOKENID;

	public static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = SQL_DELETE_FROM
			+ IDM_OAUTH_ACCESS_TOKEN + " where refresh_token = ?";

	public static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = SQL_INSERT_INTO + IDM_OAUTH_REFRESH_TOKEN
			+ " (token_id, token, authentication) values (?, ?, ?)";

	public static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = SQL_SELECT_TOKENID + IDM_OAUTH_REFRESH_TOKEN
			+ SQL_WHERE_TOKENID;

	public static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from "
			+ IDM_OAUTH_REFRESH_TOKEN + SQL_WHERE_TOKENID;

	public static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = SQL_DELETE_FROM + IDM_OAUTH_REFRESH_TOKEN
			+ SQL_WHERE_TOKENID;

	private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove";

	private static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

	private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS + " from "
			+ IDM_OAUTH_CLIENT_DETAILS;

	public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	public static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + SQL_WHERE_CLIENT_ID;

	public static final String DEFAULT_INSERT_STATEMENT = SQL_INSERT_INTO + IDM_OAUTH_CLIENT_DETAILS + " ("
			+ CLIENT_FIELDS + ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

	public static final String DEFAULT_UPDATE_STATEMENT = "update " + IDM_OAUTH_CLIENT_DETAILS + " set "
			+ CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

	public static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update " + IDM_OAUTH_CLIENT_DETAILS
			+ " set client_secret = ? where client_id = ?";

	public static final String DEFAULT_DELETE_STATEMENT = SQL_DELETE_FROM + IDM_OAUTH_CLIENT_DETAILS
			+ SQL_WHERE_CLIENT_ID;

	public static final String DEFAULT_SELECT_AUTH_CODE_STATEMENT = "select code, authentication from "
			+ IDM_OAUTH_CODE + " where code = ?";

	public static final String DEFAULT_INSERT_AUTH_CODE_STATEMENT = SQL_INSERT_INTO + IDM_OAUTH_CODE
			+ " (code, authentication) values (?, ?)";

	public static final String DEFAULT_DELETE_AUTH_CODE_STATEMENT = SQL_DELETE_FROM + IDM_OAUTH_CODE
			+ " where code = ?";

	public static final String DEFAULT_CLIENT_TOKEN_INSERT_STATEMENT = SQL_INSERT_INTO + IDM_OAUTH_CLIENT_TOKEN
			+ " (token_id, token, authentication_id, user_name, client_id) values (?, ?, ?, ?, ?)";

	public static final String DEFAULT_CLIENT_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = SQL_SELECT_TOKENID
			+ IDM_OAUTH_CLIENT_TOKEN + SQL_WHERE_AUTHENTICATION_ID;

	public static final String DEFAULT_CLIENT_TOKEN_DELETE_STATEMENT = SQL_DELETE_FROM + IDM_OAUTH_CLIENT_TOKEN
			+ SQL_WHERE_AUTHENTICATION_ID;

}