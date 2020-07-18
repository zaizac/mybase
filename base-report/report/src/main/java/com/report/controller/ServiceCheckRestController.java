/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.report.config.cache.RedisCacheWrapper;
import com.report.core.AbstractRestController;
import com.report.sdk.constants.ReportUrlConstants;
import com.report.sdk.constants.RptCacheConstants;
import com.report.sdk.model.ServiceCheck;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
public class ServiceCheckRestController extends AbstractRestController {

	@Autowired
	private CacheManager cacheManager;

	private static final String SERVICE_NAME = "REPORT Service";


	@GetMapping(value = ReportUrlConstants.SERVICE_CHECK + "/test", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return SERVICE_NAME;
	}


	@GetMapping(value = ReportUrlConstants.SERVICE_CHECK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// IDM Service
		String idmUrl = messageSource.getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			String str = getIdmService(request).checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(
					new ServiceCheck("IDM Service", idmUrl, BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}


	@GetMapping(value = ReportUrlConstants.REPORT_CACHE_EVICT, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(RptCacheConstants.CACHE_BUCKET);
		if (!BaseUtil.isObjNull(prefixKey)) {
			cache.evictByPrefix(prefixKey);
		} else {
			cache.clear();
		}
		return true;
	}

}