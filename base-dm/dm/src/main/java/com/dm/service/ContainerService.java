/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dm.constants.BeanConstants;
import com.dm.core.AbstractService;
import com.dm.core.IMongoRepository;
import com.dm.dao.ContainerRepository;
import com.dm.model.Container;


/**
 * The Class ContainerService.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.CONTAINER_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.CONTAINER_SVC)
public class ContainerService extends AbstractService<Container> {

	/** The container repo. */
	@Autowired
	@Qualifier(BeanConstants.CONTAINER_REPO)
	private ContainerRepository containerRepo;


	/* (non-Javadoc)
	 * @see com.dm.core.AbstractService#primaryDao()
	 */
	@Override
	public IMongoRepository<Container> primaryDao() {
		return containerRepo;
	}

}