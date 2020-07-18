package com.wfw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wfw.model.AbstractEntity;

@NoRepositoryBean
public interface GenericDao<T extends AbstractEntity> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Integer>  {
	
}
