/**
 * 
 */
package com.notify.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.notify.constants.QualifierConstants;
import com.notify.core.GenericRepository;
import com.notify.model.NotFcmResponse;

/**
 * @author mary.jane
 *
 */
@Repository
@RepositoryDefinition(domainClass = NotFcmResponse.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_FCM_RESPONSE_DAO)
public interface NotFcmResponseRepository  extends GenericRepository<NotFcmResponse> {

}
