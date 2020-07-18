/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@NoRepositoryBean
public interface GenericRepository<T>
		extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Integer> {

}