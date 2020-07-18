/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.core;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.sdk.client.IdmServiceClient;
import com.bstsb.util.constants.BaseConstants;



/**
 * The Class AbstractService.
 *
 * @author Mary Jane Buenaventura
 * @param <S> the generic type
 * @since May 4, 2018
 */
@Transactional
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S extends AbstractEntity> implements SimpleDataAccess<S> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	/**
	 * Primary dao.
	 *
	 * @return the generic repository
	 */
	public abstract GenericRepository<S> primaryDao();


	/** The idm service. */
	@Autowired
	private IdmServiceClient idmService;


	/**
	 * Gets the idm service.
	 *
	 * @param request the request
	 * @return the idm service
	 */
	public IdmServiceClient getIdmService(HttpServletRequest request) {
		String messageId = request.getHeader(BaseConstants.HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(BaseConstants.HEADER_AUTHORIZATION);
		idmService.setAuthToken(auth);
		idmService.setMessageId(messageId);
		return idmService;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.SimpleDataAccess#create(com.bstsb.notify.core.AbstractEntity)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public S create(S s) {
		// create
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.SimpleDataAccess#update(com.bstsb.notify.core.AbstractEntity)
	 */
	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public S update(S s) {
		// update
		if (s != null) {
			this.primaryDao().saveAndFlush(s);
		}
		return s;
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.SimpleDataAccess#find(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public S find(java.lang.Integer id) {
		return this.primaryDao().findOne(id);
	}


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.SimpleDataAccess#delete(java.lang.Integer)
	 */
	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return false;
		}
	}


	/**
	 * Gets the SQL timestamp.
	 *
	 * @return the SQL timestamp
	 */
	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}

}