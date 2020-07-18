/**
 *
 */
package com.bstsb.idm.dao;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmUserConnection;


/**
 * @author mary.jane
 *
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserConnection.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_CONN_REPOSITORY)
public interface IdmUserConnectionRepository extends GenericRepository<IdmUserConnection> {

	@Query("select u from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId order by rank ")
	public List<IdmUserConnection> findByUserIdAndProviderId(@Param("userId") String userId,
			@Param("providerId") String providerId);


	@Query("select u from IdmUserConnection u where u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserId order by rank ")
	public List<IdmUserConnection> findByProviderUserIdAndProviderId(@Param("providerUserId") String providerUserId,
			@Param("providerId") String providerId);


	@Query("select u.userConnectionPK.userId from IdmUserConnection u where u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserId")
	public List<String> findUserByProviderIdAndProviderUserId(@Param("providerId") String providerId,
			@Param("providerUserId") String providerUserId);


	@Query("select u.userConnectionPK.userId from IdmUserConnection u where u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserIds")
	public List<String> findUsersByProviderIdAndProviderUserId(@Param("providerId") String providerId,
			@Param("providerUserIds") Set<String> providerUserIds);


	@Query("select u.userConnectionPK.userId from IdmUserConnection u where u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserIds")
	public List<String> findUserByProviderIdAndProviderUserId(@Param("providerId") String providerId,
			@Param("providerUserIds") Set<String> providerUserIds);


	@Modifying
	@Query("delete from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId")
	public IdmUserConnection removeConnections(@Param("userId") String userId, @Param("providerId") String providerId);


	@Modifying
	@Query("delete from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserId")
	public IdmUserConnection removeConnection(@Param("userId") String userId, @Param("providerId") String providerId,
			@Param("providerUserId") String providerUserId);


	@Query("select u from IdmUserConnection u where u.userConnectionPK.userId = :userId order by providerId,rank")
	public List<IdmUserConnection> findUserConnectionsByUserId(@Param("userId") String userId);


	@Query("select u from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserId order by providerId,rank")
	public List<IdmUserConnection> findUserConByUserIdProviderIdProviderUserId(@Param("userId") String userId,
			@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);


	@Query("select u from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId in :providerUserIds order by providerId,rank")
	public List<IdmUserConnection> findConnectionsToUsers(@Param("userId") String userId,
			@Param("providerId") String providerId, @Param("providerUserIds") Set<String> providerUserIds);


	@Query("select coalesce(max(u.rank) + 1, 1) from IdmUserConnection u where u.userConnectionPK.userId = :userId and u.userConnectionPK.providerId = :providerId and u.userConnectionPK.providerUserId = :providerUserId")
	public Integer getMaximumRank(@Param("userId") String userId, @Param("providerId") String providerId,
			@Param("providerUserId") String providerUserId);
}
