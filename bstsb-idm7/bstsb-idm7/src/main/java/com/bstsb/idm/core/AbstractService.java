/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S extends AbstractEntity> implements SimpleDataAccess<S> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract GenericRepository<S> primaryDao();


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