/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.report.config.cache.RedisCacheWrapper;
import com.baseboot.report.core.AbstractRestController;
import com.baseboot.report.sdk.constants.ReportUrlConstants;
import com.baseboot.report.sdk.constants.RptCacheConstants;
import com.baseboot.report.sdk.model.ServiceCheck;
import com.baseboot.report.sdk.util.BaseUtil;
import com.baseboot.report.util.ConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
public class ServiceCheckRestController extends AbstractRestController implements RptCacheConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCheckRestController.class);

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	MessageSource messageSource;

	private static final String SERVICE_NAME = "REPORT Service";

	private static final String SUCCESS = "SUCCESS";

	private static final String FAILED = "FAILED";


	@RequestMapping(value = ReportUrlConstants.SERVICE_CHECK + "/test", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return SERVICE_NAME;
	}


	@RequestMapping(value = ReportUrlConstants.SERVICE_CHECK, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)), SUCCESS);

		// IDM Service
		String idmUrl = messageSource.getMessage(ConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			String str = getIdmService(request).checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(new ServiceCheck("IDM Service", idmUrl, FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(FAILED);
		}

		return svcTest;
	}


	@RequestMapping(value = ReportUrlConstants.REPORT_CACHE_EVICT, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(CACHE_BUCKET);
		if (!BaseUtil.isObjNull(prefixKey)) {
			cache.evictByPrefix(prefixKey);
		} else {
			cache.clear();
		}
		return true;
	}

}