/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.service;


import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.dm.constants.BeanConstants;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


/**
 * The Class StorageService.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.STORAGE_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.STORAGE_SVC)
public class StorageService {

	/** The mongo template. */
	@Autowired
	MongoTemplate mongoTemplate;


	/**
	 * Save.
	 *
	 * @param bucket the bucket
	 * @param inputStream the input stream
	 * @param contentType the content type
	 * @param filename the filename
	 * @return the string
	 */
	public String save(String bucket, InputStream inputStream, String contentType, String filename) {
		GridFSInputFile input = getGridFs(bucket).createFile(inputStream, filename, true);
		input.setContentType(contentType);
		input.save();
		return input.getId().toString();
	}


	/**
	 * Gets the.
	 *
	 * @param bucket the bucket
	 * @param id the id
	 * @return the grid FSDB file
	 */
	public GridFSDBFile get(String bucket, String id) {
		return getGridFs(bucket).findOne(new ObjectId(id));
	}


	/**
	 * Gets the by filename.
	 *
	 * @param bucket the bucket
	 * @param filename the filename
	 * @return the by filename
	 */
	public GridFSDBFile getByFilename(String bucket, String filename) {
		return getGridFs(bucket).findOne(filename);
	}


	/**
	 * Gets the grid fs.
	 *
	 * @param bucket the bucket
	 * @return the grid fs
	 */
	public GridFS getGridFs(String bucket) {
		return new GridFS(mongoTemplate.getDb(), bucket);
	}

}