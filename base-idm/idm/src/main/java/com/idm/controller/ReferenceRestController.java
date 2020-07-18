package com.idm.controller;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.config.cache.RedisCacheWrapper;
import com.idm.constants.IdmTxnCodeConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmConfig;
import com.idm.model.IdmPortalType;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.IdmConfigDto;
import com.idm.sdk.model.LoginDto;
import com.idm.sdk.model.PortalType;
import com.idm.service.IdmConfigService;
import com.idm.service.IdmPortalTypeService;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.JsonUtil;
import com.util.constants.BaseConstants;


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

	@Autowired
	private IdmPortalTypeService idmPortalTypeSvc;


	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG + IdmUrlConstants.PORTALTYPE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<IdmConfigDto> idmConfigByPortal(@RequestParam(value = "portalType") String portalType,
			HttpServletRequest request) throws IOException {
		List<IdmConfig> confLst = idmConfigSvc.findAllByPortalType(portalType);

		if (confLst.isEmpty()) {
			return new ArrayList<>();
		}

		return JsonUtil.transferToList(confLst, IdmConfigDto.class);
	}


	@PostMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG + IdmUrlConstants.PORTALTYPE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<IdmConfigDto> createIdmConfigByPortal(@RequestParam(value = "portalType") String portalType,
			@RequestBody List<IdmConfig> idmConfigLst, HttpServletRequest request) throws IOException {
		if (BaseUtil.isListNullZero(idmConfigLst)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		idmConfigSvc.updateAll(idmConfigLst, getCurrUserId(request));

		return idmConfigByPortal(portalType, request);
	}


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


	@GetMapping(value = IdmUrlConstants.PORTALTYPE_SEARCH_BY_PORTALTYPECODE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public PortalType retrieveIdmPortalTypeByPortalTypeCode(
			@RequestParam(value = "portalTypeCode") String portalTypeCode) throws IOException {
		IdmPortalType idmPortalType = idmPortalTypeSvc.findByPortalTypeCode(portalTypeCode);
		return JsonUtil.transferToObject(idmPortalType, PortalType.class);
	}


	@GetMapping(value = IdmUrlConstants.PORTALTYPE_SEARCH, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PortalType> retrieveAllIdmPortalType() throws IOException {
		List<IdmPortalType> result = idmPortalTypeSvc.findAll();
		return JsonUtil.transferToList(result, PortalType.class);
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.PORTALTYPE_CREATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PortalType> createIdmPortalType(@RequestBody List<IdmPortalType> portalTypes,
			HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(portalTypes)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		String userId = getCurrUserId(request);
		List<IdmPortalType> returnPortalTypes = new ArrayList<>();
		for (IdmPortalType portalType : portalTypes) {
			IdmPortalType idmPortalType = JsonUtil.transferToObject(portalType, IdmPortalType.class);
			idmPortalType.setCreateId(
					(BaseUtil.isObjNull(idmPortalType.getCreateId()) ? userId : idmPortalType.getCreateId()));
			idmPortalType.setUpdateId(
					(BaseUtil.isObjNull(idmPortalType.getUpdateId()) ? userId : idmPortalType.getUpdateId()));
			returnPortalTypes.add(idmPortalTypeSvc.create(portalType));
		}

		return JsonUtil.transferToList(portalTypes, PortalType.class);
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.PORTALTYPE_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PortalType> updateIdmPortalType(@RequestBody List<IdmPortalType> portalTypes,
			HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(portalTypes)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		String userId = getCurrUserId(request);
		List<IdmPortalType> returnPortalTypes = new ArrayList<>();
		for (IdmPortalType portalType : portalTypes) {
			IdmPortalType idmPortalType = JsonUtil.transferToObject(portalType, IdmPortalType.class);
			idmPortalType.setCreateId(
					(BaseUtil.isObjNull(idmPortalType.getCreateId()) ? userId : idmPortalType.getCreateId()));
			idmPortalType.setUpdateId(
					(BaseUtil.isObjNull(idmPortalType.getUpdateId()) ? userId : idmPortalType.getUpdateId()));
			returnPortalTypes.add(idmPortalTypeSvc.update(portalType));
		}

		return JsonUtil.transferToList(portalTypes, PortalType.class);
	}


	@PostMapping(value = IdmUrlConstants.PORTALTYPE_DELETE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Boolean deleteIdmPortalType(@RequestBody IdmPortalType portalType, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(portalType)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmPortalType idmPortalType = JsonUtil.transferToObject(portalType, IdmPortalType.class);
		return idmPortalTypeSvc.delete(idmPortalType);
	}


	/**
	 * Find All Config
	 *
	 * @return List<IdmConfigDto>
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.USER_CONFIG, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<IdmConfigDto> getAllUserConfig() throws IOException {
		List<IdmConfig> idmConfList = idmConfigSvc.findAllUserConfig();
		if (BaseUtil.isListNull(idmConfList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToList(idmConfList, IdmConfigDto.class);
	}


	/**
	 * Update User Config
	 *
	 * @param IdmConfigDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.USER_CONFIG_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public IdmConfigDto updateUserConfig(@Valid @RequestBody IdmConfigDto usrConfig, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(usrConfig)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		IdmConfig idmCfg = JsonUtil.transferToObject(usrConfig, IdmConfig.class);
		idmCfg.setUpdateId(usrConfig.getCreateId());
		idmCfg.setUpdateDt(new Timestamp(new Date().getTime()));
		IdmConfig idmUsrCfg = idmConfigSvc.updateUserConfig(idmCfg);
		return JsonUtil.transferToObject(idmUsrCfg, IdmConfigDto.class);
	}


	/**
	 * Fetch User Config by code
	 *
	 * @param menuCode
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.REFERENCE + IdmUrlConstants.FIND_USR_CONFIG_BY_CONFIG_CODE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public IdmConfigDto findUserConfigByConfigCode(@PathVariable String configCode) throws IOException {
		IdmConfig idmCfg = idmConfigSvc.findByConfigCode(configCode);
		if (idmCfg == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToObject(idmCfg, IdmConfigDto.class);
	}

}