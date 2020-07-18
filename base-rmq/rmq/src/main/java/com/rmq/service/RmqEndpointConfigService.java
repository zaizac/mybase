/**
 * 
 */
package com.rmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmq.constants.QualifierConstants;
import com.rmq.dao.GenericRepository;
import com.rmq.dao.RmqEndpointConfigRepository;
import com.rmq.model.RmqEndpointConfig;

/**
 * @author mary.jane
 *
 */
@Transactional
@Service(QualifierConstants.ENDPOINT_CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.ENDPOINT_CONFIG_SVC)
public class RmqEndpointConfigService extends AbstractService<RmqEndpointConfig> {

	@Autowired
	RmqEndpointConfigRepository endpointConfigDao;
	
	@Override
	public GenericRepository<RmqEndpointConfig> primaryDao() {
		return endpointConfigDao;
	}

	public RmqEndpointConfig findByModuleAndAction(String module, String action) {
		return endpointConfigDao.findByModuleAndAction(module, action);
	}
	
}
