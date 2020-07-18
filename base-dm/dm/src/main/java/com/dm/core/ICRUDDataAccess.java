/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.core;


/**
 * The Interface ICRUDDataAccess.
 *
 * @author Mary Jane Buenaventura
 * @param <S> the generic type
 * @since May 8, 2018
 */
public interface ICRUDDataAccess<S> {

	/**
	 * Creates the.
	 *
	 * @param s the s
	 * @return the s
	 */
	S create(final S s);


	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the s
	 */
	S find(final String id);


	/**
	 * Update.
	 *
	 * @param s the s
	 * @return the s
	 */
	S update(final S s);


	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean delete(final String id);

}