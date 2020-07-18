package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.RefMetadataQf;
import com.be.dao.RefMetadataRepository;
import com.be.model.RefMetadata;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


@Lazy
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Service(QualifierConstants.REF_METADATA_SVC)
@Qualifier(QualifierConstants.REF_METADATA_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefMetadataService extends AbstractService<RefMetadata> {

	@Autowired
	private RefMetadataRepository metadataRepository;

	@Autowired
	private RefMetadataQf refMetadataQf;


	@Override
	public GenericRepository<RefMetadata> primaryDao() {
		return metadataRepository;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refMetadataQf.generateCriteria(cb, from, criteria);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefMetadata> getAllMetadata() {
		return metadataRepository.getAllMetadata();
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefMetadata> findMetadataByCriteria(RefMetadata refMtdt) {
		return refMetadataQf.searchAllByProperty(refMtdt);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefMetadata saveAndUpdate(RefMetadata refDoc) {
		return metadataRepository.saveAndFlush(refDoc);
	}

}
