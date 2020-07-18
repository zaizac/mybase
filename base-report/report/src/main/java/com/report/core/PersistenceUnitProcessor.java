/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.core;


import javax.persistence.spi.PersistenceUnitTransactionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class PersistenceUnitProcessor implements PersistenceUnitPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUnitProcessor.class);


	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		PersistenceUnitTransactionType puTransType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		pui.setTransactionType(puTransType);
		LOGGER.info("Transaction Type : {} ", pui.getTransactionType().name());
	}

}
