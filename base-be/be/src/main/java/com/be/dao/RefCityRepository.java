package com.be.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefCity;


/**
 * @author Mary Jane Buenaventura
 * @since 25 Oct 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefCity.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_CITY_DAO)
public interface RefCityRepository extends GenericRepository<RefCity> {

	@Query("select u from RefCity u where u.cityCd = :cityCd ")
	RefCity findByCityCode(@Param("cityCd") String cityCd);


	@Query("select u from RefCity u where u.cityDesc = :cityDesc")
	RefCity findByCityDesc(@Param("cityDesc") String cityDesc);


	@Query("select u from RefCity u where u.cityCd = :cityCd and u.state.stateCd = :stateCd ")
	RefCity findByCityStateCode(@Param("cityCd") String cityCd, @Param("stateCd") String stateCd);

}