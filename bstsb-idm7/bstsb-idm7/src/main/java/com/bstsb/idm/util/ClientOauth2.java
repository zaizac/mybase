/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.util;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.Token;
import com.bstsb.util.JsonUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class ClientOauth2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientOauth2.class);


	public Token generateAccessToken(List<NameValuePair> params, HttpServletRequest request) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		int readTimeout = 20;
		RequestConfig rc = RequestConfig.custom().setAuthenticationEnabled(true).setConnectTimeout(readTimeout * 1000)
				.setConnectionRequestTimeout(readTimeout * 1000).setSocketTimeout(readTimeout * 1000).build();
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(cm);
		builder.setDefaultRequestConfig(rc);
		CloseableHttpClient httpClient = builder.build();
		String baseUrl = String.format("%s://%s:%d%s/", request.getScheme(), request.getServerName(),
				request.getServerPort(), request.getContextPath());
		HttpPost post = new HttpPost(baseUrl + "idm/token");
		Token token = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(post);

			String body = EntityUtils.toString(response.getEntity());
			LOGGER.info(body);
			Map<String, Object> map = JsonUtil.convertJsonToMap(body);
			token = new Token();
			token.setAccessToken(String.valueOf(map.get("access_token")));
			token.setExpiresIn(String.valueOf(map.get("expires_in")));
			token.setRefreshToken(String.valueOf(map.get("refresh_token")));
			token.setScope(String.valueOf(map.get("scope")));
		} catch (IOException e) {
			LOGGER.error("IOException:", e);
		} catch (IdmException e) {
			LOGGER.error("Exception:", e);
		}
		return token;

	}

}