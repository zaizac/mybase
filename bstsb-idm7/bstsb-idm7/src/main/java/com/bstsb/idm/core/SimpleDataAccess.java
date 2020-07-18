/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.core;

/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity> {

	S create(final S s);


	S update(final S s);


	S find(final Integer id);


	boolean delete(final Integer id);

}