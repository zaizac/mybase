/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.core;


import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.report.sdk.constants.ReportErrorCodeEnum;
import com.report.sdk.exception.ReportException;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Transactional
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S extends AbstractEntity> implements SimpleDataAccess<S> {

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	protected PlatformTransactionManager transactionManager;


	public abstract GenericRepository<S> primaryDao();


	@Override
	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public S create(S s) {
		try {
			this.primaryDao().save(s);
			return s;
		} catch (Exception e) {
			throw new ReportException(ReportErrorCodeEnum.E500RPT005);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public S update(S s) {
		try {
			this.primaryDao().saveAndFlush(s);
			return s;
		} catch (Exception e) {
			throw new ReportException(ReportErrorCodeEnum.E500RPT006);
		}
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = ReportException.class)
	public S find(java.lang.Integer id) {
		try {
			return this.primaryDao().findOne(id);
		} catch (Exception e) {
			throw new ReportException(ReportErrorCodeEnum.E500RPT008);
		}

	}


	@Override
	@Transactional(readOnly = true, rollbackFor = ReportException.class)
	public List<S> findAll() {
		try {
			return this.primaryDao().findAll();
		} catch (Exception e) {
			throw new ReportException(ReportErrorCodeEnum.E500RPT008);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = ReportException.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			throw new ReportException(ReportErrorCodeEnum.E500RPT007);
		}
	}
}