/**
 * Copyright 2019
 */
package com.be.config;


import javax.persistence.spi.PersistenceUnitTransactionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
public class JtaPuPostProcessor implements PersistenceUnitPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(JtaPuPostProcessor.class);


	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
		PersistenceUnitTransactionType transacType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		mutablePersistenceUnitInfo.setTransactionType(transacType);
		if (transacType != null) {
			LOGGER.info("Transaction Type :{}", transacType);
		}
	}

}