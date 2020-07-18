/**
 * Copyright 2019
 */
package com.be.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 16, 2018
 * @param <T>
 */
@NoRepositoryBean
public interface GenericRepository<T>
		extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Integer> {

}