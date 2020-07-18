/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.report.sdk.exception.ReportException;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Transactional
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S extends AbstractEntity, id> implements SimpleDataAccess<S> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	protected PlatformTransactionManager transactionManager;


	public abstract GenericRepository<S> primaryDao();


	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public <T extends AbstractEntity> S create(S s) {
		try {
			this.primaryDao().save(s);
			return s;
		} catch (Throwable e) {
			LOGGER.error("Create Exception", e);
			return null;
		}
	}


	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public <T extends AbstractEntity> S update(S s) {
		try {
			this.primaryDao().saveAndFlush(s);
			return s;
		} catch (Throwable e) {
			LOGGER.error("Update Exception", e);
			return null;
		}
	}


	@Transactional(readOnly = true, rollbackFor = ReportException.class)
	public <T extends AbstractEntity> S find(java.lang.Integer id) {
		try {
			return this.primaryDao().findOne(id);
		} catch (Throwable e) {
			LOGGER.error("Find Exception", e);
			return null;
		}
	}


	@Transactional(readOnly = true, rollbackFor = ReportException.class)
	public <T extends AbstractEntity> List<S> findAll() {
		try {
			return this.primaryDao().findAll();
		} catch (Throwable e) {
			LOGGER.error("FindAll Exception", e);
			return null;
		}
	}


	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Throwable e) {
			LOGGER.error("delete Exception", e);
			return false;
		}
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		return currentTimestamp;
	}

}