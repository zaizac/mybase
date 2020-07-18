/**
 *
 */
package com.bstsb.idm.service;


import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmUserConnectionRepository;
import com.bstsb.idm.model.IdmUserConnection;


/**
 * @author mary.jane
 *
 */
@Transactional
@Service(QualifierConstants.IDM_USER_CONN_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_CONN_SERVICE)
public class IdmUserConnectionService extends AbstractService<IdmUserConnection> {

	@Autowired
	IdmUserConnectionRepository idmUserConnDao;


	@Override
	public GenericRepository<IdmUserConnection> primaryDao() {
		return idmUserConnDao;
	}


	public List<IdmUserConnection> findByUserIdAndProviderId(String userId, String providerId) {
		return idmUserConnDao.findByUserIdAndProviderId(userId, providerId);
	}


	public List<IdmUserConnection> findByProviderUserIdAndProviderId(String providerUserId, String providerId) {
		return idmUserConnDao.findByProviderUserIdAndProviderId(providerUserId, providerId);
	}


	public List<String> findUserByProviderIdAndProviderUserId(String providerId, String providerUserId) {
		return idmUserConnDao.findUserByProviderIdAndProviderUserId(providerId, providerUserId);
	}


	public List<String> findUsersByProviderIdAndProviderUserId(String providerId, Set<String> providerUserIds) {
		return idmUserConnDao.findUsersByProviderIdAndProviderUserId(providerId, providerUserIds);
	}


	public List<String> findUserByProviderIdAndProviderUserId(String providerId, Set<String> providerUserIds) {
		return idmUserConnDao.findUserByProviderIdAndProviderUserId(providerId, providerUserIds);
	}


	public IdmUserConnection removeConnections(String userId, String providerId) {
		return idmUserConnDao.removeConnections(userId, providerId);
	}


	public IdmUserConnection removeConnection(String userId, String providerId, String providerUserId) {
		return idmUserConnDao.removeConnection(userId, providerId, providerUserId);
	}


	public List<IdmUserConnection> findUserConnectionsByUserId(String userId) {
		return idmUserConnDao.findUserConnectionsByUserId(userId);
	}


	public List<IdmUserConnection> findUserConByUserIdProviderIdProviderUserId(String userId, String providerId,
			String providerUserId) {
		return idmUserConnDao.findUserConByUserIdProviderIdProviderUserId(userId, providerId, providerUserId);
	}


	public List<IdmUserConnection> findConnectionsToUsers(String userId, String providerId,
			Set<String> providerUserIds) {
		return idmUserConnDao.findConnectionsToUsers(userId, providerId, providerUserIds);
	}


	public IdmUserConnection createUserConnection(IdmUserConnection idc) {
		Integer rank = idmUserConnDao.getMaximumRank(idc.getUserConnectionPK().getUserId(),
				idc.getUserConnectionPK().getProviderId(), idc.getUserConnectionPK().getProviderUserId());
		idc.setRank(rank);
		return idmUserConnDao.saveAndFlush(idc);
	}


	public IdmUserConnection updateUserConnection(IdmUserConnection idc) {
		return idmUserConnDao.saveAndFlush(idc);
	}
}
