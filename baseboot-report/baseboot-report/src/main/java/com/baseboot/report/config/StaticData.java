/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.baseboot.be.sdk.client.BeServiceClient;
import com.baseboot.be.sdk.constants.ServiceConstants;
import com.baseboot.be.sdk.exception.BeException;
import com.baseboot.be.sdk.model.City;
import com.baseboot.be.sdk.model.Country;
import com.baseboot.be.sdk.model.State;
import com.baseboot.be.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.report.sdk.constants.RptCacheConstants;
import com.baseboot.report.util.ConfigConstants;
import com.baseboot.report.util.WebUtil;


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
	private BeServiceClient apjatiService;


	public StaticData() {
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		String skey = messageSource.getMessage(ConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());
		String clientId = messageSource.getMessage(ConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault());
		apjatiService.setToken(skey);
		apjatiService.setClientId(clientId);
	}


	private BeServiceClient getApjatiService() {
		apjatiService.setAuthToken(null);
		apjatiService.setMessageId(String.valueOf(UUID.randomUUID()));
		return apjatiService;
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> cmnConfig() {
		return ((Map<String, String>) getList(ServiceConstants.STAT_LST_CONFIG));
	}


	@SuppressWarnings("unchecked")
	private Object getList(String entityType) {
		String cacheKey = RptCacheConstants.CACHE_STATIC_RPT.concat(entityType);
		Cache cache = cacheManager.getCache(RptCacheConstants.CACHE_BUCKET);
		Cache.ValueWrapper cv = cache.get(cacheKey);

		if (BaseUtil.isEquals(entityType, ServiceConstants.STAT_LST_CITY)) {
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				return (List<City>) cv.get();
			}
			try {
				List<City> objLst = getApjatiService().reference().findByAllCities(false);
				if (!BaseUtil.isListNullZero(objLst)) {
					cache.put(cacheKey, objLst);
				}
				return objLst;
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (BeException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			return null;

		} else if (BaseUtil.isEquals(entityType, ServiceConstants.STAT_LST_CONFIG)) {
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				return (Map<String, String>) cv.get();
			}
			try {
				Map<String, String> objMap = getApjatiService().reference().findAllConfig();
				if (!BaseUtil.isObjNull(objMap)) {
					cache.put(cacheKey, objMap);
				}
				return objMap;
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (BeException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			return null;

		} else if (BaseUtil.isEquals(entityType, ServiceConstants.STAT_LST_STATE)) {
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				return (List<State>) cv.get();
			}
			try {
				LOGGER.info("STAT_LST_STATE");
				List<State> objLst = getApjatiService().reference().findAllState();

				if (!BaseUtil.isListNullZero(objLst)) {
					cache.put(cacheKey, objLst);
				}
				return objLst;
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			return null;

		} else if (BaseUtil.isEquals(entityType, ServiceConstants.STAT_LST_COUNTRY)) {
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				return (List<Country>) cv.get();
			}
			try {
				List<Country> objLst = getApjatiService().reference().allCountry();
				if (!BaseUtil.isListNullZero(objLst)) {
					cache.put(cacheKey, objLst);
				}
				return objLst;
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e))
					throw e;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			return null;
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
			if (BaseUtil.isEqualsCaseIgnore(cityCode, city.getCityCode())) {
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
		List<State> newLst = new ArrayList<State>();
		for (State obj : lst) {
			if (BaseUtil.isEqualsCaseIgnore(state, obj.getStateCode())) {
				newLst.add(obj);
			}
		}
		return newLst;
	}


	@SuppressWarnings("unchecked")
	public List<State> stateList(String country) {
		List<State> lst = (List<State>) getList(ServiceConstants.STAT_LST_STATE);
		List<State> newLst = new ArrayList<State>();
		if (!BaseUtil.isListNullZero(lst)) {
			for (State obj : lst) {
				if (BaseUtil.isEqualsCaseIgnore(country, obj.getCountry())) {
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
				if (BaseUtil.isEqualsCaseIgnore(stateCode, obj.getStateCode())) {
					return obj;
				}
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<Country> countryList() {
		return (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
	}


	@SuppressWarnings("unchecked")
	public List<Country> countrySourceList() {
		List<Country> lst = (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
		List<Country> newLst = new ArrayList<Country>();
		if (!BaseUtil.isListNullZero(lst)) {
			for (Country obj : lst) {
				if (!obj.getCntryInd().isEmpty()) {
					newLst.add(obj);
				}
			}
		}
		return newLst;
	}


	@SuppressWarnings("unchecked")
	public Country country(String code) {
		List<Country> lst = (List<Country>) getList(ServiceConstants.STAT_LST_COUNTRY);
		if (!BaseUtil.isListNullZero(lst)) {
			for (Country obj : lst) {
				if (BaseUtil.isEqualsCaseIgnore(code, obj.getCntryCode())) {
					return obj;
				}
			}
		}
		return null;
	}

}