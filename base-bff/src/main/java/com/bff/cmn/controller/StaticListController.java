package com.bff.cmn.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.ReferenceConstants;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.City;
import com.be.sdk.model.Country;
import com.be.sdk.model.Metadata;
import com.be.sdk.model.Reason;
import com.be.sdk.model.State;
import com.be.sdk.model.Status;
import com.bff.cmn.constants.BffUrlConstants;
import com.bff.cmn.form.StaticData;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.CacheConstants;
import com.bff.util.constants.MessageConstants;
import com.bff.util.constants.PageConstants;
import com.google.gson.GsonBuilder;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.IdmConfigDto;
import com.idm.sdk.model.PortalType; 
import com.idm.sdk.model.UserRole;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_REF_STATIC)
public class StaticListController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticListController.class);

	private static final String PFX_IDM = "\n\tIDM : ";

	private static final String PFX_PORTAL = "\n\tPORTAL EMP : ";
	
	private static final String INVALID_CONFIG_BODY_EMPTY = "Invalid config - Response Body is empty.";
	
	/** bff.properties - start **/
    @Value("${app.portal.ui.content:BFF_PORTAL_CONTENT}")
    private String portalUIContent;
    private static String portalUIContentDefault = "light";
    
    @Value("${app.portal.ui.layout:BFF_PORTAL_LAYOUT}")
    private String portalUILayout;
    private static String portalUILayoutDefault = "horizontal";
    
    @Value("${app.portal.ui.menu:BFF_PORTAL_MENU}")
    private String portalUIMenu;
    private static String portalUIMenuDefault = "top";
    
    @Value("${app.portal.ui.mode:BFF_PORTAL_MODE}")
    private String portalUIMode;
    private static String portalUIModeDefault = "light";
    
    @Value("${app.portal.ui.page:BFF_PORTAL_PAGE}")
    private String portalUIPage;
    private static String portalUIPageDefault = "default";
    
    @Value("${app.portal.ui.texture:BFF_PORTAL_TEXTURE}")
    private String portalUITexture;
    private static String portalUITextureDefault = "blue";
    
    @Value("${app.portal.ui.theme:BFF_PORTAL_THEME}")
    private String portalUITheme;
    private static String portalUIThemeDefault = "blue";
    
    /** bff.properties - end **/
    
	@Autowired
	private StaticData staticData;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	@GetMapping(value = "/config", produces = { MediaType.APPLICATION_JSON })
	public Map<String, String> config(@RequestParam(value = "config") String config) {
		if (BaseUtil.isObjNull(config)) {
			throw new BeException("Invalid config reference");
		}
		if ("IDM_CONFIG".equalsIgnoreCase(config)) {
			return staticData.idmConfig();
		} else if ("USER_STATUS".equalsIgnoreCase(config)) {
			return staticData.defaultUserStatusList();
		}
		return staticData.beConfig();
	}


	@PostMapping(value = "/config/updateConfig", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<IdmConfigDto> updateIdmConfig(@RequestParam(value = "portalType") String portalType,
			@RequestBody List<IdmConfigDto> idmConfigDtos, HttpServletRequest request) {
		if (BaseUtil.isObjNull(idmConfigDtos)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		if (BaseUtil.isStringNull(portalType)) {
			throw new BeException("Invalid portal type - Portal Type is empty.");
		}

		LOGGER.debug("Update Idm Config : {}", new GsonBuilder().create().toJson(idmConfigDtos));

		staticData.addIdmConfigByPortal(portalType, idmConfigDtos);
		return idmConfigDtos;
	}


	@GetMapping(value = "/config/portalType", produces = { MediaType.APPLICATION_JSON })
	public List<IdmConfigDto> configByPortalType(@RequestParam(value = "portalType") String portalType) {
		if (BaseUtil.isObjNull(portalType)) {
			throw new BeException("Invalid config reference - invalid Portal Type.");
		}
		// fetch from IDM_CONFIG and assign to array
		ArrayList<IdmConfigDto> returnIdmConfig = new ArrayList<> (staticData.configByPortalType(portalType));
		if (Boolean.TRUE.equals(BaseUtil.isListNullZero(returnIdmConfig))) {
			// regular expression "^([^_]+)" to find text before the first "_" found
			// example: BFF_PORTAL_CONTENT will return BFF
			final String regexFindFirstUnderline = "^([^_]+)";
			// reset default value from properties to variable portalType received
			portalUIContent = portalUIContent.replaceAll(regexFindFirstUnderline, portalType);
			portalUILayout  = portalUILayout.replaceAll(regexFindFirstUnderline, portalType);
			portalUIMenu    = portalUIMenu.replaceAll(regexFindFirstUnderline, portalType);
			portalUIMode    = portalUIMode.replaceAll(regexFindFirstUnderline, portalType);
			portalUIPage    = portalUIPage.replaceAll(regexFindFirstUnderline, portalType);
			portalUITexture = portalUITexture.replaceAll(regexFindFirstUnderline, portalType);
			portalUITheme   = portalUITheme.replaceAll(regexFindFirstUnderline, portalType);
			// if not exist in db set default value
			returnIdmConfig.add(new IdmConfigDto(0, portalUIContent, portalUIContent, portalUIContentDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUILayout,  portalUILayout,  portalUILayoutDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUIMenu,    portalUIMenu,    portalUIMenuDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUIMode,    portalUIMode,    portalUIModeDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUIPage,    portalUIPage,    portalUIPageDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUITexture, portalUITexture, portalUITextureDefault));
			returnIdmConfig.add(new IdmConfigDto(0, portalUITheme,   portalUITheme,   portalUIThemeDefault));
			LOGGER.info("Insert new Theme Switcher configuration : {}", new GsonBuilder().create().toJson(returnIdmConfig));
			// update db with new portal type setting
			staticData.addIdmConfigByPortal(portalType, returnIdmConfig);
		}
		
		return returnIdmConfig;
	}


	@GetMapping(value = "/userStatus", produces = { MediaType.APPLICATION_JSON })
	public Map<String, String> userStatus() {
		return staticData.defaultUserStatusList();
	}


	@PostMapping(value = "/status", produces = { MediaType.APPLICATION_JSON })
	public List<Status> refStatusByType(@RequestBody Status status) {
		LOGGER.info("Reference .... {}", status);

		if (!BaseUtil.isObjNull(status.getStatusType())) {
			return staticData.findByStatusCd(status.getStatusCd());
		} else if (!BaseUtil.isObjNull(status.getStatusCd())) {
			return staticData.findByStatusCd(status.getStatusCd());
		} else {
			return staticData.findAllStatus();
		}
	}


	@GetMapping(value = "/refresh")
	public String refreshStaticList(@RequestParam("staticlistType") String staticlistType,
			HttpServletRequest request) {
		String result = null;
		try {
			LOGGER.debug("Refresh static list : {}", staticlistType);
			result = processEvict(staticlistType);
		} catch (Exception e) {
			LOGGER.error("{} : {}", e.getMessage(), messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS));
		}
		LOGGER.debug(result);
		return result;
	}


	@PostMapping(value = "/refresh")
	public String refreshAll(HttpServletRequest request) throws Exception {
		try {
			return processEvict(null);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			return e.getMessage();
		}
	}


	private String processEvict(String prefixKey) throws Exception {
		StringBuilder sb = new StringBuilder();
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
					LOGGER.debug("redisKey: {}", data);
					cache.evict(data);
				}
			} else {
				Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
				cache.clear();
				sb.append(PFX_PORTAL + true);
			}
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
			if (!BaseUtil.isObjNull(prefixKey)) {
				sb.append(PFX_IDM + getIdmService().evict(prefixKey));
			} else {
				sb.append(PFX_IDM + getIdmService().evict());
			}
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


	@GetMapping(value = "/idm/roles", produces = { MediaType.APPLICATION_JSON })
	public List<UserRole> findAllRoles() {
		return getIdmService().findAllRoles();
	}


	// ----------------- CITIES - START -------------------------//
	@GetMapping(value = BffUrlConstants.CITIES, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<City> findAllCities() {
		return staticData.findAllCityList();
	}


	@PostMapping(value = BffUrlConstants.CITIES_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<City> createCities(@RequestBody List<City> cities, HttpServletRequest request) {
		if (BaseUtil.isObjNull(cities)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create Cities : {}", new GsonBuilder().create().toJson(cities));
		return staticData.createCities(cities);
	}


	@PostMapping(value = BffUrlConstants.CITIES_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<City> updateCities(@RequestBody List<City> cities, HttpServletRequest request) {
		if (BaseUtil.isObjNull(cities)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update Cities : {}", new GsonBuilder().create().toJson(cities));
		return staticData.updateCities(cities);
	}


	@PostMapping(value = BffUrlConstants.CITIES_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<City> searchCities(@RequestBody City city, HttpServletRequest request) {
		if (BaseUtil.isObjNull(city)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search Ref Cities : {}", new GsonBuilder().create().toJson(city));
		return getBeService().reference().searchCities(city);
	}
	// ----------------- CITIES - END -------------------------//


	// ----------------- STATES - START -------------------------//
	@GetMapping(value = BffUrlConstants.STATES, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<State> findAllStates() {
		return staticData.findAllStateList();
	}


	@PostMapping(value = BffUrlConstants.STATES_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<State> createStates(@RequestBody List<State> states, HttpServletRequest request) {
		if (BaseUtil.isObjNull(states)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create States : {}", new GsonBuilder().create().toJson(states));
		return staticData.createStates(states);
	}


	@PostMapping(value = BffUrlConstants.STATES_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<State> updateStates(@RequestBody List<State> states, HttpServletRequest request) {
		if (BaseUtil.isObjNull(states)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update States : {}", new GsonBuilder().create().toJson(states));
		return staticData.updateStates(states);
	}


	@PostMapping(value = BffUrlConstants.STATES_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<State> searchStates(@RequestBody State state, HttpServletRequest request) {
		if (BaseUtil.isObjNull(state)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search States : {}", new GsonBuilder().create().toJson(state));
		return getBeService().reference().searchStates(state);
	}
	// ----------------- STATES - END -------------------------//


	// ----------------- COUNTRIES - START -------------------------//
	@GetMapping(value = BffUrlConstants.COUNTRIES, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Country> findAllCountries() {
		return staticData.findAllCountryList();
	}


	@PostMapping(value = BffUrlConstants.COUNTRIES_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Country> createCountries(@RequestBody List<Country> countries,
			HttpServletRequest request) {
		if (BaseUtil.isObjNull(countries)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create Countries : {}", new GsonBuilder().create().toJson(countries));
		return staticData.createCountries(countries);
	}


	@PostMapping(value = BffUrlConstants.COUNTRIES_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Country> updateCountries(@RequestBody List<Country> countries,
			HttpServletRequest request) {
		if (BaseUtil.isObjNull(countries)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update Countries : {}", new GsonBuilder().create().toJson(countries));
		return staticData.updateCountries(countries);
	}


	@PostMapping(value = BffUrlConstants.COUNTRIES_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Country> searchCountries(@RequestBody Country country, HttpServletRequest request) {
		if (BaseUtil.isObjNull(country)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search Countries : {}", new GsonBuilder().create().toJson(country));
		return getBeService().reference().searchCountries(country);
	}


	@GetMapping(value = ReferenceConstants.REF_TYP_COUNTRY + "/serve", consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<Country> findCountriesServe(@RequestParam Map<String, String> requestParams) {
		return getBeService().reference().findAllCountryServe();
	}
	// ----------------- COUNTRIES - END -------------------------//


	// Metadata services
	@GetMapping(value = BffUrlConstants.METADATA, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Metadata> findAllMetadata() {
		return staticData.findAllMetadata();
	}


	@PostMapping(value = BffUrlConstants.METADATA_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Metadata createMetadata(@RequestBody Metadata metadata, HttpServletRequest request) {
		if (BaseUtil.isObjNull(metadata)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create Metadata : {}", new GsonBuilder().create().toJson(metadata));
		return staticData.addMetadata(metadata);
	}


	@PostMapping(value = BffUrlConstants.METADATA_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Metadata updateMetadata(@RequestBody Metadata metadata, HttpServletRequest request) {
		if (BaseUtil.isObjNull(metadata)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update Metadata : {}", new GsonBuilder().create().toJson(metadata));
		return staticData.updateMetadata(metadata);
	}


	@PostMapping(value = BffUrlConstants.METADATA_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Metadata> searchMetadata(@RequestBody Metadata metadata, HttpServletRequest request) {
		if (BaseUtil.isObjNull(metadata)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search Metadata : {}", new GsonBuilder().create().toJson(metadata));
		return getBeService().reference().findMetadataByCriteria(metadata);
	}


	@GetMapping(value = BffUrlConstants.METADATA + "/findByMtdtCd", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Metadata> searchByMetadataCd(@RequestParam(value = "mtdtCd") String mtdtCd,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(mtdtCd)) {
			throw new BeException("Invalid portal type - Metadata Code is empty.");
		}
		return staticData.findByMetadataCd(mtdtCd);
	}


	@GetMapping(value = BffUrlConstants.METADATA + "/findByMtdtType", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Metadata> searchByMetadataType(@RequestParam(value = "mtdtType") String mtdtType,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(mtdtType)) {
			throw new BeException("Invalid portal type - Metadata Type is empty.");
		}
		return staticData.findByMetadataType(mtdtType);
	}


	@GetMapping(value = BffUrlConstants.METADATA + "/findByMtdtDesc", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Metadata> searchByMetadataDesc(@RequestParam(value = "mtdtDesc") String mtdtDesc,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(mtdtDesc)) {
			throw new BeException("Invalid portal type - Metadata Desc is empty.");
		}
		return staticData.findByMetadataDesc(mtdtDesc);
	}


	// Status services
	@GetMapping(value = BffUrlConstants.STATUS, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Status> findAllStatus() {
		return staticData.findAllStatus();
	}


	@PostMapping(value = BffUrlConstants.STATUS_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Status createStatus(@RequestBody Status status, HttpServletRequest request) {
		if (BaseUtil.isObjNull(status)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create Status : {}", new GsonBuilder().create().toJson(status));
		return staticData.addStatus(status);
	}


	@PostMapping(value = BffUrlConstants.STATUS_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Status updateStatus(@RequestBody Status status, HttpServletRequest request) {
		if (BaseUtil.isObjNull(status)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update Status : {}", new GsonBuilder().create().toJson(status));
		return staticData.updateStatus(status);
	}


	@PostMapping(value = BffUrlConstants.STATUS_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Status> searchStatus(@RequestBody Status status, HttpServletRequest request) {
		if (BaseUtil.isObjNull(status)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search Status : {}", new GsonBuilder().create().toJson(status));
		return getBeService().reference().findStatusByCriteria(status);
	}


	@GetMapping(value = BffUrlConstants.STATUS + "/findByStatusCd", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Status> searchByStatusCd(@RequestParam(value = "statusCd") String statusCd,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(statusCd)) {
			throw new BeException("Invalid portal type - Status Code is empty.");
		}
		return staticData.findByStatusCd(statusCd);
	}


	@GetMapping(value = BffUrlConstants.STATUS + "/findByStatusType", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Status> searchByStatusType(@RequestParam(value = "statusType") String statusType,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(statusType)) {
			throw new BeException("Invalid portal type - Status Type is empty.");
		}
		return staticData.findByStatusType(statusType);
	}


	@GetMapping(value = BffUrlConstants.STATUS + "/findByStatusDesc", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Status> searchByStatusDesc(@RequestParam(value = "statusDesc") String statusDesc,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(statusDesc)) {
			throw new BeException("Invalid portal type - Status Desc is empty.");
		}
		return staticData.findByStatusDesc(statusDesc);
	}


	// Reason services
	@GetMapping(value = BffUrlConstants.REASON, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Reason> findAllReason() {
		return staticData.findAllReason();
	}


	@PostMapping(value = BffUrlConstants.REASON_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Reason createReason(@RequestBody Reason reason, HttpServletRequest request) {
		if (BaseUtil.isObjNull(reason)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Create Reason : {}", new GsonBuilder().create().toJson(reason));
		return staticData.addReason(reason);
	}


	@PostMapping(value = BffUrlConstants.REASON_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Reason updateReason(@RequestBody Reason reason, HttpServletRequest request) {
		if (BaseUtil.isObjNull(reason)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Update Reason : {}", new GsonBuilder().create().toJson(reason));
		return staticData.updateReason(reason);
	}


	@PostMapping(value = BffUrlConstants.REASON_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<Reason> searchReason(@RequestBody Reason reason, HttpServletRequest request) {
		if (BaseUtil.isObjNull(reason)) {
			throw new BeException(INVALID_CONFIG_BODY_EMPTY);
		}
		LOGGER.debug("Search Reason : {}", new GsonBuilder().create().toJson(reason));
		return getBeService().reference().findReasonByCriteria(reason);
	}


	@GetMapping(value = BffUrlConstants.REASON + "/findByReasonCd", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Reason> searchByReasonCd(@RequestParam(value = "reasonCd") String reasonCd,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(reasonCd)) {
			throw new BeException("Invalid portal type - Reason Code is empty.");
		}
		return staticData.findByReasonCd(reasonCd);
	}


	@GetMapping(value = BffUrlConstants.REASON + "/findByReasonType", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Reason> searchByReasonType(@RequestParam(value = "reasonType") String reasonType,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(reasonType)) {
			throw new BeException("Invalid portal type - Reason Type is empty.");
		}
		return staticData.findByReasonType(reasonType);
	}


	@GetMapping(value = BffUrlConstants.REASON + "/findByReasonDesc", consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Reason> searchByReasonDesc(@RequestParam(value = "reasonDesc") String reasonDesc,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(reasonDesc)) {
			throw new BeException("Invalid portal type - Reason Desc is empty.");
		}
		return staticData.findByReasonDesc(reasonDesc);
	}

	@GetMapping(value = BffUrlConstants.PORTAL_TYPE, consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<PortalType> findAllPortalType (HttpServletRequest request) {
		return staticData.findAllPortalType();
	}
	
	@PostMapping(value = BffUrlConstants.PORTAL_TYPE_UPDATE, consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<PortalType> updatePortalType (@RequestBody List<PortalType> portalTypes, HttpServletRequest request) {
		return staticData.updatePortalType(portalTypes);
	}

	@PostMapping(value = BffUrlConstants.PORTAL_TYPE_DELETE, consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deletePortalType (@RequestBody PortalType portalType, HttpServletRequest request) {
		return staticData.deletePortalType(portalType);
	}

}