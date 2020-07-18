/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class AbstractService.
 *
 * @author Mary Jane Buenaventura
 * @param <S> the generic type
 * @since May 8, 2018
 */
public abstract class AbstractService<S> implements ICRUDDataAccess<S> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	/**
	 * Primary dao.
	 *
	 * @return the i mongo repository
	 */
	public abstract IMongoRepository<S> primaryDao();


	/* (non-Javadoc)
	 * @see com.dm.core.ICRUDDataAccess#create(java.lang.Object)
	 */
	@Override
	public S create(S s) {
		this.primaryDao().save(s);
		return s;
	}


	/* (non-Javadoc)
	 * @see com.dm.core.ICRUDDataAccess#find(java.lang.String)
	 */
	@Override
	public S find(String id) {
		return this.primaryDao().findOne(id);
	}


	/* (non-Javadoc)
	 * @see com.dm.core.ICRUDDataAccess#update(java.lang.Object)
	 */
	@Override
	public S update(S s) {
		primaryDao().save(s);
		return s;
	}


	/* (non-Javadoc)
	 * @see com.dm.core.ICRUDDataAccess#delete(java.lang.String)
	 */
	@Override
	public boolean delete(String id) {
		try {
			primaryDao().delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			return false;
		}
	}

}
