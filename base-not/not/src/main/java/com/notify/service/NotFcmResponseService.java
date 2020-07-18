/**
 * 
 */
package com.notify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notify.constants.QualifierConstants;
import com.notify.core.AbstractService;
import com.notify.core.GenericRepository;
import com.notify.dao.NotFcmResponseRepository;
import com.notify.model.NotFcmResponse;

/**
 * @author mary.jane
 *
 */
@Transactional
@Service(QualifierConstants.NOT_FCM_RESPONSE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_FCM_RESPONSE_SVC)
public class NotFcmResponseService extends AbstractService<NotFcmResponse> {
	
	@Autowired
	private NotFcmResponseRepository notFcmResponseDao;

	@Override
	public GenericRepository<NotFcmResponse> primaryDao() {
		return notFcmResponseDao;
	}

}
