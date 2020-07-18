package com.idm.config;


import java.util.List;

import com.idm.core.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity> {

	S create(final S s);


	S update(final S s);


	S find(final Integer id);


	List<S> findAll();


	boolean delete(final Integer id);

}