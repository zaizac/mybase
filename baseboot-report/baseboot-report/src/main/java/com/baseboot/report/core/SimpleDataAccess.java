/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public interface SimpleDataAccess<S extends AbstractEntity> {

	public <T extends AbstractEntity> S create(final S s);


	public <T extends AbstractEntity> S update(final S s);


	public <T extends AbstractEntity> S find(final Integer id);


	public <T extends AbstractEntity> List<S> findAll();


	public boolean delete(final Integer id);

}
