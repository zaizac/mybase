/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.core;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.service.SimpleDataAccess;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S> implements SimpleDataAccess<S> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract GenericRepository<S> primaryDao();


	@Override
	@Transactional(readOnly = false, rollbackFor = IdmException.class)
	public S create(S s) {
		try {
			this.primaryDao().save(s);
			return s;
		} catch (Exception e) {
			LOGGER.error("create Exception", e);
			return null;
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = IdmException.class)
	public S update(S s) {
		try {
			this.primaryDao().saveAndFlush(s);
			return s;
		} catch (Exception e) {
			LOGGER.error("update Exception", e);
			return null;
		}
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = IdmException.class)
	public S find(java.lang.Integer id) {
		try {
			return null; // this.primaryDao().findById(id);
		} catch (Exception e) {
			LOGGER.error("find Exception", e);
			return null;
		}
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = IdmException.class)
	public List<S> findAll() {
		try {
			return this.primaryDao().findAll();
		} catch (Exception e) {
			LOGGER.error("findAll Exception", e);
			return new ArrayList<>();
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = IdmException.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().deleteById(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("delete Exception", e);
			return false;
		}
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}

}