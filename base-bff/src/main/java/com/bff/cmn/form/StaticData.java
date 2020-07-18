/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.form;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.be.sdk.exception.BeException;
import com.be.sdk.model.City;
import com.be.sdk.model.Country;
import com.be.sdk.model.Document;
import com.be.sdk.model.Metadata;
import com.be.sdk.model.Reason;
import com.be.sdk.model.State;
import com.be.sdk.model.Status;
import com.bff.cmn.constants.ServiceConstants;
import com.bff.config.audit.AuditActionPolicy;
import com.bff.core.AbstractService;
import com.bff.util.WebUtil;
import com.bff.util.constants.AppConstants;
import com.bff.util.constants.CacheConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AuditAction;
import com.idm.sdk.model.IdmConfigDto;
import com.idm.sdk.model.PortalType;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


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
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (BeException e) {
			LOGGER.error(AppConstants.LOG_BE_EXCEPTION, e.getMessage());
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
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
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (BeException e) {
			LOGGER.error(AppConstants.LOG_BE_EXCEPTION, e.getMessage());
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
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
		List<City> cacheCities = cache.get(cacheKey) != null ? ListUtils.union((List<City>) cache.get(cacheKey), returnCities)
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
		List<City> cacheCities = cache.get(cacheKey) != null ? ListUtils.union((List<City>) cache.get(cacheKey), returnCities)
				: returnCities;

		if (!BaseUtil.isObjNull(cacheCities)) {
			cache.put(cacheKey, cacheCities);
		}

		return returnCities;
	}


	@SuppressWarnings("unchecked")
	public List<State> findAllStateList() {
		return ((List<State>) refList(ServiceConstants.STAT_LST_STATE));
	}


	public List<State> createStates(List<State> states) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_STATE);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);

		List<State> returnStates = getBeService().reference().createStates(states);
		@SuppressWarnings("unchecked")
		List<State> cacheStates = cache.get(cacheKey) != null ? ListUtils.union((List<State>) cache.get(cacheKey), returnStates)
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
		List<State> cacheStates = cache.get(cacheKey) != null ? ListUtils.union((List<State>) cache.get(cacheKey), returnStates)
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
		map.put("I", "INACTIVE");
		map.put("F", "PENDING ACTIVATION");
		return map;
	}


	public void addUserAction(AuditActionPolicy auditPolicy, String userId, String reqData,
			HttpServletRequest request) {
		try {
			AuditAction audit = new AuditAction();
			audit.setPortal(messageService.getMessage("app.portal.type"));
			audit.setPage(auditPolicy.page());
			audit.setAuditInfo(auditPolicy.action());
			audit.setUserId(userId);
			audit.setLookupData(reqData);
			getIdmService(request).createAuditAction(audit);
		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}
	}


	@SuppressWarnings("unchecked")
	public List<Status> statusList() {
		return (List<Status>) refList(ServiceConstants.STAT_LST_STATUS);
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


	// Document
	@SuppressWarnings("unchecked")
	public List<Document> findAllDocuments() {
		return (List<Document>) refList(ServiceConstants.STAT_LST_DOCUMENT);
	}


	public Document addDocument(Document documents) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_DOCUMENT);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Document resDocuments = getBeService().reference().createDocuments(documents);
		if (!BaseUtil.isObjNull(resDocuments)) {
			cache.put(cacheKey, resDocuments);
		}

		return resDocuments;
	}


	public Document updateDocument(Document documents) {
		String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_DOCUMENT);
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		cache.evict(cacheKey);
		Document resDocuments = getBeService().reference().updateDocuments(documents);
		if (!BaseUtil.isObjNull(resDocuments)) {
			cache.put(cacheKey, resDocuments);
		}
		return resDocuments;
	}


	@SuppressWarnings("unchecked")
	public List<Document> findByDocumentBucket(String dmBucket) {
		List<Document> resLst = new ArrayList<>();
		List<Document> documentLst = (List<Document>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(dmBucket)) {
			resLst = documentLst.stream().filter(res -> res.getDmBucket().contains(dmBucket))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<Document> findByDocumentDesc(String docDesc) {
		List<Document> resLst = new ArrayList<>();
		List<Document> documentLst = (List<Document>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(docDesc)) {
			resLst = documentLst.stream().filter(res -> res.getDocDesc().contains(docDesc))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<Document> findByDocumentType(String docType) {
		List<Document> resLst = new ArrayList<>();
		List<Document> documentLst = (List<Document>) refList(ServiceConstants.STAT_LST_DOCUMENT);

		if (!BaseUtil.isObjNull(docType)) {
			resLst = documentLst.stream().filter(res -> res.getDocTrxnNo().contains(docType))
					.collect(Collectors.toList());
		}

		return resLst;
	}


	@SuppressWarnings("unchecked")
	public List<Document> findDocumentsByTrxnNo(String trxnNo) {
		List<Document> newLst = new ArrayList<>();
		List<Document> lst = (List<Document>) refList(ServiceConstants.STAT_LST_DOCUMENT);
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
			resLst = statusLst.stream().filter(res -> res.getStatusType().contains(statusType))
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
			restLst = statLst.stream().filter(mtdt -> mtdt.getMtdtCd().contains(mtdtCd))
					.collect(Collectors.toList());
		}

		return restLst;
	}


	@SuppressWarnings("unchecked")
	public List<Metadata> findByMetadataType(String mtdtType) {
		List<Metadata> restLst = new ArrayList<>();
		List<Metadata> statLst = (List<Metadata>) refList(ServiceConstants.STAT_LST_METADATA);
		if (!BaseUtil.isObjNull(mtdtType)) {
			restLst = statLst.stream().filter(mtdt -> mtdt.getMtdtType().contains(mtdtType))
					.collect(Collectors.toList());
		}

		return restLst;
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
		List<PortalType> cachePortalTypes = cache.get(cacheKey) != null ? ListUtils.union((List<PortalType>) cache.get(cacheKey), returnPortalTypes)
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
			// if delete success  reset cache
			String cacheKey = CacheConstants.CACHE_KEY_REF.concat(ServiceConstants.STAT_LST_PORTALTYPE);
			Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
			cache.evict(cacheKey);
			List <PortalType> portalTypes = getIdmService().retrieveAllIdmPortalType();
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
		return (List<City>) refList(ServiceConstants.STAT_LST_CITY);
	}


	@SuppressWarnings("unchecked")
	public City city(String cityCode) {

		List<City> cityLst = (List<City>) refList(ServiceConstants.STAT_LST_CITY);

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

		List<City> lst = (List<City>) refList(ServiceConstants.STAT_LST_CITY);

		List<City> newLst = new ArrayList<>();

		for (City obj : lst) {

			if (BaseUtil.isEqualsCaseIgnore(state, obj.getState().getStateCd())) {
				newLst.add(obj);
			}
		}

		Collections.sort(newLst, new Comparator<City>() {

			@Override
			public int compare(City o1, City o2) {
				return o1.getState().getStateCd().compareTo(o2.getState().getStateCd());
			}
		});

		return newLst;
	}


	@SuppressWarnings("unchecked")
	public List<Country> countryList() {
		return (List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY);
	}


	@SuppressWarnings("unchecked")
	public List<Country> countrySourceList() {
		List<Country> lst = (List<Country>) refList(ServiceConstants.STAT_LST_COUNTRY);
		List<Country> newLst = new ArrayList<>();
		for (Country obj : lst) {
			if (obj.getCntryInd() == true) {
				newLst.add(obj);
			}
		}

		Collections.sort(newLst, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				int b1 = o1.getCntryInd() ? 1 : 0;
		        int b2 = o2.getCntryInd() ? 1 : 0;
				return (b1 - b2);
			}
		});
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