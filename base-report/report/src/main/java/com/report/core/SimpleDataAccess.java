/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.core;


import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity> {

	public S create(final S s);


	public S update(final S s);


	public S find(final Integer id);


	public List<S> findAll();


	public boolean delete(final Integer id);

}
