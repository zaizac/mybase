/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.svc;


import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.baseboot.dm.util.BeanConstants;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.STORAGE_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.STORAGE_SVC)
public class StorageService {

	@Autowired
	MongoTemplate mongoTemplate;


	public String save(String bucket, InputStream inputStream, String contentType, String filename) {
		GridFSInputFile input = getGridFs(bucket).createFile(inputStream, filename, true);
		input.setContentType(contentType);
		input.save();
		return input.getId().toString();
	}


	public GridFSDBFile get(String bucket, String id) {
		return getGridFs(bucket).findOne(new ObjectId(id));
	}


	public GridFSDBFile getByFilename(String bucket, String filename) {
		return getGridFs(bucket).findOne(filename);
	}


	public GridFS getGridFs(String bucket) {
		return new GridFS((DB) mongoTemplate.getDb(), bucket);
	}

}