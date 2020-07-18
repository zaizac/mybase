/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.model.ServiceCheck;
import com.portal.constants.PageConstants;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@RestController
@RequestMapping(PageConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractController {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	MessageSource messageSource;

	private static final String SERVICE_NAME = "INTERNAL PORTAL";


	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(SERVICE_NAME, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// IDM Service
		String idmUrl = messageSource.getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault());
		try {
			String str = getIdmService().checkConnection();
			svcTest.setIdm(new ServiceCheck(str, idmUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setIdm(
					new ServiceCheck("IDM Service", idmUrl, BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		// NOTIFICATION Service
		String notifyUrl = messageSource.getMessage(BaseConfigConstants.SVC_NOT_URL, null, Locale.getDefault());
		try {
			String str = getNotifyService().checkConnection();
			svcTest.setNotification(new ServiceCheck(str, notifyUrl, BaseConstants.SUCCESS));
		} catch (Exception e) {
			svcTest.setNotification(new ServiceCheck("Notification Service", notifyUrl,
					BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}


	@GetMapping(value = "test", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String serviceCheck() {
		return SERVICE_NAME;
	}


	@GetMapping(value = "all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Object> serviceCheckAll(HttpServletRequest request) {
		List<Object> svcTestLst = new ArrayList<>();
		svcTestLst.add(serviceCheck(request));
		try {
			svcTestLst.add(getIdmService().serviceTest());
		} catch (Exception e) {
			svcTestLst.add(new ServiceCheck("IDM Service",
					messageSource.getMessage(BaseConfigConstants.SVC_IDM_URL, null, Locale.getDefault()),
					BaseConstants.FAILED));
		}
		try {
			svcTestLst.add(getNotifyService().serviceTest());
		} catch (Exception e) {
			svcTestLst.add(new ServiceCheck("NOTIFICATION Service",
					messageSource.getMessage(BaseConfigConstants.SVC_NOT_URL, null, Locale.getDefault()),
					BaseConstants.FAILED));
		}
		return svcTestLst;
	}
}