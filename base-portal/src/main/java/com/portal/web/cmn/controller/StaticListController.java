/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.cmn.controller;


import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.idm.sdk.exception.IdmException;
import com.portal.constants.CacheConstants;
import com.portal.constants.MessageConstants;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.core.AbstractController;
import com.portal.web.util.WebUtil;
import com.util.BaseUtil;
import com.util.PopupBox;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_CMN_STATIC)
public class StaticListController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticListController.class);

	private static final String PFX_IDM = "\n\tIDM : ";

	private static final String PFX_PORTAL = "\n\tPORTAL EMP : ";

	private static final String PFX_REPORT = "\n\tREPORT : ";

	private static final String PFX_MNTCE = "mntce";

	private static final String PFX_STATLST = "statlst";

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@GetMapping()
	public ModelAndView view() {
		return getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, PFX_MNTCE, PFX_STATLST);
	}


	@GetMapping(value = "/refresh")
	public ModelAndView refreshStaticList(@RequestParam("staticlistType") String staticlistType,
			HttpServletRequest request) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, PFX_MNTCE, PFX_STATLST);
		String listName = "";
		String result = null;
		try {
			LOGGER.debug("Refresh static list : {}", staticlistType);
			result = processEvict(staticlistType);
			mav.addAllObjects(PopupBox.success(null, null,
					listName + messageService.getMessage(MessageConstants.SUCC_REFRSH),
					PageConstants.PAGE_CMN_STATIC));
		} catch (Exception e) {
			mav.addAllObjects(PopupBox
					.error(listName + messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
		LOGGER.debug(result);
		return mav;
	}


	@PostMapping()
	public ModelAndView refreshAll(HttpServletRequest request) throws Exception {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, PFX_MNTCE, PFX_STATLST);
		try {
			processEvict(null);
			mav.addAllObjects(PopupBox.success(null, null,
					messageService.getMessage(MessageConstants.SUCC_STLST_REFRSH), PageConstants.PAGE_CMN_STATIC));
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			mav.addAllObjects(PopupBox.error(messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
		return mav;
	}


	private String processEvict(String prefixKey) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("\n[Clearing Cache Buckets:");
		if (!BaseUtil.isObjNull(prefixKey)) {
			sb.append(" key - " + prefixKey);
		}
		try {
			evictCache(prefixKey, sb);
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			sb.append(PFX_PORTAL + false);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			sb.append(PFX_PORTAL + false);
		}

		try {
			evictIdmService(prefixKey, sb);
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			sb.append(PFX_IDM + false);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			sb.append(PFX_IDM + false);
		}


		sb.append("\n]");
		return sb.toString();
	}


	private void evictCache(String prefixKey, StringBuilder sb) {
		if (!BaseUtil.isObjNull(prefixKey)) {
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			Set<String> redisKeys = redisTemplate.keys(CacheConstants.CACHE_PREFIX + "*" + prefixKey + "*");
			// Store the keys in a List
			Iterator<String> it = redisKeys.iterator();
			while (it.hasNext()) {
				String data = it.next();
				LOGGER.debug("redisKey: {}", data);
				cache.evict(data);
			}
		} else {
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			cache.clear();
			sb.append(PFX_PORTAL + true);
		}
	}


	private void evictIdmService(String prefixKey, StringBuilder sb) {
		if (!BaseUtil.isObjNull(prefixKey)) {
			sb.append(PFX_IDM + getIdmService().evict(prefixKey));
		} else {
			sb.append(PFX_IDM + getIdmService().evict());
		}

	}

}