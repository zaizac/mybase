/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import javax.persistence.spi.PersistenceUnitTransactionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class PersistenceUnitProcessor implements PersistenceUnitPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUnitProcessor.class);


	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		PersistenceUnitTransactionType puTransType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		pui.setTransactionType(puTransType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transaction Type : {} ", pui.getTransactionType().name());
		}
	}

}
