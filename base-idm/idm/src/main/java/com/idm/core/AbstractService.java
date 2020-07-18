package com.idm.core;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.SimpleDataAccess;
import com.idm.constants.QualifierConstants;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@Transactional(QualifierConstants.TRANS_MANAGER)
public abstract class AbstractService<S extends AbstractEntity> implements SimpleDataAccess<S> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	@Qualifier(QualifierConstants.TRANS_MANAGER)
	protected PlatformTransactionManager transactionManager;


	public abstract GenericRepository<S> primaryDao();


	public abstract List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria);


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public S create(S s) {
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public S update(S s) {
		return this.primaryDao().saveAndFlush(s);
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public S find(Integer id) {
		return this.primaryDao().getOne(id);
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<S> findAll() {
		return this.primaryDao().findAll();
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e);
			return false;
		}
	}

}