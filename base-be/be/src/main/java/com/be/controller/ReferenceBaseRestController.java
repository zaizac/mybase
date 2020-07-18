package com.be.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.config.cache.RedisCacheWrapper;
import com.be.constants.ConfigConstants;
import com.be.core.AbstractRestController;
import com.be.model.BeConfig;
import com.be.model.RefCity;
import com.be.model.RefCountry;
import com.be.model.RefDocument;
import com.be.model.RefMetadata;
import com.be.model.RefReason;
import com.be.model.RefState;
import com.be.model.RefStatus;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.constants.BeUrlConstants;
import com.be.sdk.constants.ReferenceConstants;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.City;
import com.be.sdk.model.Country;
import com.be.sdk.model.Document;
import com.be.sdk.model.Metadata;
import com.be.sdk.model.Reason;
import com.be.sdk.model.State;
import com.be.sdk.model.Status;
import com.be.service.BeConfigService;
import com.be.service.RefCityService;
import com.be.service.RefCountryService;
import com.be.service.RefDocumentService;
import com.be.service.RefMetadataService;
import com.be.service.RefReasonService;
import com.be.service.RefStateService;
import com.be.service.RefStatusService;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * The rest controller class for base static reference
 *
 * @author ilhomjon
 * @since Oct 24, 2019
 */
@Lazy
@RestController
@RequestMapping(BeUrlConstants.REFERENCE)
public class ReferenceBaseRestController extends AbstractRestController {

	@Autowired
	private BeConfigService configSvc;

	@Autowired
	private RefCityService refCitySvc;

	@Autowired
	private RefCountryService refCountrySvc;

	@Autowired
	private RefDocumentService refDocSvc;

	@Autowired
	private RefStateService refStateSvc;

	@Autowired
	private RefStatusService refStatusSvc;

	@Autowired
	private RefMetadataService refMetadataSvc;

	@Autowired
	private RefReasonService refReasonSvc;


