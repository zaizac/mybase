/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.web.constants.CacheConstants;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.idm.form.ComponentsForm;
import com.baseboot.web.util.JsonUtil;
import com.baseboot.web.util.WebUtil;
import com.be.sdk.constants.MailTemplateConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;
import com.idm.sdk.exception.IdmException;
import com.notify.sdk.constants.MailTypeEnum;
import com.notify.sdk.model.Notification;
import com.notify.sdk.util.MailUtil;
import com.util.BaseUtil;
import com.util.DateUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_CMN_OTP)
public class OTPController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

	private static final String EMAIL_OTP_NO = "EMAIL_OTP_NO";

	private static final String EMAIL_OTP_TIME = "EMAIL_OTP_TIME";

	@Autowired
	CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public @ResponseBody String generateOtp(@RequestParam String emailAddress,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "otpId") String otpId, HttpSession session) {

		String cacheKey = CacheConstants.CACHE_KEY_OTP.concat(otpId).concat("~").concat(session.getId());
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("emailAddress : {}", emailAddress);
			LOGGER.debug("otpId: {}", otpId);
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

		String otp = generateOtp();

		// Send OTP to Email
		Map<String, Object> map = new HashMap<>();
		map.put("GEN_DATE", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.s").format(new Date()));
		map.put("otp", otp);
		Notification notification = new Notification();
		notification.setNotifyTo(emailAddress);
//		notification.setSubject(MailTemplateConstants.CMN_O);
		notification.setMetaData(MailUtil.convertMapToJson(map));
		try {
//			getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.CMN_OTP);
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error("IdmException: {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
		}

		boolean hasSmsNotif = false; // Boolean.valueOf(staticData.jlsConfig().get(BeConfigConstants.SMS_SWITCH));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SMS NOTIFICATION: {}", hasSmsNotif);
		}
		if (mobile != null && hasSmsNotif) {
			String newmobile = mobile;
			try {
				String content = messageService.getMessage("lbl.otp.msg.mobile", new String[] { otp });
//				getNotifyService().sendSms(newmobile, content);
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
			return messageService.getMessage("lbl.otp.gen.succ", new String[] { emailAddress }) + "*^*1";
		} else {
			return messageService.getMessage("lbl.otp.gen.fail") + "*^*0";
		}
	}


	public String generateOtp() {
		long millSecond = getSQLTimestamp().getTime();
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
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> validate(@RequestParam("otpId") String otpId, ComponentsForm components,
			HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		result.put("isValid", false);
		result.put("isExpired", false);
		if (!BaseUtil.isObjNull(components) && !BaseUtil.isObjNull(components.getOtpCode())) {
			String cacheKey = CacheConstants.CACHE_KEY_OTP.concat(otpId).concat("~").concat(session.getId());
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
						int timeDiff = timeDiffMins(session, (String) map.get(EMAIL_OTP_TIME));
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Time Diff: {}", timeDiff);
						}

						// if more than 15 mins expired
						if (timeDiff > (15 * 60)) {
							result.put("isExpired", true);
							result.put("message", messageService.getMessage("lbl.otp.exprd.regen"));
						} else {
							result.put("message", messageService.getMessage("lbl.otp.vrfy.succ"));
						}

						result.put("isValid", true);
					} else {
						result.put("message", messageService.getMessage("lbl.otp.vrfy.fail"));
					}
				} catch (JsonParseException e) {
					LOGGER.error("JsonParseException: {}", e.getMessage());
				} catch (JsonMappingException e) {
					LOGGER.error("JsonMappingException: {}", e.getMessage());
				} catch (IOException e) {
					LOGGER.error("IOException: {}", e.getMessage());
				}
			} else {
				result.put("message", messageService.getMessage("lbl.otp.vrfy.fail"));
			}

		} else {
			result.put("message", messageService.getMessage("lbl.otp.vrfy.fail"));
		}

		return result;
	}


	private int timeDiffMins(HttpSession session, String otpGenTime) {
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