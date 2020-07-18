package com.idm.ldap;


import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.util.StringUtils;

import com.idm.config.ConfigConstants;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.SSHA;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class IdmUserDaoImpl implements IdmUserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserDaoImpl.class);

	private static final String PWORD = "userPassword";

	private static final String OBJ_CLASS = "objectClass";

	private static final String PERSON = "person";

	@Autowired
	private LdapTemplate ldapTemplate;

	@Value("${" + ConfigConstants.LDAP_CONF_PWORD_SALT + "}")
	private String pwdSalt;

	@Value("${" + ConfigConstants.LDAP_CONF_BASEDN_GROUP + "}")
	private String groupDn;


	@Override
	public IdmUserLdap searchEntry(String userName) {
		LOGGER.debug("executing {searchEntry}");
		if (!StringUtils.hasText(userName)) {
			return null;
		}
		LdapQuery query = query().searchScope(SearchScope.SUBTREE).timeLimit(5000).countLimit(1).where(OBJ_CLASS)
				.is(PERSON).and("uid").is(userName.trim());

		List<IdmUserLdap> list = ldapTemplate.search(query, new UserAttributesMapper());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public boolean authenticate(IdmUserLdap ldapEntryBean) {
		LOGGER.debug("executing {authenticate}");
		if (ldapEntryBean == null) {
			return false;
		}

		if (!StringUtils.hasText(ldapEntryBean.getUserName())
				|| !StringUtils.hasText(ldapEntryBean.getUserPassword())) {
			return false;
		}

		IdmUserLdap user = searchEntry(ldapEntryBean.getUserName());
		LOGGER.debug("Entered Pword: {}", ldapEntryBean.getUserPassword());
		LOGGER.debug("Current Pword: {}", user.getUserPassword());
		boolean isAuthenticate = false;
		if (BaseUtil.isEquals(user.getUserPassword(), ldapEntryBean.getUserPassword())) {
			isAuthenticate = true;
		}

		return isAuthenticate;
	}


	@Override
	public boolean changePassword(IdmUserLdap ldapEntryBean, boolean isDirectReplacePwd) {
		LOGGER.debug("executing {changePassword}");
		if (BaseUtil.isObjNull(ldapEntryBean)) {
			return false;
		}
		if (!StringUtils.hasText(ldapEntryBean.getUserName())
				|| !StringUtils.hasText(ldapEntryBean.getUserPassword())) {
			return false;
		}

		try {
			String password = null;
			if (isDirectReplacePwd) {
				password = ldapEntryBean.getUserPassword();
			} else {
				password = SSHA.getLDAPSSHAHash(ldapEntryBean.getUserPassword(), pwdSalt);
			}

			ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					new BasicAttribute(PWORD, password));
			ldapTemplate.modifyAttributes("uid=" + ldapEntryBean.getUserName() + query().base(),
					new ModificationItem[] { item });
			LOGGER.debug("UPDATED PASSWORD BY USERNAME [USERNAME :[{}] ] [PASSWORD :[{}] ]",
					ldapEntryBean.getUserName(), ldapEntryBean.getUserPassword());
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			return false;
		}
	}


	@Override
	public boolean updateMail(IdmUserLdap ldapEntryBean) {
		LOGGER.debug("executing {updateMail}");
		if (BaseUtil.isObjNull(ldapEntryBean)) {
			return false;
		}

		try {
			ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					new BasicAttribute("mail", ldapEntryBean.getEmail()));
			ldapTemplate.modifyAttributes("uid=" + ldapEntryBean.getUserName() + query().base(),
					new ModificationItem[] { item });
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return false;
		}
	}


	@Override
	public boolean createEntry(IdmUserLdap ldapEntryBean) {
		LOGGER.info("executing {createEntry}");
		if (BaseUtil.isObjNull(ldapEntryBean)) {
			return false;
		}

		if (!StringUtils.hasText(ldapEntryBean.getUserName())
				|| !StringUtils.hasText(ldapEntryBean.getUserPassword())) {
			return false;
		}

		try {
			String pword = SSHA.getLDAPSSHAHash(ldapEntryBean.getUserPassword(), pwdSalt);

			Attribute objectClass = new BasicAttribute(OBJ_CLASS);
			objectClass.add(PERSON);
			objectClass.add("top");
			objectClass.add("inetOrgPerson");

			Attributes userAttributes = new BasicAttributes();
			userAttributes.put(objectClass);
			userAttributes.put("cn", ldapEntryBean.getUserName());
			userAttributes.put("sn", ldapEntryBean.getUserName());
			userAttributes.put("mail",
					StringUtils.hasText(ldapEntryBean.getEmail()) ? ldapEntryBean.getEmail() : "");
			userAttributes.put(PWORD, pword);
			ldapTemplate.bind(bindDN(ldapEntryBean.getUserName()), null, userAttributes);

			IdmUserLdap idmUserLdap = searchEntry(ldapEntryBean.getUserName());
			if (BaseUtil.isObjNull(idmUserLdap)) {
				throw new IdmException(IdmErrorCodeEnum.E400IDM122);
			}

			LOGGER.info("LDAP created: {}", idmUserLdap.getUserName());

			return true;
		} catch (NameAlreadyBoundException nabe) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { ldapEntryBean.getUserName() });
		} catch (Exception e) {
			LOGGER.error("{}", e);
			return false;
		}
	}


	@Override
	public boolean destoryEntry(String userName) {
		if (!StringUtils.hasText(userName)) {
			return false;
		}

		try {
			ldapTemplate.unbind(bindDN(userName));
		} catch (InvalidNameException e) {
			LOGGER.error("InvalidNameException: ", e);
			return false;
		}
		return true;
	}


	@Override
	public boolean assignUserToGroup(String username, String groupName) {
		LOGGER.debug("executing {assignUserToGroup}");
		if (!StringUtils.hasText(username) || !StringUtils.hasText(groupName)) {
			return false;
		}

		try {
			ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					new BasicAttribute("member", "uid=" + username + query().base()));
			ldapTemplate.modifyAttributes("cn=" + groupName + "," + groupDn, new ModificationItem[] { item });
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return false;
		}
	}


	@Override
	public List<String> getAssignedGroupsByUser(String userName) {
		LdapQuery query = query().searchScope(SearchScope.SUBTREE).timeLimit(5000).countLimit(1).where(OBJ_CLASS)
				.is(PERSON).and("uid").is(userName.trim());
		return ldapTemplate.search(query, new SingleAttributesMapper());
	}


	private static Name bindDN(String dn) throws InvalidNameException {
		try {
			return new LdapName("uid=" + dn + query().base());
		} catch (InvalidNameException e) {
			throw new IdmException(e.getMessage());
		}
	}


	private class UserAttributesMapper implements AttributesMapper<IdmUserLdap> {

		@Override
		public IdmUserLdap mapFromAttributes(Attributes attributes) throws NamingException {
			if (attributes == null) {
				return null;
			}
			IdmUserLdap user = new IdmUserLdap();
			if (attributes.get(PWORD) != null) {
				String userPassword = new String((byte[]) attributes.get(PWORD).get());
				user.setUserPassword(userPassword);
			}
			if (attributes.get("uid") != null) {
				user.setUserName(attributes.get("uid").get().toString());
			}
			if (attributes.get("sn") != null) {
				user.setSn(attributes.get("sn").get().toString());
			}
			if (attributes.get("mail") != null) {
				user.setEmail(attributes.get("mail").get().toString());
			}
			if (attributes.get("cn") != null) {
				user.setCn(attributes.get("cn").get().toString());
			}
			return user;
		}
	}

	private class SingleAttributesMapper implements AttributesMapper<String> {

		@Override
		public String mapFromAttributes(Attributes attrs) throws NamingException {
			Attribute cn = attrs.get("cn");
			return cn.toString();
		}
	}
}