	/**
	 * Fetch All data from BE_CONFIG
	 *
	 * @param request
	 * @return Map
	 */
	@GetMapping(value = BeUrlConstants.CONFIG, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> beConfig(HttpServletRequest request) {
		List<BeConfig> confLst = configSvc.primaryDao().findAll();
		Map<String, String> config = new HashMap<>();
		if (!confLst.isEmpty()) {
			for (BeConfig conf : confLst) {
				config.put(conf.getConfigCode(), conf.getConfigVal());
			}
		}
		return config;
	}


	/**
	 * Find code from BE_CONFIG and return BeConfig object
	 *
	 * @param configCode
	 * @param request
	 * @return BeConfig
	 */
	@GetMapping(value = BeUrlConstants.CONFIG + "/{configCode}", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public BeConfig findConfig(@PathVariable String configCode, HttpServletRequest request) {
		return configSvc.findByConfigCode(configCode);
	}


	// ------------- CITY START -----------------------------//
	@SuppressWarnings("unchecked")
	@GetMapping(value = ReferenceConstants.REF_TYP_CITY, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<City> findCities() throws IOException{
		ArrayList<City> cities = new ArrayList<>();
		cities.addAll(JsonUtil.transferToList(refCitySvc.findAll(), City.class));
		return cities;
	}


	@PostMapping(value = ReferenceConstants.CREATE_CITY, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<City> createCity(@RequestBody List<City> cities, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(cities)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<City> returnCities = new ArrayList<>();
		for (City city : cities) {
			RefCity refCity = JsonUtil.transferToObject(city, RefCity.class);
			refCity.setCreateId((BaseUtil.isStringNull(refCity.getCreateId()) ? userId : refCity.getCreateId()));
			refCity.setUpdateId((BaseUtil.isStringNull(refCity.getUpdateId()) ? userId : refCity.getUpdateId()));
			if (city.getState() != null && city.getState().getStateId() != null) {
				refCity.setState(dozerMapper.map(city.getState(), RefState.class));
			} else if (city.getStateId() != null) {
				RefState tempRefState = new RefState();
				tempRefState.setStateId(city.getStateId());
				refCity.setState(tempRefState);
			}
			returnCities.add(JsonUtil.transferToObject(refCitySvc.create(refCity), City.class));
		}
		return returnCities;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_CITY, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<City> updateCity(@RequestBody List<City> cities, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(cities)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<City> returnCities = new ArrayList<>();
		for (City city : cities) {
			RefCity refCity = JsonUtil.transferToObject(city, RefCity.class);
			refCity.setCreateId((BaseUtil.isStringNull(refCity.getCreateId()) ? userId : refCity.getCreateId()));
			refCity.setUpdateId((BaseUtil.isStringNull(refCity.getUpdateId()) ? userId : refCity.getUpdateId()));
			if (city.getState() != null && city.getState().getStateId() != null) {
				refCity.setState(dozerMapper.map(city.getState(), RefState.class));
			} else if (city.getStateId() != null) {
				RefState tempRefState = new RefState();
				tempRefState.setStateId(city.getStateId());
				refCity.setState(tempRefState);
			}
			returnCities.add(JsonUtil.transferToObject(refCitySvc.update(refCity), City.class));
		}
		return returnCities;
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = ReferenceConstants.SEARCH_CITY, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<City> searchCity(@RequestBody City city, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(city)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		RefCity refCity = JsonUtil.transferToObject(city, RefCity.class);
		if (city.getState() != null && city.getState().getStateId() != null) {
			refCity.setState(JsonUtil.transferToObject(city.getState(), RefState.class));
		} else if (city.getStateId() != null) {
			RefState tempRefState = new RefState();
			tempRefState.setStateId(city.getStateId());
			refCity.setState(tempRefState);
		}

		return JsonUtil.transferToList(refCitySvc.searchByProperty(refCity), City.class);
	}
	// ------------- CITY END -----------------------------//


	// ------------- COUNTRY START -----------------------------//
	@SuppressWarnings("unchecked")
	@GetMapping(value = ReferenceConstants.REF_TYP_COUNTRY, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Country> findCountries() throws IOException{
		ArrayList<Country> countries = new ArrayList<>();
		countries.addAll(JsonUtil.transferToList(refCountrySvc.findAll(), Country.class));
		return countries;
	}


	@PostMapping(value = ReferenceConstants.CREATE_COUNTRY, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Country> createCountry(@RequestBody List<Country> countries, HttpServletRequest request) throws IOException{
		if (BaseUtil.isObjNull(countries)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<Country> returnCountries = new ArrayList<>();
		for (Country country : countries) {
			RefCountry refCountry = JsonUtil.transferToObject(country, RefCountry.class);
			refCountry.setCreateId(
					(BaseUtil.isStringNull(refCountry.getCreateId()) ? userId : refCountry.getCreateId()));
			refCountry.setUpdateId(
					(BaseUtil.isStringNull(refCountry.getUpdateId()) ? userId : refCountry.getUpdateId()));
			returnCountries.add(JsonUtil.transferToObject(refCountrySvc.create(refCountry), Country.class));
		}
		return returnCountries;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_COUNTRY, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Country> updateCountry(@RequestBody List<Country> countries, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(countries)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<Country> returnCountries = new ArrayList<>();
		for (Country country : countries) {
			RefCountry refCountry = JsonUtil.transferToObject(country, RefCountry.class);
			refCountry.setCreateId(
					(BaseUtil.isStringNull(refCountry.getCreateId()) ? userId : refCountry.getCreateId()));
			refCountry.setUpdateId(
					(BaseUtil.isStringNull(refCountry.getUpdateId()) ? userId : refCountry.getUpdateId()));
			returnCountries.add(JsonUtil.transferToObject(refCountrySvc.update(refCountry),Country.class));
		}
		return returnCountries;
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = ReferenceConstants.SEARCH_COUNTRY, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Country> searchCountry(@RequestBody Country country, HttpServletRequest request) throws IOException{
		if (BaseUtil.isObjNull(country)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		RefCountry refCountry = JsonUtil.transferToObject(country, RefCountry.class);
		return JsonUtil.transferToList(refCountrySvc.searchAllByProperty(refCountry),Country.class);
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = ReferenceConstants.REF_TYP_COUNTRY + "/serve", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Country> findCountriesServe() throws IOException {
		return JsonUtil.transferToList(refCountrySvc.findByCountryInd(true), Country.class);
	}
	// ------------- COUNTRY - END -----------------------------//


	// ------------- STATE - START -----------------------------//
	@SuppressWarnings("unchecked")
	@GetMapping(value = ReferenceConstants.REF_TYP_STATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<State> findState() throws IOException {
		ArrayList<State> states = new ArrayList<>();
		states.addAll(JsonUtil.transferToList(refStateSvc.findAll(), State.class));
		return states;
	}

	@PostMapping(value = ReferenceConstants.CREATE_STATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<State> createState(@RequestBody List<State> states, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(states)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<State> returnStates = new ArrayList<>();
		for (State state : states) {
			RefState refState = JsonUtil.transferToObject(state, RefState.class);
			refState.setCreateId((BaseUtil.isStringNull(refState.getCreateId()) ? userId : refState.getCreateId()));
			refState.setUpdateId((BaseUtil.isStringNull(refState.getUpdateId()) ? userId : refState.getUpdateId()));
			if (state.getCountry() != null && state.getCountry().getCntryCd() != null) {
				refState.setCountry(JsonUtil.transferToObject(state.getCountry(), RefCountry.class));
			} 
			returnStates.add(JsonUtil.transferToObject(refStateSvc.create(refState), State.class));
		}
		return returnStates;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_STATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<State> updateState(@RequestBody List<State> states, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(states)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		String userId = getCurrUserId(request);
		List<State> returnStates = new ArrayList<>();
		for (State state : states) {
			RefState refState = JsonUtil.transferToObject(state, RefState.class);
			refState.setCreateId((BaseUtil.isStringNull(refState.getCreateId()) ? userId : refState.getCreateId()));
			refState.setUpdateId((BaseUtil.isStringNull(refState.getUpdateId()) ? userId : refState.getUpdateId()));
			if (state.getCountry() != null && state.getCountry().getCntryCd() != null) {
				refState.setCountry(JsonUtil.transferToObject(state.getCountry(), RefCountry.class));
			} 
			returnStates.add(JsonUtil.transferToObject(refStateSvc.update(refState), State.class));
		}
		return returnStates;
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = ReferenceConstants.SEARCH_STATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<State> searchState(@RequestBody State state, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(state)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		RefState refState = JsonUtil.transferToObject(state, RefState.class);
		if (state.getCountry() != null && state.getCountry().getCntryCd() != null) {
			refState.setCountry(JsonUtil.transferToObject(state.getCountry(), RefCountry.class));
		} 
		return JsonUtil.transferToList(refStateSvc.searchAllByProperty(refState), State.class);
	}

	// ------------- STATE - END -----------------------------//


	@GetMapping(value = ReferenceConstants.REF_TYP_DOCUMENT, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefDocument> getDocuments(HttpServletRequest request) {
		return refDocSvc.getAllDocuments();
	}


	@PostMapping(value = ReferenceConstants.REF_TYP_DOCUMENT, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefDocument> findDocumentsByDocId(@RequestBody Document docs, HttpServletRequest request) {
		if (!BaseUtil.isObjNull(docs)) {
			RefDocument refDoc = dozerMapper.map(docs, RefDocument.class);
			return refDocSvc.findTrxnDocumentsByCriteria(refDoc);
		} else {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}
	}


	@PostMapping(value = ReferenceConstants.CREATE_DOCUMENT, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefDocument createDocument(@RequestBody Document docs, HttpServletRequest request) {
		RefDocument refDoc = null;
		if (BaseUtil.isObjNull(docs)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refDoc = dozerMapper.map(docs, RefDocument.class);
			refDoc.setCreateDt(getSQLTimestamp());
			refDoc.setCreateId(ConfigConstants.SYSTEM);
			refDoc.setUpdateDt(getSQLTimestamp());
			refDoc.setUpdateId(ConfigConstants.SYSTEM);
			refDoc = refDocSvc.create(refDoc);
		}

		return refDoc;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_DOCUMENT, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefDocument updateDocument(@RequestBody Document docs, HttpServletRequest request) {
		RefDocument refDoc = null;

		if (BaseUtil.isObjNull(docs) || BaseUtil.isObjNull(docs.getDocId())) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refDoc = refDocSvc.findTrxnDocumentsByDocId(docs.getDocId());
			if (BaseUtil.isObjNull(refDoc)) {
				throw new BeException(BeErrorCodeEnum.I404C001);
			}

			refDoc.setDocTrxnNo(docs.getDocTrxnNo());
			refDoc.setType(docs.getType());
			refDoc.setSize(docs.getSize());
			refDoc.setCompulsary(docs.isCompulsary());
			refDoc.setDimensionCompulsary(docs.isDimensionCompulsary());
			refDoc.setMinWidth(docs.getMinWidth());
			refDoc.setMaxWidth(docs.getMaxWidth());
			refDoc.setMinHeight(docs.getMinHeight());
			refDoc.setMaxHeight(docs.getMaxHeight());
			refDoc.setDmBucket(docs.getDmBucket());
			refDoc.setUpdateDt(getSQLTimestamp());
			refDoc.setUpdateId(ConfigConstants.SYSTEM);
			refDoc = refDocSvc.update(refDoc);
		}

		return refDoc;
	}


	// RefMetadata services
	@GetMapping(value = ReferenceConstants.REF_TYP_METADATA, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefMetadata> getAllMetadata(HttpServletRequest request) {
		return refMetadataSvc.getAllMetadata();
	}


	@PostMapping(value = ReferenceConstants.REF_TYP_METADATA, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefMetadata> findRefMetadataByCriteria(@RequestBody Metadata metadata, HttpServletRequest request) {
		if (!BaseUtil.isObjNull(metadata)) {
			RefMetadata refMtdt = dozerMapper.map(metadata, RefMetadata.class);
			return refMetadataSvc.findMetadataByCriteria(refMtdt);
		} else {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}
	}


	@PostMapping(value = ReferenceConstants.CREATE_METADATA, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefMetadata createMetadata(@RequestBody Metadata metadata, HttpServletRequest request) {
		RefMetadata refMtdt = null;

		if (BaseUtil.isObjNull(metadata)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refMtdt = dozerMapper.map(metadata, RefMetadata.class);
			refMtdt.setCreateDt(getSQLTimestamp());
			refMtdt.setCreateId(ConfigConstants.SYSTEM);
			refMtdt.setUpdateDt(getSQLTimestamp());
			refMtdt.setUpdateId(ConfigConstants.SYSTEM);
			refMtdt = refMetadataSvc.create(refMtdt);
		}

		return refMtdt;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_METADATA, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefMetadata updateMetadata(@RequestBody Metadata metadata, HttpServletRequest request) {
		RefMetadata refMtdt = null;

		if (BaseUtil.isObjNull(metadata) || BaseUtil.isObjNull(metadata.getMtdtId())) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refMtdt = refMetadataSvc.find(metadata.getMtdtId());
			if (BaseUtil.isObjNull(refMtdt)) {
				throw new BeException(BeErrorCodeEnum.I404C001);
			}

			refMtdt.setMtdtId(metadata.getMtdtId());
			refMtdt.setStatus(metadata.isStatus());
			refMtdt.setMtdtCd(metadata.getMtdtCd());
			refMtdt.setMtdtType(metadata.getMtdtType());
			refMtdt.setMtdtDesc(metadata.getMtdtDesc());
			refMtdt.setUpdateDt(getSQLTimestamp());
			refMtdt.setUpdateId(ConfigConstants.SYSTEM);
			refMtdt = refMetadataSvc.update(refMtdt);
		}

		return refMtdt;
	}


	// Reason services
	@GetMapping(value = ReferenceConstants.REF_TYP_REASON, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefReason> getAllReasons(HttpServletRequest request) {
		return refReasonSvc.getAllReason();
	}


	@PostMapping(value = ReferenceConstants.REF_TYP_REASON, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefReason> findRefReasonByCriteria(@RequestBody Reason reason, HttpServletRequest request) {
		if (!BaseUtil.isObjNull(reason)) {
			RefReason refReson = dozerMapper.map(reason, RefReason.class);
			return refReasonSvc.searchAllByProperty(refReson);
		} else {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}
	}


	@PostMapping(value = ReferenceConstants.CREATE_REASON, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefReason createReason(@RequestBody Reason reason, HttpServletRequest request) {
		RefReason refReason = null;
		if (BaseUtil.isObjNull(reason)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refReason = dozerMapper.map(reason, RefReason.class);
			refReason.setCreateDt(getSQLTimestamp());
			refReason.setCreateId(ConfigConstants.SYSTEM);
			refReason.setUpdateDt(getSQLTimestamp());
			refReason.setUpdateId(ConfigConstants.SYSTEM);
			refReason = refReasonSvc.create(refReason);
		}

		return refReason;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_REASON, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefReason updateReason(@RequestBody Reason reason, HttpServletRequest request) {
		RefReason refReason = null;
		if (BaseUtil.isObjNull(reason) || BaseUtil.isObjNull(reason.getReasonId())) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refReason = refReasonSvc.find(reason.getReasonId());
			if (BaseUtil.isObjNull(refReason)) {
				throw new BeException(BeErrorCodeEnum.I404C001);
			}

			refReason.setStatus(reason.isStatus());
			refReason.setReasonCd(reason.getReasonCd());
			refReason.setReasonType(reason.getReasonType());
			refReason.setReasonDesc(reason.getReasonDesc());
			refReason.setUpdateDt(getSQLTimestamp());
			refReason.setUpdateId(ConfigConstants.SYSTEM);
			refReason = refReasonSvc.update(refReason);
		}

		return refReason;
	}


	// Status services
	@GetMapping(value = ReferenceConstants.REF_TYP_STATUS, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RefStatus> getAllStatus(HttpServletRequest request) {
		return refStatusSvc.getAllStatus();
	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = ReferenceConstants.REF_TYP_STATUS, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Status> findRefStatusByCriteria(@RequestBody Status status, HttpServletRequest request) {
		if (BaseUtil.isObjNull(status)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		}

		List<Status> result = new ArrayList<>();
		try {
			RefStatus refStatus = JsonUtil.transferToObject(status, RefStatus.class);
			result = JsonUtil.transferToList(refStatusSvc.searchAllByProperty(refStatus), Status.class);
		} catch (IOException e) {
			// DO NOTHING
		}
		return result;
	}


	@PostMapping(value = ReferenceConstants.CREATE_STATUS, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefStatus createStatus(@RequestBody Status status, HttpServletRequest request) {

		RefStatus refStatus = null;
		if (BaseUtil.isObjNull(status)) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refStatus = dozerMapper.map(status, RefStatus.class);
			refStatus.setCreateDt(getSQLTimestamp());
			refStatus.setCreateId(ConfigConstants.SYSTEM);
			refStatus.setUpdateDt(getSQLTimestamp());
			refStatus.setUpdateId(ConfigConstants.SYSTEM);
			refStatusSvc.create(refStatus);
		}

		return refStatus;
	}


	@PostMapping(value = ReferenceConstants.UPDATE_STATUS, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public RefStatus updateStatus(@RequestBody Status status, HttpServletRequest request) {
		RefStatus refStatus = null;
		if (BaseUtil.isObjNull(status) || BaseUtil.isObjNull(status.getStatusId())) {
			throw new BeException(BeErrorCodeEnum.E400C913);
		} else {
			refStatus = refStatusSvc.find(status.getStatusId());
			if (BaseUtil.isObjNull(refStatus)) {
				throw new BeException(BeErrorCodeEnum.I404C001);
			}

			refStatus.setStatusType(status.getStatusType());
			refStatus.setStatusCd(status.getStatusCd());
			refStatus.setStatusDesc(status.getStatusDesc());
			refStatus.setUpdateDt(getSQLTimestamp());
			refStatus.setUpdateId(ConfigConstants.SYSTEM);
			refStatusSvc.update(refStatus);
		}

		return refStatus;
	}

	@GetMapping(value = "/evict", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		LOGGER.debug("Cache Evict with Prefix: {}", prefixKey);
		RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(BeCacheConstants.CACHE_BUCKET);
		if (!BaseUtil.isObjNull(prefixKey)) {
			cache.evictByPrefix(prefixKey);
		} else {
			cache.clear();
			return true;
		}
		return false;
	}

	
}
