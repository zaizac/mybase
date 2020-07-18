package com.wfw.dao;


import com.wfw.model.AbstractEntity;


/**
 * @author Kamruzzaman
 *
 */
public interface SimpleDao<S extends AbstractEntity> {

	S create(final S s);


	S update(final S s);


	S find(final Integer id);


	boolean delete(final Integer id);

}
