/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.TestSignUpRepository;
import com.be.model.TestSignUp;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 *
 */
@Lazy
@Transactional
@Service("testSignUpSvc")
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier("testSignUpSvc")
public class TestSignUpService extends AbstractService<TestSignUp> {

	@Autowired
	private TestSignUpRepository testSignUpDao;


	@Override
	public GenericRepository<TestSignUp> primaryDao() {
		return testSignUpDao;
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public TestSignUp findByRefNo(String refNo) {
		return testSignUpDao.findByRefNo(refNo);
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}

}
