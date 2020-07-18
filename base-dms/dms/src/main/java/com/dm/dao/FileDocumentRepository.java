/**
 * 
 */
package com.dm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dm.model.FileMetadata;

/**
 * @author mary.jane
 *
 */
@Repository
public interface FileDocumentRepository extends JpaRepository<FileMetadata, String> {

}