/**
 *
 */
package com.bstsb.idm.sdk.client;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.SocialUserConnectionDto;
import com.bstsb.idm.sdk.model.SocialUserDto;
import com.bstsb.util.BaseUtil;



/**
 * @author mary.jane
 *
 */
public class SocialService extends AbstractService {

	private IdmRestTemplate restTemplate;

	private String url;

	private static final String USER_ID = "userId";

	private static final String PROVIDER_ID = "providerId";

	private static final String PROVIDER_USER_ID = "providerUserId";


	public SocialService(IdmRestTemplate restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}


	@Override
	public IdmRestTemplate restTemplate() {
		return restTemplate;
	}


	@Override
	public String url() {
		return url;
	}


	/*********************************
	 * IDM_USER_CONNECTION
	 *******************************************/
	public List<SocialUserConnectionDto> findAllConnections() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_ALL_USER_CONN);

		SocialUserConnectionDto[] resp = restTemplate().getForObject(getServiceURI(sb.toString()),
				SocialUserConnectionDto[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return Collections.<SocialUserConnectionDto> emptyList();
	}


	public List<SocialUserConnectionDto> findAllConnectionsByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_ALL_USER_CONN_USERID);

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);

		SocialUserConnectionDto[] resp = restTemplate().getForObject(getServiceURI(sb.toString()),
				SocialUserConnectionDto[].class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return Collections.<SocialUserConnectionDto> emptyList();
	}


	public List<SocialUserConnectionDto> findConnections(String userId, String providerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_ALL_USER_CONN_USERID_PROVID);

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put(PROVIDER_ID, providerId);

		SocialUserConnectionDto[] resp = restTemplate().getForObject(getServiceURI(sb.toString()),
				SocialUserConnectionDto[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return Collections.<SocialUserConnectionDto> emptyList();
	}


	public List<SocialUserConnectionDto> findConnectionsToUsers(String userId, String providerId,
			Set<String> providerUserIds) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_CONNS_TO_USERS);

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put(PROVIDER_ID, providerId);
		params.put("providerUserIds", providerUserIds);

		SocialUserConnectionDto[] resp = restTemplate().getForObject(getServiceURI(sb.toString()),
				SocialUserConnectionDto[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return Collections.<SocialUserConnectionDto> emptyList();
	}


	public SocialUserConnectionDto findPrimaryConnection(String userId, String providerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_PRIMARY_USER_CONN);

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put(PROVIDER_ID, providerId);

		SocialUserConnectionDto resp = restTemplate().getForObject(getServiceURI(sb.toString()),
				SocialUserConnectionDto.class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return resp;
		}
		return null;
	}


	public List<String> findUserIdsWithConnection(String providerId, String providerUserId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_USERID_USER_CONN);
		sb.append("?providerId={providerId}&providerUserId={providerUserId}");

		Map<String, Object> params = new HashMap<>();
		params.put(PROVIDER_ID, providerId);
		params.put(PROVIDER_USER_ID, providerUserId);

		String[] result = restTemplate().getForObject(getServiceURI(sb.toString()), String[].class, params);
		if (!BaseUtil.isObjNull(result)) {
			return Arrays.asList(result);
		}
		return Collections.<String> emptyList();
	}


	public List<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_USERID_USER_CONN);
		sb.append("?providerId={providerId}&providerUserIds={providerUserIds}");

		Map<String, Object> params = new HashMap<>();
		params.put(PROVIDER_ID, providerId);
		params.put("providerUserIds", providerUserIds);

		String[] result = restTemplate().getForObject(getServiceURI(sb.toString()), String[].class, params);
		if (!BaseUtil.isObjNull(result)) {
			return Arrays.asList(result);
		}
		return Collections.<String> emptyList();
	}


	public SocialUserConnectionDto updateConnection(SocialUserConnectionDto userConn) {
		try {
			return restTemplate().putForObject(getServiceURI(IdmUrlConstants.UPDATE_USER_CONN), userConn,
					SocialUserConnectionDto.class);
		} catch (IdmException e) {
			return null;
		}
	}


	public SocialUserConnectionDto createConnection(SocialUserConnectionDto userConn) {
		StringBuilder sb = new StringBuilder(IdmUrlConstants.CREATE_USER_CONN);
		return restTemplate().postForObject(getServiceURI(sb.toString()), userConn, SocialUserConnectionDto.class);
	}


	public boolean removeConnections(String userId, String providerId) {
		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put(PROVIDER_ID, providerId);
		return restTemplate().deleteForObject(getServiceURI(IdmUrlConstants.DELETE_USER_CONN), params);
	}


	public boolean removeConnection(String userId, String providerId, String providerUserId) {
		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put(PROVIDER_ID, providerId);
		params.put(PROVIDER_USER_ID, providerUserId);
		return restTemplate().deleteForObject(getServiceURI(IdmUrlConstants.DELETE_USER_CONN_ALL_KEYS), params);
	}


	/*********************************
	 * IDM_SOCIAL
	 *******************************************/
	public SocialUserDto findSocialUser(String userId, String provider, String providerUserId) {

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_SOCIAL_USER);
		sb.append("?userId={userId}&provider={provider}&providerUserId={providerUserId}");

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		params.put("provider", provider);
		params.put(PROVIDER_USER_ID, providerUserId);

		String restUrl = getServiceURI(sb.toString());

		SocialUserDto resp = restTemplate().getForObject(restUrl, SocialUserDto.class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return resp;
		}

		return null;
	}


	public SocialUserDto findSocialUserByProviderId(String provider, String providerUserId) {

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_SOCIAL_USER_BY_PROVID);
		sb.append("?provider={provider}&providerUserId={providerUserId}");

		Map<String, Object> params = new HashMap<>();
		params.put("provider", provider);
		params.put(PROVIDER_USER_ID, providerUserId);

		String restUrl = getServiceURI(sb.toString());

		SocialUserDto resp = restTemplate().getForObject(restUrl, SocialUserDto.class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return resp;
		}

		return null;
	}


	public SocialUserDto findSocialUserByUserId(String userId) {

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_SOCIAL_USER_BY_USERID);
		sb.append("?userId={userId}");

		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		String restUrl = getServiceURI(sb.toString());

		SocialUserDto resp = restTemplate().getForObject(restUrl, SocialUserDto.class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return resp;
		}

		return null;
	}

}
