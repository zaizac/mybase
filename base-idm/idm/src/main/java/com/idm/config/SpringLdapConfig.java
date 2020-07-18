package com.idm.config;


import java.util.Hashtable;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;

import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserDaoImpl;
import com.util.CryptoUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
public class SpringLdapConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLdapConfig.class);

	@Autowired
	MessageSource messageSource;

	@Value("${" + ConfigConstants.LDAP_CONF_URL + "}")
	String ldapUrl;

	@Value("${" + ConfigConstants.LDAP_CONF_UNAME + "}")
	String ldapUname;

	@Value("${" + ConfigConstants.LDAP_CONF_PWORD + "}")
	String ldapPword;

	@Value("${" + ConfigConstants.LDAP_CONF_BASEDN + "}")
	String ldapBase;

	@Value("${" + ConfigConstants.LDAP_CONF_PWORD_SALT + "}")
	String ldapPwdSalt;

	@Value("${" + ConfigConstants.LDAP_CONF_BASEDN_GROUP + "}")
	String ldapBaseGrp;

	@Value("${" + ConfigConstants.LDAP_CONF_CONNECTION_TIMEOUT + "}")
	String ldapConnTimeout;

	@Value("${" + ConfigConstants.LDAP_CONF_READ_TIMEOUT + "}")
	String ldapReadTimeout;

	String skey;

	private static final String CONNECT_TIMEOUT_ENV = "com.sun.jndi.ldap.connect.timeout";

	private static final String READ_TIMEOUT_ENV = "com.sun.jndi.ldap.read.timeout";


	@Bean
	public LdapContextSource ldapContextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapUrl);
		contextSource.setBase(ldapBase);
		contextSource.setUserDn(ldapUname);
		contextSource.setPassword(CryptoUtil.decrypt(ldapPword, skey));
		contextSource.setPooled(false);
		Hashtable<String, Object> baseEnv = new Hashtable<>();
		baseEnv.put(CONNECT_TIMEOUT_ENV, ldapConnTimeout);
		baseEnv.put(READ_TIMEOUT_ENV, ldapReadTimeout);
		contextSource.setBaseEnvironmentProperties(baseEnv);
		contextSource.afterPropertiesSet();
		return contextSource;
	}


	@Bean
	public ContextSource poolingLdapContextSource() {
		PoolingContextSource poolingContextSource = new PoolingContextSource();
		poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
		poolingContextSource.setContextSource(ldapContextSource());
		poolingContextSource.setTestOnBorrow(true);
		poolingContextSource.setTestWhileIdle(true);
		return new TransactionAwareContextSourceProxy(poolingContextSource);
	}


	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(ldapContextSource());
	}


	@Bean
	public IdmUserDao idmUserDao() {
		return new IdmUserDaoImpl();
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		skey = messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("LDAP Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("URL: " + ldapUrl + BaseConstants.NEW_LINE);
		sb.append("BaseDn: " + ldapBase + BaseConstants.NEW_LINE);
		sb.append("Username: " + ldapUname + BaseConstants.NEW_LINE);
		sb.append("Password: " + ldapPword + BaseConstants.NEW_LINE);
		sb.append("PasswordSalt: " + ldapPwdSalt + BaseConstants.NEW_LINE);
		sb.append("BaseDn Group: " + ldapBaseGrp);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info("{}", sb);
	}

}