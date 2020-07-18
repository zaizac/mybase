/**
 * Copyright 2019. 
 */
package com.dm.core;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dm.config.SimpleDataAccess;
import com.dm.constants.QualifierConstants;
import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.util.model.IQfCriteria;


/**
 * The Class AbstractService.
 *
 * @author Mary Jane Buenaventura
 * @param <S> the generic type
 * @since May 8, 2018
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@Transactional(QualifierConstants.TRANS_MANAGER)
public abstract class AbstractService<S> implements SimpleDataAccess<S> {

	@Autowired
	@Qualifier(QualifierConstants.TRANS_MANAGER)
	protected PlatformTransactionManager transactionManager;


	public abstract GenericRepository<S> primaryDao();


	public abstract List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria);


	@Override
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = false, rollbackFor = DmException.class)
	public S create(S s) {
		try {
			this.primaryDao().save(s);
			return s;
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM001);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = false, rollbackFor = DmException.class)
	public S update(S s) {
		try {
			this.primaryDao().saveAndFlush(s);
			return s;
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM002);
		}
	}


	@Override
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = true, rollbackFor = DmException.class)
	public S find(String id) {
		try {
			return this.primaryDao().findOne(id);
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM004);
		}

	}


	@Override
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = true, rollbackFor = DmException.class)
	public List<S> findAll() {
		try {
			return this.primaryDao().findAll();
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM004);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = false, rollbackFor = DmException.class)
	public boolean delete(String id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003);
		}
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}


	protected String getCurrUserId(HttpServletRequest request) {
		return String.valueOf(request.getAttribute("currUserId"));
	}

}
