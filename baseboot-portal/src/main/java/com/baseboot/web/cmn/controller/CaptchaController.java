/**
 * 
 */
package com.baseboot.web.cmn.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baseboot.web.constants.CacheConstants;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.idm.form.ComponentsForm;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since 18 Jul 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_CMN_CAPTCHA)
public class CaptchaController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@RequestMapping(value = "/{page}", params = "captchaId", method = RequestMethod.GET, produces = {
			MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody ResponseEntity<byte[]> generateImage(@PathVariable String page,
			@RequestParam("captchaId") String captchaId, HttpServletRequest request, HttpSession session) {

		String cacheKey = CacheConstants.CACHE_KEY_CAPTCHA.concat(page).concat("~").concat(session.getId());
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("captchaId: {}", captchaId);
			LOGGER.debug("Page: {}", page);
			LOGGER.debug("Session ID: {}", session.getId());
			LOGGER.debug(cacheKey);
		}

		// Evict existing cachekey
		Set<String> redisKeys = redisTemplate.keys("*" + cacheKey + "*");
		Iterator<String> it = redisKeys.iterator();
		while (it.hasNext()) {
			String data = it.next();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("redisKey: {}" + data);
			}
			cache.evict(data);
		}

		CaptchaGenerator captchaGenerator = new CaptchaGenerator();
		byte[] fb = null;

		// Generate NEW Captcha
		Map<String, Object> map = captchaGenerator.getCaptchaShadowedText(6);
		if (!BaseUtil.isObjNull(map)) {
			BufferedImage image = (BufferedImage) map.get(CaptchaGenerator.CPTCHIMG);
			if (!BaseUtil.isObjNull(image)) {
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					ImageIO.write(image, "png", baos);
					baos.flush();
					fb = baos.toByteArray();
					baos.close();
					// Save value to Cache for later validation
					cache.put(cacheKey, map.get(CaptchaGenerator.CPTCHVAL));
				} catch (IOException e) {
					LOGGER.error("IOException: {}", e);
				}
			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE));
		String filename = cacheKey + ".png";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fb, headers, HttpStatus.OK);
		return response;
	}


	@RequestMapping(value = "/{page}/validate", params = "captchaId", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> validateCaptcha(@PathVariable String page, @RequestParam("captchaId") String captchaId,
			ComponentsForm components, HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isValid", false);
		if (!BaseUtil.isObjNull(page) && !BaseUtil.isObjNull(captchaId) 
				&& !BaseUtil.isObjNull(components) && !BaseUtil.isObjNull(components.getCaptcha())) {
			String cacheKey = CacheConstants.CACHE_KEY_CAPTCHA.concat(page).concat("~").concat(session.getId());
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			Cache.ValueWrapper cv = cache.get(cacheKey);
			String captchaValue = components.getCaptcha();
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				String capVal = (String) cv.get();
				LOGGER.info("Captcha Code: {}", capVal);
				LOGGER.info("Captcha Response: {}", captchaValue);
				if (!BaseUtil.isObjNull(capVal) && capVal.equals(captchaValue)) {
					result.put("isValid", true);
				}
			}

		}

		return result;
	}

}