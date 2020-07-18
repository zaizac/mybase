/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public interface SimpleDataAccess<S> {

	public S create(final S s);


	public S update(final S s);


	public S find(final Integer id);


	public List<S> findAll();


	public boolean delete(final Integer id);

}
