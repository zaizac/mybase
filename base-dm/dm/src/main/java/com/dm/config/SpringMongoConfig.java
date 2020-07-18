/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.config;


import java.util.ArrayList;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.dm.constants.BeanConstants;
import com.dm.constants.ConfigConstants;
import com.dm.service.broker.DocumentsProcessor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * The Class SpringMongoConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableMongoRepositories(basePackages = { ConfigConstants.BASE_PACKAGE_REPO })
public class SpringMongoConfig extends AbstractMongoConfiguration {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringMongoConfig.class);

	/** The mongo db name. */
	@Value("${" + ConfigConstants.MONGO_CONF_DB + "}")
	private String mongoDbName;

	/** The mongo host. */
	@Value("${" + ConfigConstants.MONGO_CONF_HOST + "}")
	private String mongoHost;

	/** The mongo port. */
	@Value("${" + ConfigConstants.MONGO_CONF_PORT + "}")
	private String mongoPort;

	/** The mongo replica set name. */
	@Value("${" + ConfigConstants.MONGO_CONF_SET_NAME + "}")
	private String mongoSetName;

	/** The mongo uname. */
	@Value("${" + ConfigConstants.MONGO_CONF_UNAME + "}")
	private String mongoUname;

	/** The mongo pword. */
	@Value("${" + ConfigConstants.MONGO_CONF_PWORD + "}")
	private String mongoPword;

	/** The skey. */
	@Value("${" + BaseConfigConstants.SVC_IDM_SKEY + "}")
	private String skey;

	/** The message source. */
	@Autowired
	MessageSource messageSource;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#
	 * getDatabaseName()
	 */
	@Override
	protected String getDatabaseName() {
		return mongoDbName;
	}


	/** The mongo. */
	Mongo mongo;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.config.AbstractMongoConfiguration#mongo
	 * ()
	 */
	@Override
	public Mongo mongo() throws Exception {
		if (!BaseUtil.isObjNull(mongo)) {
			return mongo;
		}

		ArrayList<ServerAddress> addr = new ArrayList<>();
		String[] host = mongoHost.split(",");
		String[] port = mongoPort.split(",");

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("MONGO Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		for (int i = 0; i < host.length; i++) {
			sb.append("Host : " + host[i] + ":" + port[i] + BaseConstants.NEW_LINE);
			addr.add(new ServerAddress(host[i], Integer.parseInt(port[i])));
		}
		sb.append("DB Name : " + mongoDbName + BaseConstants.NEW_LINE);
		sb.append("Username : " + mongoUname + BaseConstants.NEW_LINE);
		sb.append("Password : " + mongoPword);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.debug("{}", sb);

		MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
		optionsBuilder.connectTimeout(50);
		optionsBuilder.writeConcern(WriteConcern.ACKNOWLEDGED);
		optionsBuilder.readPreference(ReadPreference.primary());
		if (!BaseUtil.isObjNull(mongoSetName)) {
			optionsBuilder.requiredReplicaSetName(mongoSetName);
		}
		if (!BaseUtil.isObjNull(mongoUname)) {
			MongoCredential credential = MongoCredential.createCredential(mongoUname, mongoDbName,
					CryptoUtil.decrypt(mongoPword, skey).toCharArray());
			mongo = new MongoClient(addr, credential, optionsBuilder.build());
		} else {
			mongo = new MongoClient(addr, optionsBuilder.build());
		}
		return mongo;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#
	 * mongoTemplate()
	 */
	@Override
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#
	 * mappingMongoConverter()
	 */
	@Override
	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		MongoMappingContext mappingContext = new MongoMappingContext();
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
		mongoConverter.setCustomConversions(customConversions());
		return mongoConverter;
	}


	/**
	 * Grid fs template.
	 *
	 * @return the grid fs template
	 * @throws Exception
	 *              the exception
	 */
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}


	/**
	 * Doc processor.
	 *
	 * @return the documents processor
	 */
	@Bean
	@Qualifier(BeanConstants.DOCUMENT_PROCESSOR)
	public DocumentsProcessor docProcessor() {
		return new DocumentsProcessor();
	}


	/**
	 * Destroy.
	 */
	@PreDestroy
	public void destroy() {
		LOGGER.info("Closing MongoDB Connection");
		mongo.close();
	}

}