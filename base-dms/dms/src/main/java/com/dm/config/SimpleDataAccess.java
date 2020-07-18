/**
 * Copyright 2019
 */
package com.dm.config;


import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
public interface SimpleDataAccess<S> {

	S create(final S s);


	S update(final S s);


	S find(final String id);


	List<S> findAll();


	boolean delete(final String id);

}
