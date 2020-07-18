/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.core;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public abstract class AbstractService<S extends AbstractEntity, id> implements ICRUDDataAccess<S, ObjectId> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract IMongoRepository<S> primaryDao();


	@Override
	public <T extends AbstractEntity> S create(S s) {
		this.primaryDao().save(s);
		return s;
	}


	@Override
	public <T extends AbstractEntity> S find(String id) {
		return this.primaryDao().findOne(id);
	}


	@Override
	public <T extends AbstractEntity> S update(S s) {
		primaryDao().save(s);
		return s;
	}


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
