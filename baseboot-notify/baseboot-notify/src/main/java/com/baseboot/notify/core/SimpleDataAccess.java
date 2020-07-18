/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.core;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity, ID extends Serializable> {

	public <T extends AbstractEntity> S create(final S s);


	public <T extends AbstractEntity> S update(final S s);


	public <T extends AbstractEntity> S find(final Integer id);


	public boolean delete(final Integer id);

}