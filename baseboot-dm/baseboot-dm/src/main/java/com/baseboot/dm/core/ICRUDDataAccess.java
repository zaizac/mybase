/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.core;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface ICRUDDataAccess<S extends AbstractEntity, ID extends Serializable> {

	public <T extends AbstractEntity> S create(final S s);


	public <T extends AbstractEntity> S find(final String id);


	public <T extends AbstractEntity> S update(final S s);


	public boolean delete(final String id);

}