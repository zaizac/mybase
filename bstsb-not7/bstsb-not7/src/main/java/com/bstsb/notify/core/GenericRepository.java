/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * The Interface GenericRepository.
 *
 * @author Mary Jane Buenaventura
 * @param <T> the generic type
 * @since May 4, 2018
 */
@NoRepositoryBean
public interface GenericRepository<T extends AbstractEntity>
		extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Integer> {

}