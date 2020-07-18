/**
 * 
 */
package com.dm.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.core.AbstractService;
import com.dm.core.GenericRepository;
import com.dm.dao.FileDocumentRepository;
import com.dm.dao.FileHistoryRepository;
import com.dm.dao.FileStorageRepository;
import com.dm.exception.FileStorageException;
import com.dm.model.FileMetadata;
import com.dm.model.FileHistory;
import com.dm.model.FileStorage;
import com.util.BaseUtil;
import com.util.model.IQfCriteria;

/**
 * @author mary.jane
 *
 */
@Service
public class FileStorageService extends AbstractService<FileStorage> {

	@Autowired
    private FileDocumentRepository fileDocumentRepository;
	
	@Autowired
	private FileStorageRepository fileStorageDao;
	
	@Autowired
	private FileHistoryRepository fileHistoryRepository;
	
	@Override
	public GenericRepository<FileStorage> primaryDao() {
		return fileStorageDao;
	}

	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}

    public FileMetadata storeFile(FileMetadata fileMetadata) {

        // Check if the file's name contains invalid characters
        if(fileMetadata.getFilename().contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileMetadata.getFilename());
        }

        return fileDocumentRepository.save(fileMetadata);
    }
    
    public FileMetadata saveFile(FileMetadata fd) {
    	FileStorage fs = new FileStorage();
    	fs.setFilename(fd.getFilename());
    	fs.setContent(fd.getContent());
    	fs.setContentType(fd.getContentType());
    	fs.setLength(fd.getLength());
    	fs.setUploadDate(fd.getUploadDate());
    	fs.setVersion(fd.getVersion());
    	fs = fileStorageDao.saveAndFlush(fs);
    	fd.setFilesId(fs.getFilesId());
    	fd = fileDocumentRepository.saveAndFlush(fd);
    	FileHistory fh = new FileHistory();
    	fh.setFilesId(fd.getFilesId());
    	fh.setDocMgtId(fd.getId());
    	fh.setVersion(fd.getVersion());
    	fileHistoryRepository.saveAndFlush(fh);
    	return fd;
    }
    
    public FileMetadata findByDocMgtId(String id) {
    	return fileDocumentRepository.findOne(id);
    }

    public FileMetadata getFile(String fileId) {
        return fileDocumentRepository.findOne(fileId);
    }
    
    public byte[] findDocumentById(String filesId) {
    	FileStorage fs = fileStorageDao.findOne(filesId);
    	if(!BaseUtil.isObjNull(fs)) {
    		return fs.getContent();
    	}
    	return null;
    }
    

}
