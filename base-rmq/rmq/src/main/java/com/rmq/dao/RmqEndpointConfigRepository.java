/**
 * 
 */
package com.rmq.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmq.constants.QualifierConstants;
import com.rmq.model.RmqEndpointConfig;

/**
 * @author mary.jane
 *
 */
@Repository
@RepositoryDefinition(domainClass = RmqEndpointConfig.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.ENDPOINT_CONFIG_DAO)
public interface RmqEndpointConfigRepository extends GenericRepository<RmqEndpointConfig> {

	public RmqEndpointConfig findByModuleAndAction(@Param("module") String module, @Param("action") String action);

}
