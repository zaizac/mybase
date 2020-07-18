/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.service;


import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public interface SimpleDataAccess<S> {

	S create(final S s);


	S update(final S s);


	S find(final Integer id);


	List<S> findAll();


	boolean delete(final Integer id);

}
