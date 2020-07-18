/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.be.sdk.builder;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.be.sdk.client.BeRestTemplate;
import com.be.sdk.constants.BeUrlConstants;
import com.be.sdk.constants.ReferenceConstants;
import com.be.sdk.model.City;
import com.be.sdk.model.Config;
import com.be.sdk.model.Country;
import com.be.sdk.model.Document;
import com.be.sdk.model.Metadata;
import com.be.sdk.model.Reason;
import com.be.sdk.model.State;
import com.be.sdk.model.Status;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class ReferenceBaseService extends AbstractService {

	private BeRestTemplate restTemplate;

	private String url;


	public ReferenceBaseService(BeRestTemplate restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}


	@Override
	public BeRestTemplate restTemplate() {
		return restTemplate;
	}


	@Override
	public String url() {
		return url;
	}


	public boolean evict() {
		return evict(null);
	}


	public boolean evict(String prefixKey) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(BeUrlConstants.REFERENCE).append("/evict");
		if (!BaseUtil.isObjNull(prefixKey)) {
			sbUrl.append("?prefixKey=" + prefixKey);
		}

		return restTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> findAllBeConfig() {
		return restTemplate().getForObject(getServiceURI(BeUrlConstants.REFERENCE + BeUrlConstants.CONFIG),
				HashMap.class);
	}


	public List<Config> findBeConfig() {
		Config[] svcResp = restTemplate().getForObject(BeUrlConstants.CONFIG, Config[].class);
		return Arrays.asList(svcResp);
	}


	public List<Config> findBeConfig(String configAgency) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(BeUrlConstants.CONFIG).append("/{configAgency}");

		Map<String, Object> params = new HashMap<>();
		params.put("configAgency", configAgency);

		Config[] svcResp = restTemplate().getForObject(sbUrl.toString(), Config[].class, params);
		return Arrays.asList(svcResp);
	}


	public List<Country> updateRefCountries(List<Country> countries) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REF_REF_COUNTRY);
		Country[] refCountries = restTemplate().postForObject(getServiceURI(sb.toString()), countries,
				Country[].class);
		return Arrays.asList(refCountries);
	}


	// ----------------------- CITIES - START
	// ----------------------------------//
	public List<City> findAllCities() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.REF_TYP_CITY);
		City[] svcResp = restTemplate().getForObject(getServiceURI(sb.toString()), City[].class);
		return Arrays.asList(svcResp);
	}


	public List<City> createCities(List<City> cities) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.CREATE_CITY);
		City[] refCities = restTemplate().postForObject(getServiceURI(sb.toString()), cities, City[].class);
		return Arrays.asList(refCities);
	}


	public List<City> updateCities(List<City> cities) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.UPDATE_CITY);
		City[] refCities = restTemplate().postForObject(getServiceURI(sb.toString()), cities, City[].class);
		return Arrays.asList(refCities);
	}


	public List<City> searchCities(City city) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.SEARCH_CITY);
		City[] refCities = restTemplate().postForObject(getServiceURI(sb.toString()), city, City[].class);
		return Arrays.asList(refCities);
	}
	// ----------------------- CITIES - END
	// ----------------------------------//


	// ----------------------- STATE - START
	// ----------------------------------//
	public List<State> findAllStates() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.REF_TYP_STATE);
		State[] svcResp = restTemplate().getForObject(getServiceURI(sb.toString()), State[].class);
		return Arrays.asList(svcResp);
	}


	public List<State> createStates(List<State> states) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.CREATE_STATE);
		State[] refStates = restTemplate().postForObject(getServiceURI(sb.toString()), states, State[].class);
		return Arrays.asList(refStates);
	}


	public List<State> updateStates(List<State> states) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.UPDATE_STATE);
		State[] refStates = restTemplate().postForObject(getServiceURI(sb.toString()), states, State[].class);
		return Arrays.asList(refStates);
	}


	public List<State> searchStates(State states) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.SEARCH_STATE);
		State[] refStates = restTemplate().postForObject(getServiceURI(sb.toString()), states, State[].class);
		return Arrays.asList(refStates);
	}
	// ----------------------- STATE - END ----------------------------------//


	// ----------------------- COUNTRY - START
	// ----------------------------------//
	public List<Country> findAllCountry() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.REF_TYP_COUNTRY);
		Country[] svcResp = restTemplate().getForObject(getServiceURI(sb.toString()), Country[].class);
		return Arrays.asList(svcResp);
	}


	public List<Country> createCountries(List<Country> countries) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.CREATE_COUNTRY);
		Country[] refCountries = restTemplate().postForObject(getServiceURI(sb.toString()), countries,
				Country[].class);
		return Arrays.asList(refCountries);
	}


	public List<Country> updateCountries(List<Country> countries) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.UPDATE_COUNTRY);
		Country[] refCountries = restTemplate().postForObject(getServiceURI(sb.toString()), countries,
				Country[].class);
		return Arrays.asList(refCountries);
	}


	public List<Country> searchCountries(Country country) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(ReferenceConstants.SEARCH_COUNTRY);
		Country[] refCountries = restTemplate().postForObject(getServiceURI(sb.toString()), country, Country[].class);
		return Arrays.asList(refCountries);
	}


	public List<Country> findAllCountryServe() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_COUNTRY + "/serve");
		Country[] svcResp = restTemplate().getForObject(getServiceURI(sb.toString()), Country[].class);
		return Arrays.asList(svcResp);
	}
	// ----------------------- COUNTRY - END
	// ----------------------------------//


	public List<Status> findAllStatus() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_STATUS);
		Status[] svcResp = restTemplate().getForObject(getServiceURI(sb.toString()), Status[].class);
		return Arrays.asList(svcResp);
	}


	public Status findByStatusCd(String statusCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_STATUS);
		sb.append(BaseConstants.SLASH + statusCode);
		return restTemplate().getForObject(getServiceURI(sb.toString()), Status.class);
	}


	// RefDocuments services
	public List<Document> getAllDocument() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_DOCUMENT);
		Document[] refStats = restTemplate().getForObject(getServiceURI(sb.toString()), Document[].class);
		return Arrays.asList(refStats);
	}


	public List<Document> findDocumentsByCriteria(Document docs) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_DOCUMENT);
		Document[] resDocs = restTemplate().postForObject(getServiceURI(sb.toString()), docs, Document[].class);
		return Arrays.asList(resDocs);
	}


	public Document createDocuments(Document docs) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.CREATE_DOCUMENT);
		return restTemplate().postForObject(getServiceURI(sb.toString()), docs, Document.class);
	}


	public Document updateDocuments(Document docs) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.UPDATE_DOCUMENT);
		return restTemplate().postForObject(getServiceURI(sb.toString()), docs, Document.class);
	}


	// RefMetadata services
	public List<Metadata> getAllMetadata() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_METADATA);
		Metadata[] resList = restTemplate().getForObject(getServiceURI(sb.toString()), Metadata[].class);
		return Arrays.asList(resList);
	}


	public List<Metadata> findMetadataByCriteria(Metadata mtdt) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_METADATA);
		Metadata[] resList = restTemplate().postForObject(getServiceURI(sb.toString()), mtdt, Metadata[].class);
		return Arrays.asList(resList);
	}


	public Metadata createMetadata(Metadata mtdt) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.CREATE_DOCUMENT);
		return restTemplate().postForObject(getServiceURI(sb.toString()), mtdt, Metadata.class);
	}


	public Metadata updateMetadata(Metadata mtdt) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.UPDATE_DOCUMENT);
		return restTemplate().postForObject(getServiceURI(sb.toString()), mtdt, Metadata.class);
	}


	// Reason services
	public List<Reason> getAllReason() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_REASON);
		Reason[] resList = restTemplate().getForObject(getServiceURI(sb.toString()), Reason[].class);
		return Arrays.asList(resList);
	}


	public List<Reason> findReasonByCriteria(Reason reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_REASON);
		Reason[] resList = restTemplate().postForObject(getServiceURI(sb.toString()), reason, Reason[].class);
		return Arrays.asList(resList);
	}


	public Reason createReason(Reason reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.CREATE_REASON);
		return restTemplate().postForObject(getServiceURI(sb.toString()), reason, Reason.class);
	}


	public Reason updateReason(Reason reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.UPDATE_REASON);
		return restTemplate().postForObject(getServiceURI(sb.toString()), reason, Reason.class);
	}


	// Status services
	public List<Status> getAllStatus() {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_STATUS);
		Status[] resList = restTemplate().getForObject(getServiceURI(sb.toString()), Status[].class);
		return Arrays.asList(resList);
	}


	public List<Status> findStatusByCriteria(Status status) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.REF_TYP_STATUS);
		Status[] resList = restTemplate().postForObject(getServiceURI(sb.toString()), status, Status[].class);
		return Arrays.asList(resList);
	}


	public Status createStatus(Status status) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.CREATE_STATUS);
		return restTemplate().postForObject(getServiceURI(sb.toString()), status, Status.class);
	}


	public Status updateStatus(Status status) {
		StringBuilder sb = new StringBuilder();
		sb.append(BeUrlConstants.REFERENCE);
		sb.append(BaseConstants.SLASH + ReferenceConstants.UPDATE_STATUS);
		return restTemplate().postForObject(getServiceURI(sb.toString()), status, Status.class);
	}


	public List<Status> findByAllStatus() {
		Status[] svcResp = restTemplate().getForObject(getServiceURI(BeUrlConstants.STATUS), Status[].class);

		return Arrays.asList(svcResp);
	}


	public Status findByStatusCode(String statusCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("statusCode", statusCode);

		return restTemplate().getForObject(getServiceURI(BeUrlConstants.STATUS + "/{statusCode}"), Status.class,
				params);
	}

}