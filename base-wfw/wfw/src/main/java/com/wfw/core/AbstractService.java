/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.core;


import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wfw.dao.SimpleDao;
import com.wfw.model.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@EnableTransactionManagement
public abstract class AbstractService<S extends AbstractEntity> implements SimpleDao<S> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	protected Mapper dozerMapper;


	public abstract GenericDao<S> primaryDao();


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, propagation = Propagation.REQUIRED)
	public S create(S s) {
		// create
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, propagation = Propagation.REQUIRED)
	public S update(S s) {
		// update
		this.primaryDao().count();
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public S find(java.lang.Integer id) {
		return this.primaryDao().findOne(id);
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			return false;
		}
	}


	@Modifying(clearAutomatically = true)
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(S s) {
		this.primaryDao().delete(s);
	}

}
