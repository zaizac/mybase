/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.be.sdk.client.BeServiceClient;
import com.be.sdk.constants.ServiceConstants;
import com.be.sdk.model.City;
import com.be.sdk.model.Country;
import com.be.sdk.model.State;
import com.idm.sdk.exception.IdmException;
import com.report.sdk.constants.RptCacheConstants;
import com.report.util.WebUtil;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.RefDocuments;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Component
public class StaticData implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticData.class);

	@Autowired
	CacheManager cacheManager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private BeServiceClient beService;
	
	@Autowired
    HttpServletRequest request;


	@Override
	public void afterPropertiesSet() throws Exception {
		String skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String clientId = messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault());
		beService.setToken(skey);
		beService.setClientId(clientId);
	}


	private BeServiceClient getBeService() {
        beService.setAuthToken(request.getHeader(BaseConstants.HEADER_AUTHORIZATION));
        beService.setMessageId(StringUtils.hasText(request.getHeader(BaseConstants.HEADER_MESSAGE_ID))
                ? request.getHeader(BaseConstants.HEADER_MESSAGE_ID)
                : String.valueOf(UUID.randomUUID()));
        return beService;
    }


	@SuppressWarnings("unchecked")
	public Map<String, String> cmnConfig() {
		return ((Map<String, String>) getList(ServiceConstants.STAT_LST_CONFIG));
	}


	@SuppressWarnings("rawtypes")
	private Object getList(String entityType) {
		String cacheKey = RptCacheConstants.CACHE_STATIC_RPT.concat(entityType);
		Cache cache = cacheManager.getCache(RptCacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);

		if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
			return cv.get();
		}

		List objLst = null;
		try {

			switch (entityType) {
			case ServiceConstants.STAT_LST_CITY:
				objLst = getBeService().reference().findAllCities();
				break;
			case ServiceConstants.STAT_LST_CONFIG:
				objLst = (List) getBeService().reference().findAllBeConfig();
				break;
			case ServiceConstants.STAT_LST_STATE:
				objLst = getBeService().reference().findAllStates();
				break;
			case ServiceConstants.STAT_LST_COUNTRY:
				objLst = getBeService().reference().findAllCountry();
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
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return null;
	}


	@SuppressWarnings("unchecked")
	public List<City> cityList() {
		return (List<City>) getList(ServiceConstants.STAT_LST_CITY);
	}


	@SuppressWarnings("unchecked")
	public City city(String cityCode) {
		List<City> cityLst = (List<City>) getList(ServiceConstants.STAT_LST_CITY);
		for (City city : cityLst) {
			if (BaseUtil.isEqualsCaseIgnore(cityCode, city.getCityCd())) {
				return city;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<State> stateList() {
		return (List<State>) getList(ServiceConstants.STAT_LST_STATE);
	}


	@SuppressWarnings("unchecked")
	public List<State> cityList(String state) {
		List<State> lst = (List<State>) getList(ServiceConstants.STAT_LST_STATE);
		List<State> newLst = new ArrayList<>();
		for (State obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(state, obj.getStateCd())) {
				newLst.add(obj);
			}
		}
		return newLst;
	}


	@SuppressWarnings("unchecked")
	public List<State> stateList(String country) {
		List<State> lst = (List<State>) getList(ServiceConstants.STAT_LST_STATE);
		List<State> newLst = new ArrayList<>();
		if (!BaseUtil.isListNullZero(lst)) {
			for (State obj : lst) {
				if (!BaseUtil.isObjNull(obj.getCountry()) 
						&& BaseUtil.isEqualsCaseIgnore(country, obj.getCountry().getCntryCd())) {
					newLst.add(obj);
				}
			}
		}
		return newLst;
	}


	@SuppressWarnings("unchecked")
	public State state(String stateCode) {
		List<State> lst = (List<State>) getList(ServiceConstants.STAT_LST_STATE);
		if (!BaseUtil.isListNullZero(lst)) {
			for (State obj : lst) {
				if (BaseUtil.isEqualsCaseIgnore(stateCode, obj.getStateCd())) {
					return obj;
				}
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<Country> getAllCountryList() {
		return ((List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY));
	}


	@SuppressWarnings("unchecked")
	public List<Country> getCountryListByCountryCode(String cntryCode) {

		List<Country> countryList = ((List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY));

		List<Country> countryList2 = new ArrayList<>();

		if (!BaseUtil.isListNull(countryList)) {

			try {
				for (Country country : countryList) {

					if (BaseUtil.isEqualsCaseIgnore(country.getCntryCd(), cntryCode)) {
						countryList2.add(country);
					}
				}
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
		}
		return countryList2;
	}


	@SuppressWarnings("unchecked")
	public List<Country> countryList() {
		return (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
	}


	@SuppressWarnings("unchecked")
	public List<Country> countrySourceList() {
		List<Country> lst = (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
		List<Country> newLst = new ArrayList<>();
		for (Country obj : lst) {
			if (!obj.getCntryInd()) {
				newLst.add(obj);
			}
		}

		return newLst;
	}


	@SuppressWarnings("unchecked")
	public Country country(String code) {
		List<Country> lst = (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
		for (Country obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(code, obj.getCntryCd())) {
				return obj;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public String countryDesc(String code) {
		List<Country> lst = (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
		for (Country obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(code, obj.getCntryCd())) {
				return obj.getCntryDesc();
			}
		}
		return "";
	}


	public Map<String, String> defaultMaritalStatusList() {
		Map<String, String> map = new HashMap<>();
		map.put("M", "Married");
		map.put("S", "Single");
		map.put("D", "Divorced");
		return map;
	}


	@SuppressWarnings("unchecked")
	public RefDocuments refDocByDocId(Integer docId) {
		List<RefDocuments> lst = (List<RefDocuments>) getList(ServiceConstants.STAT_LST_DOCUMENT);
		if (!BaseUtil.isObjNull(lst)) {
			for (RefDocuments obj : lst) {
				if (docId == obj.getDocId()) {
					return obj;
				}
			}
		}
		return null;
	}


}