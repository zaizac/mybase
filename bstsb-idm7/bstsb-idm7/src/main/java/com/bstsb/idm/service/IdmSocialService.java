/**
 *
 */
package com.bstsb.idm.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmSocialRepository;
import com.bstsb.idm.model.IdmSocial;


/**
 * @author mary.jane
 * @since 06 Jul 2018
 */
@Transactional
@Service(QualifierConstants.IDM_SOCIAL_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_SOCIAL_SERVICE)
public class IdmSocialService extends AbstractService<IdmSocial> {

	@Autowired
	private IdmSocialRepository idmSocialDao;


	@Override
	public GenericRepository<IdmSocial> primaryDao() {
		return idmSocialDao;
	}


	public IdmSocial findSocialUserByIds(String userId, String provider, String providerUserId) {
		return idmSocialDao.findSocialUserByIds(userId, provider, providerUserId);
	}


	public IdmSocial findSocialUserByProviderId(String provider, String providerUserId) {
		return idmSocialDao.findSocialUserByProviderId(provider, providerUserId);
	}


	public IdmSocial findSocialUserByUserId(String userId) {
		return idmSocialDao.findSocialUserByUserId(userId);
	}
}
