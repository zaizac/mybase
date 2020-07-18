package com.bff.cmn.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bff.core.AbstractController;
import com.bff.idm.form.ComponentsForm;
import com.bff.util.constants.CacheConstants;
import com.bff.util.constants.PageConstants;
import com.util.BaseUtil;
import com.util.CaptchaGenerator;


/**
 * @author Mary Jane Buenaventura
 * @since 18 Jul 2018
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_CMN_CAPTCHA)
public class CaptchaController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@GetMapping(value = "/{portal}/{page}", produces = { MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody ResponseEntity<byte[]> generateImage(@PathVariable String portal,@PathVariable String page,
			@RequestParam("uniqueId") String uniqueId, HttpServletRequest request) {

		String cacheKey = CacheConstants.CACHE_KEY_CAPTCHA.concat(portal).concat("~").concat(page).concat("~").concat(uniqueId);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("portal: {}", portal);
			LOGGER.debug("uniqueId: {}", uniqueId);
			LOGGER.debug("Page: {}", page);
			LOGGER.debug(cacheKey);
		}

		// Evict existing cachekey
		Set<String> redisKeys = redisTemplate.keys("*" + cacheKey + "*");
		Iterator<String> it = redisKeys.iterator();
		while (it.hasNext()) {
			String data = it.next();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("redisKey: {}", data);
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
		return new ResponseEntity<>(fb, headers, HttpStatus.OK);
	}


	@PostMapping(value = "/{portal}/{page}/validate", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> validateCaptcha(@PathVariable String page,@PathVariable String portal,
			@RequestParam("uniqueId") String uniqueId, HttpServletRequest request, @RequestBody ComponentsForm components) {
		Map<String, Object> result = new HashMap<>();
		result.put("isValid", false);
		if (!BaseUtil.isObjNull(components) && !BaseUtil.isObjNull(components.getCaptcha())  &&
				!BaseUtil.isObjNull(page) && !BaseUtil.isObjNull(uniqueId) && !BaseUtil.isObjNull(portal) ) {
			String cacheKey = CacheConstants.CACHE_KEY_CAPTCHA.concat(portal).concat("~").concat(page).concat("~").concat(uniqueId);
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