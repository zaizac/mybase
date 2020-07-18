/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bff.core.AbstractController;
import com.bff.util.constants.CacheConstants;
import com.bff.util.constants.PageConstants;
import com.util.QRGenerator;


/**
 * @author mary.jane
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_CMN_QR)
public class QRController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QRController.class);

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	public static final String API_QR_IMAGE = "API_QR_IMAGE";

	public static final String API_QR_GENERATE = "API_QR_GENERATE";

	public static final String API_QR_VERIFY = "API_QR_VERIFY";

	public static final String API_QR_CODE_GENERATE = "API_QR_CODE_GENERATE";

	public static final String API_QR_CODE_AUTHORIZE = "API_QR_CODE_AUTHORIZE";


	@GetMapping(value = "/generate", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<byte[]> printSticker(@RequestParam(defaultValue = API_QR_IMAGE) String trxnNo,
			@RequestParam(value = "license") String license, @RequestParam(value = "guid") String guid,
			HttpSession session) {

		String cacheKey = CacheConstants.CACHE_KEY_QR.concat(license).concat("~").concat(guid);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);

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

		Map<String, Object> map = new HashMap<>();
		map.put("license", license);
		map.put("guid", guid);
		BufferedImage bi = QRGenerator.generateQRCode(300, "bmi", license);
		byte[] fb = null;
		if (bi != null) {
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ImageIO.write(bi, "jpg", baos);
				baos.flush();
				fb = baos.toByteArray();
				// Save value to Cache for later validation
				cache.put(cacheKey, map);
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE));
		String filename = cacheKey + ".png";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(fb, headers, HttpStatus.OK);
	}

	// @PostMapping(value = "/verify", consumes = {
	// MediaType.APPLICATION_JSON_VALUE }, produces = {
	// MediaType.APPLICATION_JSON_VALUE })
	// public MessageResponse verifyQrCode(@RequestParam(defaultValue =
	// API_QR_VERIFY) String trxnNo,
	// HttpServletRequest request) {
	//
	// if (BaseUtil.isObjNull(qrDigitalId.getUuid()) &&
	// BaseUtil.isObjNull(qrDigitalId.getRefNo())
	// && BaseUtil.isObjNull(qrDigitalId.getMachineId())) {
	// return new MessageResponse(BeErrorCodeEnum.E400BST005,
	// request.getRequestURI(), null);
	// }
	//
	// BidUserProfile bidUserProfile =
	// bidUserProfileSvc.findUserByRefNo(qrDigitalId.getRefNo());
	// SecUserDevice secUserDevice = secUserDeviceSvc
	// .findDeviceByUidMachineId(qrDigitalId.getDeviceInfo().getMachineId(),
	// qrDigitalId.getUuid());
	//
	// if (BaseUtil.isObjNull(bidUserProfile) ||
	// BaseUtil.isObjNull(secUserDevice)) {
	// return new MessageResponse(BeErrorCodeEnum.E404BST001,
	// request.getRequestURI(), null);
	// }
	//
	// String cacheKey =
	// CacheConstants.CACHE_KEY_QR.concat(qrDigitalId.getUuid());
	// Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
	// Cache.ValueWrapper cv = cache.get(cacheKey);
	// if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
	// qrDigitalId.setRespFlag(true);
	// } else {
	// return new MessageResponse(BeErrorCodeEnum.E404BST001,
	// request.getRequestURI(), null);
	// }
	//
	// return new MessageResponse(BeErrorCodeEnum.E200BST000,
	// request.getRequestURI(), null);
	// }

}