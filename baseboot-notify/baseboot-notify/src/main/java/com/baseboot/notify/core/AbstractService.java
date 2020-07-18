/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.core;


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

import com.baseboot.idm.sdk.client.IdmServiceClient;
import com.baseboot.notify.sdk.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
public abstract class AbstractService<S extends AbstractEntity, id>
		implements SimpleDataAccess<S, Integer>, BaseConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract GenericRepository<S> primaryDao();


	@Autowired
	private IdmServiceClient idmService;


	public IdmServiceClient getIdmService(HttpServletRequest request) {
		String messageId = request.getHeader(HEADER_MESSAGE_ID);
		if (messageId == null) {
			messageId = String.valueOf(UUID.randomUUID());
		}
		String auth = request.getHeader(HEADER_AUTHORIZATION);
		idmService.setAuthToken(auth);
		idmService.setMessageId(messageId);
		return idmService;
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public <T extends AbstractEntity> S create(S s) {
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public <T extends AbstractEntity> S update(S s) {
		this.primaryDao().saveAndFlush(s);
		return s;
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T extends AbstractEntity> S find(java.lang.Integer id) {
		return null; // this.primaryDao().findById(id);
	}


	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(java.lang.Integer id) {
		try {
			this.primaryDao().deleteById(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
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