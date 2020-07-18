/**
 * 
 */
package com.dm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dm.model.FileHistory;

/**
 * @author mary.jane
 *
 */
@Repository
public interface FileHistoryRepository extends JpaRepository<FileHistory, String> {

}
