/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.config;


import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baseboot.dm.sdk.util.BaseUtil;
import com.baseboot.dm.svc.broker.DocumentsProcessor;
import com.baseboot.dm.util.BeanConstants;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.util.CryptoUtil;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableMongoRepositories(basePackages = { ConfigConstants.BASE_PACKAGE_REPO })
public class SpringMongoConfig extends AbstractMongoConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringMongoConfig.class);

	@Value("${" + ConfigConstants.MONGO_CONF_DB + "}")
	private String mongoDbName;

	@Value("${" + ConfigConstants.MONGO_CONF_HOST + "}")
	private String mongoHost;

	@Value("${" + ConfigConstants.MONGO_CONF_PORT + "}")
	private String mongoPort;

	@Value("${" + ConfigConstants.MONGO_CONF_UNAME + "}")
	private String mongoUname;

	@Value("${" + ConfigConstants.MONGO_CONF_PWORD + "}")
	private String mongoPword;

	@Value("${" + ConfigConstants.SVC_IDM_SKEY + "}")
	private String skey;

	@Autowired
	MessageSource messageSource;


	@Override
	protected String getDatabaseName() {
		return mongoDbName;
	}


	Mongo mongo;


	@Override
	public Mongo mongo() throws Exception {
		if (!BaseUtil.isObjNull(mongo)) {
			return mongo;
		}

		ArrayList<ServerAddress> addr = new ArrayList<ServerAddress>();
		String[] host = mongoHost.split(",");
		String[] port = mongoPort.split(",");

		StringBuffer sb = new StringBuffer();
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
		LOGGER.info(sb.toString());

		MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
		optionsBuilder.connectTimeout(50);
		optionsBuilder.writeConcern(WriteConcern.ACKNOWLEDGED);
		optionsBuilder.readPreference(ReadPreference.primary());

		if (!BaseUtil.isObjNull(mongoUname)) {
			MongoCredential credential = MongoCredential.createCredential(mongoUname, mongoDbName,
					CryptoUtil.decrypt(mongoPword, skey).toCharArray());
			mongo = new MongoClient(addr, Arrays.asList(credential), optionsBuilder.build());
		} else {
			mongo = new MongoClient(addr, optionsBuilder.build());
		}
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}


	@Override
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}


	@Override
	public MongoDbFactory mongoDbFactory() throws Exception {
		return super.mongoDbFactory();
	}


	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		MongoMappingContext mappingContext = new MongoMappingContext();
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
		mongoConverter.setCustomConversions(customConversions());
		return mongoConverter;
	}


	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}


	@Bean
	@Qualifier(BeanConstants.DOCUMENT_PROCESSOR)
	public DocumentsProcessor docProcessor() {
		return new DocumentsProcessor();
	}


	@PreDestroy
	public void destroy() {
		LOGGER.info("Closing MongoDB Connection");
		mongo.close();
	}

}