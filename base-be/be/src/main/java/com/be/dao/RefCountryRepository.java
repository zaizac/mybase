package com.be.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefCountry;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefCountry.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_COUNTRY_DAO)
public interface RefCountryRepository extends GenericRepository<RefCountry> {

	@Query("select u from RefCountry u where u.cntryCd = :cntryCd ")
	RefCountry findByCountryCode(@Param("cntryCd") String cntryCd);


	@Query("select u from RefCountry u where u.currency = :currency ")
	RefCountry findByCurrencyCode(@Param("currency") String currency);


	@Query("select u from RefCountry u where u.cntryInd = :cntryInd ")
	List<RefCountry> findByCountryInd(@Param("cntryInd") boolean cntryInd);
}