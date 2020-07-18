/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.svc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baseboot.dm.core.AbstractService;
import com.baseboot.dm.core.IMongoRepository;
import com.baseboot.dm.dao.ContainerRepository;
import com.baseboot.dm.model.Container;
import com.baseboot.dm.util.BeanConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.CONTAINER_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.CONTAINER_SVC)
public class ContainerService extends AbstractService<Container, Integer> {

	@Autowired
	@Qualifier(BeanConstants.CONTAINER_REPO)
	private ContainerRepository containerRepo;


	@Override
	public IMongoRepository<Container> primaryDao() {
		return containerRepo;
	}

}