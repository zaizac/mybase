/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.be.sdk.model.Metadata;
import com.dialect.iam.CustomUserDetails;
import com.dialect.iam.CustomUserProfile;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.model.Documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.portal.config.MultiPartPropertyEditor;
import com.portal.constants.AppConstants;
import com.portal.constants.ProjectEnum;
import com.portal.web.util.StaticData;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.CustomMultipartFile;


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
	protected Mapper dozerMapper;

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
		mav.addObject(AppConstants.PORTAL_MODULE, ProjectEnum.OUTBREAK.getPrefix());
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


	public boolean isUserAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// is user logged in
		return !(auth instanceof AnonymousAuthenticationToken);
	}


	public String getPwordEkey() {
		return messageService.getMessage(BaseConfigConstants.SVC_IDM_EKEY);
	}


	public String getRealPath() {
		return context.getRealPath("/");
	}


	public List<String> getCurrentUserRoles() {
		UserProfile up = getCurrentUser();
		List<String> roleLst = new ArrayList<>();
		for (UserRole roles : up.getRoles()) {
			roleLst.add(roles.getRoleCode());
		}
		return roleLst;
	}


	public UserProfile getCurrentUser() {
		UserProfile userProfile = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			userProfile = cud.getProfile();
		}
		return userProfile;
	}


	public String getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			return auth.getName();
		}
		return "";
	}


	public String getCurrentUserFullName() {
		UserProfile userProfile = getCurrentUser();
		if (!BaseUtil.isObjNull(userProfile)) {
			return userProfile.getFullName();
		}
		return "";
	}


	public String getCurrUserType() {
		UserProfile userProfile = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			userProfile = cud.getProfile();
			if (userProfile != null && userProfile.getUserType() != null) {
				return userProfile.getUserType().getUserTypeCode();
			}
		}
		return "";
	}


	public boolean hasRole(String role) {
		UserProfile userProfile = getCurrentUser();
		if (!BaseUtil.isObjNull(userProfile)) {
			List<UserRole> userRoleList = userProfile.getRoles();
			for (UserRole userRole : userRoleList) {
				if (BaseUtil.isEquals(userRole.getRoleCode(), role)) {
					return true;
				}
			}
		}
		return false;
	}


	public boolean hasAnyRole(String... roles) {
		if (!BaseUtil.isObjNull(roles)) {
			List<String> roleList = Arrays.asList(roles);
			if (!BaseUtil.isListNull(roleList)) {
				roleList.removeAll(Collections.singleton(null));
				roleList.removeAll(Collections.singleton(""));
				LOGGER.debug("splited roleList: {}", roleList);
			}
			return hasAnyRole(roleList);
		}
		return false;
	}


	public boolean hasAnyRole(List<String> roleList) {
		Map<String, String> roleMap = new HashMap<>();

		if (!BaseUtil.isListNullZero(roleList)) {
			// load all roles
			UserProfile userProfile = getCurrentUser();
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
			LOGGER.debug("SEARCH CRITERIA: {}", sb);
		}
		return map;
	}


	public CustomUserProfile getCurrProfile() {
		CustomUserProfile profile = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			profile = cud.getUserProfile();
		}
		return profile;
	}


	protected Documents getDocInCache(ProjectEnum projId, String docMgtId) throws Exception {
		String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(docMgtId);
		Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);
		Documents doc = null;
		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			doc = (Documents) cv.get();
		} else {
			doc = getDmService(projId).download(docMgtId);
			if (!BaseUtil.isObjNull(doc)) {
				cache.put(cacheKey, doc);
			}
		}
		return doc;
	}


	public String convertAppNoSlashToDash(String appNo) {
		if (appNo == null) {
			return null;
		}
		return appNo.replace(AppConstants.SLASH, AppConstants.DASH);
	}


	public String convertAppNoDashToSlash(String appNo) {
		if (appNo == null) {
			return null;
		}
		return appNo.replace(AppConstants.DASH, AppConstants.SLASH);
	}


	public Integer getAddrMtDtId(String addMtDtCd) {
		Metadata othAddrMtdt = new Metadata();
		othAddrMtdt.setMtdtType("ADDR_TYPE");
		othAddrMtdt.setMtdtCd(addMtDtCd);
		List<Metadata> metaDataLst = getBeService().reference().findMetadataByCriteria(othAddrMtdt);
		if (!BaseUtil.isListNull(metaDataLst)) {
			return metaDataLst.get(0).getMtdtId();
		}
		return -1;
	}

}