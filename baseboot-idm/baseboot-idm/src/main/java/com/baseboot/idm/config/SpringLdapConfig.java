/**
 *
 */
package com.baseboot.idm.config;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.ldap.IdmUserDao;
import com.baseboot.idm.ldap.IdmUserDaoImpl;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.util.CryptoUtil;


/**
 * @author mary.jane
 *
 */
// @Configuration
public class SpringLdapConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLdapConfig.class);

	@Autowired
	MessageSource messageSource;

	String ldapUrl;

	String ldapUname;

	String ldapPword;

	String ldapBase;

	String ldapPwdSalt;

	String ldapBaseGrp;

	String skey;


	@Bean
	public LdapContextSource ldapContextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapUrl);
		contextSource.setBase(ldapBase);
		contextSource.setUserDn(ldapUname);
		contextSource.setPassword(CryptoUtil.decrypt(ldapPword, skey));
		contextSource.setPooled(false);
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
		ldapUrl = messageSource.getMessage(ConfigConstants.LDAP_CONF_URL, null, Locale.getDefault());
		ldapUname = messageSource.getMessage(ConfigConstants.LDAP_CONF_UNAME, null, Locale.getDefault());
		ldapPword = messageSource.getMessage(ConfigConstants.LDAP_CONF_PWORD, null, Locale.getDefault());
		ldapBase = messageSource.getMessage(ConfigConstants.LDAP_CONF_BASEDN, null, Locale.getDefault());
		ldapPwdSalt = messageSource.getMessage(ConfigConstants.LDAP_CONF_PWORD_SALT, null, Locale.getDefault());
		ldapBaseGrp = messageSource.getMessage(ConfigConstants.LDAP_CONF_BASEDN_GROUP, null, Locale.getDefault());
		skey = messageSource.getMessage(ConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault());

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
		LOGGER.info(sb.toString());
	}

}
