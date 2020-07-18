/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wfw.model.AbstractEntity;

/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 * @param <T>
 */
@NoRepositoryBean
public interface GenericDao<T extends AbstractEntity>
		extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Integer> {

}
