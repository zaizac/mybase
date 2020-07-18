/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.BeConfigConstants;
import com.be.sdk.constants.MailTemplateConstants;
import com.bff.core.AbstractController;
import com.bff.idm.form.ComponentsForm;
import com.bff.util.WebUtil;
import com.bff.util.constants.CacheConstants;
import com.bff.util.constants.MessageConstants;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;
import com.idm.sdk.exception.IdmException;
import com.notify.sdk.model.Notification;
import com.notify.sdk.util.MailUtil;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_CMN_OTP)
public class OTPController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

	private static final String EMAIL_OTP_NO = "EMAIL_OTP_NO";

	private static final String EMAIL_OTP_TIME = "EMAIL_OTP_TIME";

	private static final String MESSAGE = "message";

	@Autowired
	CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@GetMapping(value = "/generate/{portal}")
	public @ResponseBody String generateOtp(@PathVariable String portal,@RequestParam String emailAddress,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "otpUniqueId") String otpUniqueId) {

		String otp = generateOtp();

		String cacheKey = CacheConstants.CACHE_KEY_OTP.concat(portal).concat("~").concat(otpUniqueId);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("emailAddress : {}", emailAddress);
			LOGGER.debug("otpId: {}", otpUniqueId);
			LOGGER.debug(cacheKey);
		}
		
		removeExistCacheKey(cache, cacheKey);

		// Send OTP to Email
		Map<String, Object> map = new HashMap<>();
		map.put("otp", otp);
		Notification notification = new Notification();
		notification.setNotifyTo(emailAddress);
		notification.setMetaData(MailUtil.convertMapToJson(map));
		try {
			getNotifyService().addNotification(notification, MailTemplateConstants.CMN_OTP);
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error("IdmException: {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
		}

		boolean hasSmsNotif = Boolean.parseBoolean(staticData.beConfig().get(BeConfigConstants.SMS_SWITCH));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SMS NOTIFICATION: {}", hasSmsNotif);
		}
		if (mobile != null && hasSmsNotif) {
			String newmobile = mobile;
			try {
				String content = messageService.getMessage("lbl.otp.msg.mobile", new String[] { otp });
				getNotifyService().sendSms(newmobile, content, null);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("SMS Sent: {} - {}", newmobile, content);
				}
			} catch (IdmException e) {
				if (WebUtil.checkSystemDown(e)) {
					throw e;
				}
				LOGGER.error("IdmException: {}", e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Generated OTP: {}", otp);
		}
		if (!BaseUtil.isObjNull(otp)) {
			JsonObject obj = new JsonObject();
			obj.addProperty(EMAIL_OTP_NO, otp);
			obj.addProperty(EMAIL_OTP_TIME, String.valueOf(DateUtil.getSQLTimestamp().getTime()));
			cache.put(cacheKey, obj.toString());
			return messageService.getMessage(MessageConstants.SUCC_OTP_GEN, new String[] { emailAddress }) + "*^*1";
		} else {
			return messageService.getMessage(MessageConstants.ERROR_OTP_GEN) + "*^*0";
		}
	}


	public String generateOtp() {
		long millSecond = DateUtil.getSQLTimestamp().getTime();
		String millSecondStr = String.valueOf(millSecond);
		return millSecondStr.substring(millSecondStr.length() - 6);
	}


	/**
	 * Validate OTP
	 *
	 * @param code
	 * @param session
	 * @return 2 = expired, 1 = valid, 0 = failed
	 */
	@PostMapping(value = "/validate/{portal}")
	public @ResponseBody Map<String, Object> validate(@PathVariable String portal,@RequestParam(value = "otpUniqueId") String otpUniqueId, @RequestBody ComponentsForm components) {
		Map<String, Object> result = new HashMap<>();
		result.put("isValid", false);
		result.put("isExpired", false);
		if (!BaseUtil.isObjNull(components) && !BaseUtil.isObjNull(components.getOtpCode()) && !BaseUtil.isObjNull(portal) &&  !BaseUtil.isObjNull(otpUniqueId) ) {
			String cacheKey = CacheConstants.CACHE_KEY_OTP.concat(portal).concat("~").concat(otpUniqueId);
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			Cache.ValueWrapper cv = cache.get(cacheKey);
			String otpValue = components.getOtpCode();
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				String otpVal = (String) cv.get();
				JsonUtil.toJsonNode(otpVal);
				try {
					Map<String, Object> map = JsonUtil.convertJsonToMap(otpVal);
					otpVal = (String) map.get(EMAIL_OTP_NO);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.info("OTP Code: {}", map.get(EMAIL_OTP_NO));
						LOGGER.info("OTP Code: {}", map.get(EMAIL_OTP_TIME));
						LOGGER.info("OTP Response: {}", otpValue);
						LOGGER.info("Matched? {}", otpVal.equals(otpValue));
					}
					if (!BaseUtil.isObjNull(otpVal) && otpVal.equals(otpValue)) {
						int timeDiff = timeDiffMins((String) map.get(EMAIL_OTP_TIME));
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Time Diff: {}", timeDiff);
						}

						// if more than 15 mins expired
						if (timeDiff > (15 * 60)) {
							result.put("isExpired", true);
							result.put(MESSAGE,
									messageService.getMessage(MessageConstants.ERROR_OTP_REGEN_EXPIRED));
						} else {
							result.put(MESSAGE, messageService.getMessage(MessageConstants.SUCC_OTP_VERIFY));
						}

						result.put("isValid", true);
					} else {
						result.put(MESSAGE, messageService.getMessage(MessageConstants.ERROR_OTP_VERIFY));
					}
				} catch (JsonParseException e) {
					LOGGER.error("JsonParseException: {}", e.getMessage());
				} catch (JsonMappingException e) {
					LOGGER.error("JsonMappingException: {}", e.getMessage());
				} catch (IOException e) {
					LOGGER.error("IOException: {}", e.getMessage());
				}
				
				
			} else {
				result.put(MESSAGE, messageService.getMessage(MessageConstants.ERROR_OTP_VERIFY));
			}
			
			LOGGER.info("OTP cache", cv);
			removeExistCacheKey(cache, cacheKey);
		} else {
			result.put(MESSAGE, messageService.getMessage(MessageConstants.ERROR_OTP_VERIFY));
		}
		return result;
	}
	
	// Evict existing cache key
	private void removeExistCacheKey(Cache  cache, String cacheKey) {
		Set<String> redisKeys = redisTemplate.keys("*" + cacheKey + "*");
		Iterator<String> it = redisKeys.iterator();
		while (it.hasNext()) {
			String data = it.next();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("redisKey: {}", data);
			}
			cache.evict(data);
		}
	}


	private int timeDiffMins(String otpGenTime) {
		long genTime = 0;

		if (!BaseUtil.isObjNull(otpGenTime)) {
			genTime = Long.parseLong(otpGenTime);
		}

		long currTime = DateUtil.getSQLTimestamp().getTime();

		long timeDiff = currTime - genTime;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("timeDiff: {}", timeDiff);
		}

		timeDiff = timeDiff / 1000;

		return (int) timeDiff;
	}

}