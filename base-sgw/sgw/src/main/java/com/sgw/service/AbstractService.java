/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.service;


import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sgw.dao.GenericRepository;
import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.sgw.sdk.exception.SgwException;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@Transactional
public abstract class AbstractService<S> implements SimpleDataAccess<S> {

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	protected PlatformTransactionManager transactionManager;


	public abstract GenericRepository<S> primaryDao();


	@Override
	@Transactional(readOnly = false, rollbackFor = SgwException.class)
	public S create(S s) {
		try {
			this.primaryDao().save(s);
			return s;
		} catch (Exception e) {
			throw new SgwException(SgwErrorCodeEnum.E500SGW001);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = SgwException.class)
	public S update(S s) {
		try {
			this.primaryDao().saveAndFlush(s);
			return s;
		} catch (Exception e) {
			throw new SgwException(SgwErrorCodeEnum.E500SGW002);
		}
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = SgwException.class)
	public S find(java.lang.Integer id) {
		try {
			return this.primaryDao().findOne(id);
		} catch (Exception e) {
			throw new SgwException(SgwErrorCodeEnum.E500SGW004);
		}

	}


	@Override
	@Transactional(readOnly = true, rollbackFor = SgwException.class)
	public List<S> findAll() {
		try {
			return this.primaryDao().findAll();
		} catch (Exception e) {
			throw new SgwException(SgwErrorCodeEnum.E500SGW004);
		}
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = SgwException.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			throw new SgwException(SgwErrorCodeEnum.E500SGW003);
		}
	}

}