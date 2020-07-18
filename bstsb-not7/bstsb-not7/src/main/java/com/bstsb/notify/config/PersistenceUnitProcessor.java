/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.config;


import javax.persistence.spi.PersistenceUnitTransactionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;


/**
 * The Class PersistenceUnitProcessor.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class PersistenceUnitProcessor implements PersistenceUnitPostProcessor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUnitProcessor.class);


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor
	 * #postProcessPersistenceUnitInfo(org.springframework.orm.jpa.
	 * persistenceunit.MutablePersistenceUnitInfo)
	 */
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		PersistenceUnitTransactionType puTransType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		pui.setTransactionType(puTransType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transaction Type : {} ", pui.getTransactionType().name());
		}
	}

}