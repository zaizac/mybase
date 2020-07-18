/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baseboot.web.constants.CacheConstants;
import com.baseboot.web.constants.MessageConstants;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.PageTemplate;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.util.WebUtil;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.PopupBox;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_CMN_STATIC)
public class StaticListController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticListController.class);

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, "mntce", "statlst");
		return mav;
	}


	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ModelAndView refreshStaticList(@RequestParam("staticlistType") String staticlistType,
			HttpServletRequest request) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, "mntce", "statlst");
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


	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView refreshAll(HttpServletRequest request) throws Exception {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_CMN_STTC_LST, "mntce", "statlst");
		try {
			processEvict(null);
			mav.addAllObjects(PopupBox.success(null, null,
					messageService.getMessage(MessageConstants.SUCC_STLST_REFRSH), PageConstants.PAGE_CMN_STATIC));
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			mav.addAllObjects(PopupBox.error(messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
		return mav;
	}


	private String processEvict(String prefixKey) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n[Clearing Cache Buckets:");
		if (!BaseUtil.isObjNull(prefixKey)) {
			sb.append(" key - " + prefixKey);
		}
		try {
			if (!BaseUtil.isObjNull(prefixKey)) {
				Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
				Set<String> redisKeys = redisTemplate.keys(CacheConstants.CACHE_PREFIX + "*" + prefixKey + "*");
				// Store the keys in a List
				Iterator<String> it = redisKeys.iterator();
				while (it.hasNext()) {
					String data = it.next();
					LOGGER.debug("redisKey: {}" + data);
					cache.evict(data);
				}
			} else {
				Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
				cache.clear();
				sb.append("\n\tPORTAL EMP : " + true);
			}
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error("IdmException: {}", e.getMessage());
			sb.append("\n\tPORTAL EMP : " + false);
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			sb.append("\n\tPORTAL EMP : " + false);
		}

		try {
			if (!BaseUtil.isObjNull(prefixKey)) {
				sb.append("\n\tIDM : " + getIdmService().evict(prefixKey));
			} else {
				sb.append("\n\tIDM : " + getIdmService().evict());
			}
		} catch (IdmException e) {
			LOGGER.error("IdmException: {}", e.getMessage());
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			sb.append("\n\tIDM : " + false);
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			sb.append("\n\tIDM : " + false);
		}

		try {
			if (!BaseUtil.isObjNull(prefixKey)) {
				sb.append("\n\tREPORT : " + getReportService().evict(prefixKey));
			} else {
				sb.append("\n\tREPORT : " + getReportService().evict());
			}
		} catch (IdmException e) {
			LOGGER.error("IdmException: {}", e.getMessage());
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			sb.append("\n\tREPORT : " + false);
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			sb.append("\n\tREPORT : " + false);
		}

		sb.append("\n]");
		return sb.toString();
	}

}