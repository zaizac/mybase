/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.core;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * The Interface IMongoRepository.
 *
 * @author Mary Jane Buenaventura
 * @param <T> the generic type
 * @since May 8, 2018
 */
@NoRepositoryBean
public interface IMongoRepository<T> extends MongoRepository<T, String>, PagingAndSortingRepository<T, String> {
}