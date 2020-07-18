/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.config;


import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class JtaPuPostProcessor implements PersistenceUnitPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(JtaPuPostProcessor.class);

	private boolean jtaMode = false;

	@Autowired
	private DataSource dataSource;


	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
		PersistenceUnitTransactionType transacType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		mutablePersistenceUnitInfo.setTransactionType(transacType);
		LOGGER.debug("[Transaction Type :{}]", mutablePersistenceUnitInfo.getTransactionType().name());
	}


	public boolean isJtaMode() {
		return jtaMode;
	}


	public void setJtaMode(boolean jtaMode) {
		this.jtaMode = jtaMode;
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}