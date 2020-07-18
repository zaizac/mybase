/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.core;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.bff.cmn.form.CustomMultipartFile;
import com.bff.cmn.form.StaticData;
import com.bff.config.MultiPartPropertyEditor;
import com.bff.util.constants.AppConstants;
import com.bff.util.constants.ProjectEnum;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.model.Documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.idm.sdk.model.UserType;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public abstract class AbstractController extends GenericAbstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

	@Autowired
	protected ServletContext context;

	@Autowired
	protected StaticData staticData;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	private CacheManager cacheManager;


	@InitBinder
	protected void bindingPreparation(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);

		CustomDateEditor editor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, false));
		binder.registerCustomEditor(CustomMultipartFile.class, new MultiPartPropertyEditor());
	}


	public ModelAndView getDefaultMav(String template) {
		String portal = messageService.getMessage(BaseConfigConstants.PORTAL_TYPE);
		ModelAndView mav = new ModelAndView(template);
		mav.addObject(AppConstants.PORTAL_MODULE, ProjectEnum.URP.getPrefix());
		mav.addObject(AppConstants.PORTAL_TYPE, portal);
		return mav;
	}


	public ModelAndView getDefaultMav(String template, String module, String transId) {
		String portal = messageService.getMessage(BaseConfigConstants.PORTAL_TYPE);
		ModelAndView mav = new ModelAndView(template);
		mav.addObject(AppConstants.PORTAL_MODULE, module);
		mav.addObject(AppConstants.PORTAL_TRANS_ID, transId);
		mav.addObject(AppConstants.PORTAL_TYPE, portal);
		return mav;
	}


	public ModelAndView getDefaultMav(String template, String module, String transId, String script) {
		return getDefaultMav(template, module, transId, script, null);
	}


	public ModelAndView getDefaultMav(String template, String module, String transId, String script, String scriptJs) {
		String portal = messageService.getMessage("app.portal.type");
		ModelAndView mav = new ModelAndView(template);
		mav.addObject(AppConstants.PORTAL_MODULE, module);
		mav.addObject(AppConstants.PORTAL_TRANS_ID, transId);
		mav.addObject(AppConstants.PORTAL_TYPE, portal);
		mav.addObject(AppConstants.PORTAL_SCRIPT, script);
		mav.addObject(AppConstants.PORTAL_SCRIPT_JS, scriptJs);
		return mav;
	}


	public String getPwordEkey() {
		return messageService.getMessage(BaseConfigConstants.SVC_IDM_EKEY);
	}


	public String getRealPath() {
		return context.getRealPath("/");
	}

	// need to change this to get current user profile object
	public UserProfile getCurrentUser(String userId) {
		UserProfile userProfile = new UserProfile();

		UserType userType = new UserType();
		userType.setUserTypeCode("INT");
		userProfile.setUserRoleGroupCode("DQ_ADMIN");

		List<UserRole> userRoleList = new ArrayList<>();
		userRoleList.add(new UserRole("DQ_USER", "DQ_USER"));
		userRoleList.add(new UserRole("DQ_ADMIN", "DQ_ADMIN"));
		userProfile.setRoles(userRoleList);
		return userProfile;
	}


	// need to change this to get current user id
	public String getCurrentUserId(HttpServletRequest request) {
		return String.valueOf(request.getAttribute("currUserId"));
	}


	public String getClientId(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (BaseUtil.isObjNull(auth)) {
			auth = request.getHeader("authorization");
		}

		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(auth,
				AuthCredentials.TOKEN_TYPE_STATIC);
		return authCredentials.getClient();
	}


	// need to change this to get current user profile object
	public boolean hasAnyRole(List<String> roleList, String userId) {
		Map<String, String> roleMap = new HashMap<>();

		if (!BaseUtil.isListNullZero(roleList)) {
			// load all roles
			UserProfile userProfile = getCurrentUser(userId);
			if (!BaseUtil.isObjNull(userProfile)) {
				List<UserRole> userRoleList = userProfile.getRoles();
				for (UserRole userRole : userRoleList) {
					roleMap.put(userRole.getRoleCode(), userRole.getRoleCode());
				}
			}

			for (String role : roleList) {
				if (roleMap.containsKey(role)) {
					return true;
				}
			}
		}

		return false;
	}


	protected Map<String, Object> getPaginationRequest(HttpServletRequest request, boolean isInitSearch) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> en = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			String[] value = request.getParameterValues(name);
			sb.append("\n\t" + name + " = " + value[0]);
			map.put(name, value[0]);
		}
		map.put("initSearch", isInitSearch);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SEARCH CRITERIA: {}", sb.toString());
		}
		LOGGER.info("SEARCH CRITERIA: {}", sb.toString());
		return map;
	}


	protected Documents getDocInCache(String dmBucket, String docMgtId, HttpServletRequest request) {
		String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(docMgtId);
		Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);
		Documents doc = null;
		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			doc = (Documents) cv.get();
		} else {
			doc = getDmService(request, dmBucket).download(docMgtId);
			if (!BaseUtil.isObjNull(doc)) {
				cache.put(cacheKey, doc);
			}
		}
		return doc;
	}


}