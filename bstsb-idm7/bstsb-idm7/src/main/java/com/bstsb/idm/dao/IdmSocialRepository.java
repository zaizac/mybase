/**
 *
 */
package com.bstsb.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmSocial;


/**
 * @author mary.jane
 * @since 06 Jul 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmSocial.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_SOCIAL_REPOSITORY)
public interface IdmSocialRepository extends GenericRepository<IdmSocial> {

	@Query("select u from IdmSocial u where u.email = :email ")
	public IdmSocial findByEmail(@Param("email") String email);


	@Query("select u from IdmSocial u where u.userId = :userId and u.provider = :provider and u.providerUserId = :providerUserId ")
	public IdmSocial findSocialUserByIds(@Param("userId") String userId, @Param("provider") String provider,
			@Param("providerUserId") String providerUserId);


	@Query("select u from IdmSocial u where u.userId = :userId")
	public IdmSocial findSocialUserByUserId(@Param("userId") String userId);


	@Query("select u from IdmSocial u where u.provider = :provider and u.providerUserId = :providerUserId")
	public IdmSocial findSocialUserByProviderId(@Param("provider") String provider,
			@Param("providerUserId") String providerUserId);

}
