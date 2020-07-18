/**
 * Copyright 2019. 
 */
package com.notify.core;



/**
 * The Interface SimpleDataAccess.
 *
 * @author Mary Jane Buenaventura
 * @param <S> the generic type
 * @since May 4, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity> {

	/**
	 * Creates the.
	 *
	 * @param s the s
	 * @return the s
	 */
	S create(final S s);


	/**
	 * Update.
	 *
	 * @param s the s
	 * @return the s
	 */
	S update(final S s);


	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the s
	 */
	S find(final Integer id);


	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean delete(final Integer id);

}