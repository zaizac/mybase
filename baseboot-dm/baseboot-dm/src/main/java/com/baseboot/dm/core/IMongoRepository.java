/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.core;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@NoRepositoryBean
public interface IMongoRepository<T extends AbstractEntity>
		extends MongoRepository<T, String>, PagingAndSortingRepository<T, String> {
}