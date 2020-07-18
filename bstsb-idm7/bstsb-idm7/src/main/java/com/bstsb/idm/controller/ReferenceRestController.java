/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.config.cache.RedisCacheWrapper;
import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmConfig;
import com.bstsb.idm.sdk.constants.IdmCacheConstants;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.LoginDto;
import com.bstsb.idm.service.IdmConfigService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.CryptoUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class ReferenceRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceRestController.class);

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private IdmConfigService idmConfigSvc;


	@GetMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> idmConfig(HttpServletRequest request) {
		List<IdmConfig> confLst = idmConfigSvc.primaryDao().findAll();
		Map<String, String> config = new HashMap<>();

		if (confLst.isEmpty()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		for (IdmConfig conf : confLst) {
			config.put(conf.getConfigCode(), conf.getConfigVal());
		}
		return config;
	}


	@PostMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> createIdmConfig(@RequestBody List<IdmConfig> idmConfigLst, HttpServletRequest request) {
		if (BaseUtil.isListNullZero(idmConfigLst)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		idmConfigSvc.updateAll(idmConfigLst, getCurrUserId(request));

		return idmConfig(request);
	}


	@GetMapping(value = IdmUrlConstants.REFERENCE + "/evict", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		LOGGER.debug("Cache Evict with Prefix: {}", prefixKey);
		RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(IdmCacheConstants.CACHE_BUCKET);
		if (!BaseUtil.isObjNull(prefixKey)) {
			cache.evictByPrefix(prefixKey);
		} else {
			cache.clear();
			return true;
		}
		return false;
	}


	@PostMapping(value = IdmUrlConstants.ENCRYPT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String encrypt(@Valid @RequestBody LoginDto loginDto, @RequestParam(required = false) boolean isHashKey,
			HttpServletRequest request) throws NoSuchAlgorithmException {
		if (BaseUtil.isObjNull(loginDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		if (isHashKey) {
			return getHash(loginDto.getPassword());
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ENCRYPT);
		return CryptoUtil.encrypt(loginDto.getPassword(), loginDto.getClientSecret());
	}


	@PostMapping(value = IdmUrlConstants.DECRYPT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String decrypt(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
		if (BaseUtil.isObjNull(loginDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_DECRYPT);
		return CryptoUtil.decrypt(loginDto.getPassword(), loginDto.getClientSecret());
	}

}