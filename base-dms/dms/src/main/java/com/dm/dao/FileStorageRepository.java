/**
 * 
 */
package com.dm.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.dm.constants.QualifierConstants;
import com.dm.core.GenericRepository;
import com.dm.model.FileStorage;

/**
 * @author mary.jane
 *
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = FileStorage.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.DM_FILE_STORAGE_DAO)
public interface FileStorageRepository  extends GenericRepository<FileStorage> {// extends JpaRepository<FileStorage, String> {

}
