/**
 * Copyright 2019.
 */
package com.portal.web.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.be.sdk.exception.BeException;
import com.be.sdk.model.City;
import com.be.sdk.model.Config;
import com.be.sdk.model.Country;
import com.be.sdk.model.Metadata;
import com.be.sdk.model.Reason;
import com.be.sdk.model.State;
import com.be.sdk.model.Status;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AuditAction;
import com.idm.sdk.model.IdmConfigDto;
import com.idm.sdk.model.PortalType;
import com.portal.config.audit.AuditActionPolicy;
import com.portal.constants.AppConstants;
import com.portal.constants.CacheConstants;
import com.portal.constants.ServiceConstants;
import com.portal.core.AbstractService;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.model.RefDocuments;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Component
public class StaticData extends AbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticData.class);

	@Autowired
	CacheManager cacheManager;


	@SuppressWarnings("unchecked")
	private Map<String, String> refMap(String entityType) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(entityType);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);

		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			return (Map<String, String>) cv.get();
		}

		Map<String, String> objMap;
		try {
			switch (entityType) {
			case ServiceConstants.STAT_LST_CONFIG:
				objMap = getBeService().reference().findAllBeConfig();
				break;
			case ServiceConstants.STAT_IDM_CONFIG:
				objMap = getIdmService().findAllConfig();
				break;
			default:
				objMap = new HashMap<>();
				break;
			}
			if (!BaseUtil.isObjNull(objMap)) {
				cache.put(cacheKey, objMap);
			}
			return objMap;
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (BeException e) {
			LOGGER.error(AppConstants.LOG_BE_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}
		return null;
	}


	@SuppressWarnings("rawtypes")
	private Object refList(String entityType) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(entityType);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);

		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			return cv.get();
		}
		List objLst = null;
		try {
			switch (entityType) {
			case ServiceConstants.STAT_LST_STATUS:
				objLst = getBeService().reference().getAllStatus();
				break;
			case ServiceConstants.STAT_LST_STATE:
				objLst = getBeService().reference().findAllStates();
				break;
			case ServiceConstants.STAT_LST_COUNTRY:
				objLst = getBeService().reference().findAllCountry();
				break;
			case ServiceConstants.STAT_LST_CITY:
				objLst = getBeService().reference().findAllCities();
				break;

			case ServiceConstants.STAT_LST_DOCUMENT:
				objLst = getBeService().reference().getAllDocument();
				break;

			case ServiceConstants.STAT_LST_METADATA:
				objLst = getBeService().reference().getAllMetadata();
				break;

			case ServiceConstants.STAT_LST_REASON:
				objLst = getBeService().reference().getAllReason();
				break;

			case ServiceConstants.STAT_LST_PORTALTYPE:
				objLst = getIdmService().retrieveAllIdmPortalType();
				break;

			default:
				objLst = null;
				break;
			}

			if (!BaseUtil.isListNullZero(objLst)) {
				cache.put(cacheKey, objLst);
			}
			return objLst;

		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (BeException e) {
			LOGGER.error(AppConstants.LOG_BE_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}
		return null;
	}


	public Map<String, String> defaultMaritalStatusList() {
		Map<String, String> map = new HashMap<>();
		map.put("M", "Married");
		map.put("S", "Single");
		map.put("D", "Divorced");
		return map;
	}


	public Map<String, String> defaultReligionStatusList() {
		Map<String, String> map = new HashMap<>();
		map.put("M", "Islam");
		map.put("C", "Cristianity");
		map.put("B", "Bhuddism");
		map.put("NM", "Other Religion");
		return map;
	}


	public Map<String, String> defaultNYUList() {
		Map<String, String> map = new HashMap<>();
		map.put("N", "NO");
		map.put("Y", "YES");
		map.put("U", "UNKNOWN");
		return map;
	}


	public Map<String, String> beConfig() {
		return (refMap(ServiceConstants.STAT_LST_CONFIG));
	}


	public String beConfig(String key) {
		if (!BaseUtil.isObjNull(key)) {
			Map<String, String> conf = beConfig();
			return conf.get(key);
		}
		return BaseConstants.EMPTY_STRING;
	}


	public Map<String, String> idmConfig() {
		return refMap(ServiceConstants.STAT_IDM_CONFIG);
	}


	public String idmConfig(String key) {
		if (!BaseUtil.isObjNull(key)) {
			Map<String, String> conf = beConfig();
			return conf.get(key);
		}
		return BaseConstants.EMPTY_STRING;
	}
	
	public List<Config> beConfigByPrefix(String prefix){
		List<Config> configLst = getBeService().reference().findBeConfig();
		return configLst.stream().filter(x -> x.getConfigCode().startsWith(prefix)).collect(Collectors.toList());
	}


	public void addIdmConfig(List<IdmConfigDto> idmConfigDto) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_IDM_CONFIG);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		Map<String, String> idmConfig = getIdmService().createIdmConfig(idmConfigDto);
		if (!BaseUtil.isObjNull(idmConfig)) {
			cache.put(cacheKey, idmConfig);
		}
	}


	public void addIdmConfigByPortal(String portalType, List<IdmConfigDto> idmConfigDto) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_IDM_CONFIG_PORTAL + portalType);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<IdmConfigDto> idmConfig = getIdmService().createIdmConfigByPortal(portalType, idmConfigDto);
		if (!BaseUtil.isObjNull(idmConfig)) {
			cache.put(cacheKey, idmConfig);
		}
	}


	@SuppressWarnings("unchecked")
	public List<City> findAllCityList() {
		return ((List<City>) refList(ServiceConstants.STAT_LST_CITY));
	}


	public List<City> createCities(List<City> cities) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_CITY);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<City> returnCities = getBeService().reference().createCities(cities);
		@SuppressWarnings("unchecked")
		List<City> cacheCities = cache.get(cacheKey) != null
				? ListUtils.union((List<City>) cache.get(cacheKey), returnCities)
				: returnCities;

		if (!BaseUtil.isObjNull(cacheCities)) {
			cache.put(cacheKey, cacheCities);
		}

		return returnCities;
	}


	public List<City> updateCities(List<City> cities) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_CITY);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<City> returnCities = getBeService().reference().updateCities(cities);
		@SuppressWarnings("unchecked")
		List<City> cacheCities = cache.get(cacheKey) != null
				? ListUtils.union((List<City>) cache.get(cacheKey), returnCities)
				: returnCities;

		if (!BaseUtil.isObjNull(cacheCities)) {
			cache.put(cacheKey, cacheCities);
		}

		return returnCities;
	}


	@SuppressWarnings("unchecked")
	public List<State> findAllStateList() {
		List<State> lst = ((List<State>) refList(ServiceConstants.STAT_LST_STATE));
		List<State> sortedState = lst.stream().sorted(Comparator.comparing(State::getStateDesc))
				.collect(Collectors.toList());
		return sortedState;
	}


	public List<State> createStates(List<State> states) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_STATE);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<State> returnStates = getBeService().reference().createStates(states);
		@SuppressWarnings("unchecked")
		List<State> cacheStates = cache.get(cacheKey) != null
				? ListUtils.union((List<State>) cache.get(cacheKey), returnStates)
				: returnStates;

		if (!BaseUtil.isObjNull(cacheStates)) {
			cache.put(cacheKey, cacheStates);
		}

		return returnStates;
	}


	public List<State> updateStates(List<State> states) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_STATE);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<State> returnStates = getBeService().reference().updateStates(states);
		@SuppressWarnings("unchecked")
		List<State> cacheStates = cache.get(cacheKey) != null
				? ListUtils.union((List<State>) cache.get(cacheKey), returnStates)
				: returnStates;

		if (!BaseUtil.isObjNull(cacheStates)) {
			cache.put(cacheKey, cacheStates);
		}

		return returnStates;
	}


	@SuppressWarnings("unchecked")
	public List<Country> findAllCountryList() {
		return ((List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY));
	}


	public List<Country> createCountries(List<Country> countries) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_COUNTRY);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<Country> returnCountries = getBeService().reference().createCountries(countries);
		@SuppressWarnings("unchecked")
		List<Country> cacheCountries = cache.get(cacheKey) != null
				? ListUtils.union((List<Country>) cache.get(cacheKey), returnCountries)
				: returnCountries;

		if (!BaseUtil.isObjNull(cacheCountries)) {
			cache.put(cacheKey, cacheCountries);
		}

		return returnCountries;
	}


	public List<Country> updateCountries(List<Country> countries) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_COUNTRY);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<Country> returnCountries = getBeService().reference().updateCountries(countries);
		@SuppressWarnings("unchecked")
		List<Country> cacheCountries = cache.get(cacheKey) != null
				? ListUtils.union((List<Country>) cache.get(cacheKey), returnCountries)
				: returnCountries;

		if (!BaseUtil.isObjNull(cacheCountries)) {
			cache.put(cacheKey, cacheCountries);
		}

		return returnCountries;
	}


	@SuppressWarnings("unchecked")
	public List<IdmConfigDto> configByPortalType(String portalType) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_IDM_CONFIG_PORTAL + portalType);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);

		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			return (List<IdmConfigDto>) cv.get();
		}

		return getIdmService().findAllByPortalType(portalType);
	}


	public Map<String, String> defaultStatusList() {
		Map<String, String> map = new HashMap<>();
		map.put("A", "ACTIVE");
		map.put("I", "INACTIVE");
		return map;
	}


	public Map<String, String> defaultUserStatusList() {
		Map<String, String> map = new HashMap<>();
		map.put("A", "ACTIVE");
		map.put("I", "DEACTIVATED");
		map.put("F", "PENDING ACTIVATION");
		return map;
	}


	public void addUserAction(AuditActionPolicy auditPolicy, String userId, String reqData) {
		try {
			AuditAction audit = new AuditAction();
			audit.setPortal(messageService.getMessage("app.portal.type"));
			audit.setPage(auditPolicy.page());
			audit.setAuditInfo(auditPolicy.action());
			audit.setUserId(userId);
			audit.setLookupData(reqData);
			getIdmService().createAuditAction(audit);
		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}
	}


	@SuppressWarnings("unchecked")
	public List<Status> statusList() {
		return (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
	}


	@SuppressWarnings("unchecked")
	public List<Status> statusList(String statusType) {
		List<Status> statusList = (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
		List<Status> statLst = new ArrayList<>();
		if (statusType != null && !BaseUtil.isListNullZero(statusList)) {
			for (Status stat : statusList) {
				if (BaseUtil.isEqualsCaseIgnore(statusType, stat.getStatusType())) {
					statLst.add(stat);
				}
			}
		}
		return statLst;
	}


	public String statusDescByStatusType(String code, String statusType) {
		List<Status> lst = findByStatusType(statusType);
		for (Status obj : lst) {
			if (BaseUtil.isEqualsCaseIgnoreAny(statusType, obj.getStatusType())
					&& BaseUtil.isEqualsCaseIgnore(code, obj.getStatusCd())) {
				return obj.getStatusDesc();
			}
		}
		return null;
	}
	
	public Status statusByStatusType(String code, String statusType) {
		List<Status> lst = findByStatusType(statusType);
		return lst.stream().filter(t -> BaseUtil.isEqualsCaseIgnoreAny(statusType, t.getStatusType())
					&& BaseUtil.isEqualsCaseIgnore(code, t.getStatusCd())).findFirst().orElse(new Status());
	}


	// RefDocuments
	@SuppressWarnings("unchecked")
	public List<RefDocuments> findAllRefDocumentss() {
		return (List<RefDocuments>) refList(ServiceConstants.STAT_LST_DOCUMENT);
	}


	@SuppressWarnings("unchecked")
	public List<RefDocuments> findByRefDocumentsBucket(String dmBucket) {
		List<RefDocuments> resLst = new ArrayList<>();
		List<RefDocuments> documentLst = (List<RefDocuments>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(dmBucket)) {
			resLst = documentLst.stream().filter(res -> res.getDmBucket().contains(dmBucket))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<RefDocuments> findByRefDocumentsDesc(String docDesc) {
		List<RefDocuments> resLst = new ArrayList<>();
		List<RefDocuments> documentLst = (List<RefDocuments>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(docDesc)) {
			resLst = documentLst.stream().filter(res -> res.getDocDesc().contains(docDesc))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<RefDocuments> findByRefDocumentsType(String docType) {
		List<RefDocuments> resLst = new ArrayList<>();
		List<RefDocuments> documentLst = (List<RefDocuments>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(docType)) {
			resLst = documentLst.stream().filter(res -> res.getDocTrxnNo().contains(docType))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<RefDocuments> findRefDocumentssByTrxnNo(String trxnNo) {
		List<RefDocuments> newLst = new ArrayList<>();
		List<RefDocuments> lst = (List<RefDocuments>) refList(ServiceConstants.STAT_LST_DOCUMENT);
		if (!BaseUtil.isObjNull(lst)) {
			newLst = lst.stream().filter(res -> res.getDocTrxnNo().contains(trxnNo)).collect(Collectors.toList());
		}
		return newLst;
	}


	// Status
	@SuppressWarnings("unchecked")
	public List<Status> findAllStatus() {
		return (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
	}


	public Status addStatus(Status status) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_STATUS);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Status resStatus = getBeService().reference().createStatus(status);
		if (!BaseUtil.isObjNull(resStatus)) {
			cache.put(cacheKey, resStatus);
		}

		return resStatus;
	}


	public Status updateStatus(Status status) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_STATUS);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Status resStatus = getBeService().reference().updateStatus(status);
		if (!BaseUtil.isObjNull(resStatus)) {
			cache.put(cacheKey, resStatus);
		}

		return resStatus;
	}


	@SuppressWarnings("unchecked")
	public List<Status> findByStatusCd(String statusCd) {
		List<Status> resLst = new ArrayList<>();
		List<Status> lst = (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
		if (!BaseUtil.isObjNull(statusCd)) {
			resLst = lst.stream().filter(res -> res.getStatusCd().contains(statusCd)).collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<Status> findByStatusType(String statusType) {
		List<Status> resLst = new ArrayList<>();
		List<Status> statusLst = (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
		if (!BaseUtil.isObjNull(statusType)) {
			resLst = statusLst.stream().filter(res -> res.getStatusType().equals(statusType))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<Status> findByStatusDesc(String statusDesc) {
		List<Status> resLst = new ArrayList<>();
		List<Status> statusLst = (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);

		if (!BaseUtil.isObjNull(statusDesc)) {
			resLst = statusLst.stream().filter(res -> res.getStatusDesc().contains(statusDesc))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	// Reason
	@SuppressWarnings("unchecked")
	public List<Reason> findAllReason() {
		return (List<Reason>) refList(ServiceConstants.STAT_LST_REASON);
	}


	public Reason addReason(Reason reason) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_REASON);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Reason resReason = getBeService().reference().createReason(reason);
		if (!BaseUtil.isObjNull(resReason)) {
			cache.put(cacheKey, resReason);
		}

		return resReason;
	}


	public Reason updateReason(Reason reason) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_REASON);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Reason resReason = getBeService().reference().updateReason(reason);
		if (!BaseUtil.isObjNull(resReason)) {
			cache.put(cacheKey, resReason);
		}

		return resReason;
	}


	@SuppressWarnings("unchecked")
	public List<Reason> findByReasonCd(String reasonCd) {
		List<Reason> restLst = new ArrayList<>();
		List<Reason> reasonLst = (List<Reason>) refList(ServiceConstants.STAT_LST_REASON);
		if (!BaseUtil.isObjNull(reasonCd)) {
			restLst = reasonLst.stream().filter(res -> res.getReasonCd().contains(reasonCd))
					.collect(Collectors.toList());
		}
		return restLst;
	}


	@SuppressWarnings("unchecked")
	public List<Reason> findByReasonDesc(String reasonDesc) {
		List<Reason> restLst = new ArrayList<>();
		List<Reason> reasonLst = (List<Reason>) refList(ServiceConstants.STAT_LST_REASON);
		if (!BaseUtil.isObjNull(reasonDesc)) {
			restLst = reasonLst.stream().filter(res -> res.getReasonDesc().contains(reasonDesc))
					.collect(Collectors.toList());
		}
		return restLst;
	}


	@SuppressWarnings("unchecked")
	public List<Reason> findByReasonType(String reasonType) {
		List<Reason> restLst = new ArrayList<>();
		List<Reason> reasonLst = (List<Reason>) refList(ServiceConstants.STAT_LST_REASON);
		if (!BaseUtil.isObjNull(reasonType)) {
			restLst = reasonLst.stream().filter(res -> res.getReasonType().contains(reasonType))
					.collect(Collectors.toList());
		}
		return restLst;
	}


	// Metadata
	@SuppressWarnings("unchecked")
	public List<Metadata> findAllMetadata() {
		return (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
	}


	public Metadata addMetadata(Metadata metadata) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_METADATA);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Metadata resMetadata = getBeService().reference().createMetadata(metadata);
		if (!BaseUtil.isObjNull(resMetadata)) {
			cache.put(cacheKey, resMetadata);
		}

		return resMetadata;
	}


	public Metadata updateMetadata(Metadata metadata) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_METADATA);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Metadata resMetadata = getBeService().reference().updateMetadata(metadata);
		if (!BaseUtil.isObjNull(resMetadata)) {
			cache.put(cacheKey, resMetadata);
		}

		return resMetadata;
	}


	@SuppressWarnings("unchecked")
	public List<Metadata> findByMetadataCd(String mtdtCd) {
		List<Metadata> restLst = new ArrayList<>();
		List<Metadata> statLst = (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
		if (!BaseUtil.isObjNull(mtdtCd)) {
			restLst = statLst.stream()
					.sorted(Comparator.comparing(Metadata::getMtdtDesc))
					.filter(mtdt -> mtdt.getMtdtCd().contains(mtdtCd))
					.collect(Collectors.toList());
		}

		return restLst;
	}


	@SuppressWarnings("unchecked")
	public List<Metadata> findByMetadataType(String mtdtType) {
		List<Metadata> restLst = new ArrayList<>();
		List<Metadata> statLst = (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
		if (!BaseUtil.isObjNull(mtdtType)) {
			restLst = statLst.stream()
					.sorted(Comparator.comparing(Metadata::getMtdtDesc))
					.filter(mtdt -> mtdt.getMtdtType().contains(mtdtType))
					.collect(Collectors.toList());
		}

		return restLst;
	}
	
	@SuppressWarnings("unchecked")
	public List<Metadata> findByMetadataTypeOrderByCode(String mtdtType) {
		List<Metadata> restLst = (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
		if (!BaseUtil.isObjNull(mtdtType)) {
			restLst = restLst.stream()
					.sorted(Comparator.comparing(Metadata::getMtdtCd))
					.filter(mtdt -> mtdt.getMtdtType().contains(mtdtType))
					.collect(Collectors.toList());
		}
		return restLst;
	}


	public Metadata findByMetadataId(Integer mtdtId) {
		List<Metadata> restLst = findAllMetadata();
		Metadata mtdtObj = new Metadata();
		if (!BaseUtil.isObjNull(restLst)) {
			mtdtObj = restLst.stream().filter(mtdt -> mtdt.getMtdtId().equals(mtdtId)).findFirst()
					.orElse(new Metadata());
		}
		return mtdtObj;
	}


	public Metadata findByMetadataType(String mtdtType, String mtdtCd) {
		List<Metadata> restLst = findByMetadataType(mtdtType);
		Metadata mtdtObj = new Metadata();
		if (!BaseUtil.isObjNull(restLst)) {
			mtdtObj = restLst.stream().filter(mtdt -> mtdt.getMtdtCd().equals(mtdtCd)).findFirst()
					.orElse(new Metadata());
		}
		return mtdtObj;
	}


	@SuppressWarnings("unchecked")
	public List<Metadata> findByMetadataDesc(String mtdtDesc) {
		List<Metadata> restLst = new ArrayList<>();
		List<Metadata> statLst = (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
		if (!BaseUtil.isObjNull(mtdtDesc)) {
			restLst = statLst.stream().filter(mtdt -> mtdt.getMtdtDesc().contains(mtdtDesc))
					.collect(Collectors.toList());
		}

		return restLst;
	}


	public Metadata findByMetadataTypeDesc(String mtdtType, String mtdtDesc) {
		List<Metadata> restLst = findByMetadataType(mtdtType);
		Metadata mtdtObj = new Metadata();
		if (!BaseUtil.isObjNull(restLst)) {
			mtdtObj = restLst.stream().filter(mtdt -> BaseUtil.isEqualsCaseIgnore(mtdt.getMtdtDesc(), mtdtDesc))
					.findFirst().orElse(new Metadata());
		}
		return mtdtObj;
	}


	@SuppressWarnings("unchecked")
	public List<PortalType> findAllPortalType() {
		return ((List<PortalType>) refList(ServiceConstants.STAT_LST_PORTALTYPE));
	}


	public List<PortalType> updatePortalType(List<PortalType> portalTypes) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_PORTALTYPE);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<PortalType> returnPortalTypes = getIdmService().updateIdmPortalType(portalTypes);
		@SuppressWarnings("unchecked")
		List<PortalType> cachePortalTypes = cache.get(cacheKey) != null
				? ListUtils.union((List<PortalType>) cache.get(cacheKey), returnPortalTypes)
				: returnPortalTypes;

		if (!BaseUtil.isObjNull(cachePortalTypes)) {
			cache.put(cacheKey, cachePortalTypes);
		}

		return returnPortalTypes;
	}


	public Boolean deletePortalType(PortalType portalType) {
		// delete from DB
		Boolean isDeleteSuccess = getIdmService().deleteIdmPortalType(portalType);

		if (Boolean.TRUE.equals(isDeleteSuccess)) {
			// if delete success reset cache
			String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_PORTALTYPE);
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			cache.evict(cacheKey);
			List<PortalType> portalTypes = getIdmService().retrieveAllIdmPortalType();
			cache.put(cacheKey, portalTypes);
		}
		return isDeleteSuccess;
	}


	@SuppressWarnings("unchecked")
	public List<State> findAllState() {
		return (List<State>) refList(ServiceConstants.STAT_LST_STATE);
	}


	@SuppressWarnings("unchecked")
	public State findByStateCd(String code) {

		List<State> lst = (List<State>) refList(ServiceConstants.STAT_LST_STATE);

		for (State obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(code, obj.getStateCd())) {
				return obj;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<City> cityList() {
		List<City> lst = (List<City>) refList(ServiceConstants.STAT_LST_CITY);
		List<City> sortedLst = lst.stream().sorted(Comparator.comparing(City::getCityDesc))
				.collect(Collectors.toList());
		return sortedLst;
	}


	public City city(String cityCode) {
		List<City> cityLst = cityList();
		for (City city : cityLst) {
			if (BaseUtil.isEqualsCaseIgnore(cityCode, city.getCityCd())) {
				return city;
			}
		}
		return null;
	}


	public String cityDesc(String cityCode) {
		City city = city(cityCode);
		if (!BaseUtil.isObjNull(city)) {
			return city.getCityDesc();
		}
		return "";
	}


	@SuppressWarnings("unchecked")
	public List<City> cityList(String state) {
		List<City> lst = cityList();
		List<City> newLst = new ArrayList<>();

		for (City obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(state, obj.getState().getStateCd())) {
				newLst.add(obj);
			}
		}

		Collections.sort(newLst, new Comparator<City>() {

			@Override
			public int compare(City o1, City o2) {
				return o1.getState().getStateCd().compareTo(o2.getState().getStateDesc());
			}
		});

		return newLst;
	}


	@SuppressWarnings("unchecked")
	public List<Country> countryList() {
		List<Country> lst = (List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY);
		List<Country> sortedUsers = lst.stream().sorted(Comparator.comparing(Country::getCntryDesc))
				.collect(Collectors.toList());
		return sortedUsers;
	}


	@SuppressWarnings("unchecked")
	public List<Country> countrySourceList() {
		List<Country> lst = countryList();
		List<Country> newLst = new ArrayList<>();
		for (Country obj : lst) {
			if (obj.getCntryInd() == true) {
				newLst.add(obj);
			}
		}
		return newLst;
	}


	@SuppressWarnings("unchecked")
	public Country country(String code) {
		List<Country> lst = (List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY);
		for (Country obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(code, obj.getCntryCd())) {
				return obj;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public String countryDesc(String code) {
		List<Country> lst = (List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY);
		for (Country obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(code, obj.getCntryCd())) {
				return obj.getCntryDesc();
			}
		}
		return "";
	}


}