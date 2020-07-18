package com.idm.util;


import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.idm.ldap.IdmUserLdap;
import com.util.SSHA;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class LdapClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LdapClientService.class);

	private Hashtable<String, String> env;

	private static final String SALT = "ldap";

	private static final String MEMBER = "member";


	public LdapClientService() {
		// DO NOTHING
	}


	/**
	 *
	 * @param connName
	 * @param connPword
	 * @param url
	 */
	public LdapClientService(String connName, String connPword, String url) {
		env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		// To get rid of the PartialResultException when using Active
		// Directory
		env.put(Context.REFERRAL, "follow");

		// Needed for the Bind (User Authorized to Query the LDAP server)
		env.put(Context.SECURITY_AUTHENTICATION, "Simple");
		env.put(Context.SECURITY_PRINCIPAL, connName);
		env.put(Context.SECURITY_CREDENTIALS, connPword);
		env.put(Context.PROVIDER_URL, url);
	}


	public boolean authenticate(String uname, String pword) {
		boolean isValid = false;
		DirContext dirCtx = null;
		NamingEnumeration<SearchResult> results = null;

		try {
			String password = SSHA.getLDAPSSHAHash(pword, SALT);
			LOGGER.debug("[UserPassword Hashed From LDAP: {}", password);

			dirCtx = new InitialDirContext(env);

			SearchControls controls = new SearchControls();

			// Search entire subtree
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Set the max number of entries to be returned as a result of
			// the search
			controls.setCountLimit(1);

			// Set the time limit of this searchcontrols in ms
			controls.setTimeLimit(5000);

			String searchString = "(&(objectClass=person)(uid=" + uname + "))";
			results = dirCtx.search("ou=Users,ou=system", searchString, controls);

			if (results != null && results.hasMore()) {
				SearchResult result = results.next();
				Attributes attrs = result.getAttributes();

				Attribute dnAttr = attrs.get("mail");
				String dn = (String) dnAttr.get();
				LOGGER.debug("getID = {}", dnAttr.getID());
				LOGGER.debug("Value = {}", dn);

				Attribute pwAttr = attrs.get("userPassword");

				byte[] pWord = (byte[]) pwAttr.get();
				String pd = new String(pWord);
				LOGGER.debug("[UserPassword Hashed From LDAP : {}", pd);

				if ((SSHA.matchSHAHash(pd, pword) && password.equals(pd)) || password.equals(pd)) {
					isValid = true;
				}
			}

		} catch (NamingException e) {
			LOGGER.error("NamingException", e);
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (final Exception e) {
					LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				}
			}

			if (dirCtx != null) {
				try {
					dirCtx.close();
				} catch (final Exception e) {
					LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				}
			}

			LOGGER.debug("Username : {} - Password: {}", uname, pword);
			LOGGER.debug("Authenticate : {}", isValid);
		}
		return isValid;
	}


	public IdmUserLdap searchEntry(final String userName) {
		if (!StringUtils.hasText(userName)) {
			return null;
		}

		NamingEnumeration<SearchResult> results = null;
		SearchControls searcCon = null;
		try {
			searcCon = new SearchControls();
			searcCon.setSearchScope(SearchControls.SUBTREE_SCOPE);

			StringBuilder searchQuery = new StringBuilder();
			searchQuery.append("ou=Users,ou=system");

			String searchString = "(&(objectClass=person)(uid=" + userName + "))";

			DirContext dirCtx = new InitialDirContext(env);
			LOGGER.debug("searchQuery: {}", searchQuery);
			results = dirCtx.search(searchQuery.toString(), searchString, searcCon);
			LOGGER.debug("results: {}", results.hasMore());

			if (!results.hasMore()) {
				return null;
			}

			IdmUserLdap bean = new IdmUserLdap();

			while (results.hasMore()) {
				SearchResult res = results.next();
				Attributes attrs = res.getAttributes();

				Attribute mailAttr = attrs.get("mail");
				String mail = (String) mailAttr.get();
				bean.setEmail(mail);

				Attribute uidAttr = attrs.get("uid");
				String uid = (String) uidAttr.get();
				bean.setUserName(uid);

				Attribute userPasswordAttr = attrs.get("userPassword");
				byte[] pWord = (byte[]) userPasswordAttr.get();
				String pd = new String(pWord);
				bean.setUserPassword(pd);

				Attribute cnAttr = attrs.get("cn");
				String cn = (String) cnAttr.get();
				bean.setCn(cn);

				Attribute snAttr = attrs.get("sn");
				String sn = (String) snAttr.get();
				bean.setSn(sn);
			}
			return bean;

		} catch (NamingException e) {
			LOGGER.error("searchEntry Exception", e);
			return null;
		}
	}


	public void addGroup(String name, String description) throws NamingException {

		// Create a container set of attributes
		Attributes container = new BasicAttributes();

		// Create the objectclass to add
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("top");
		objClasses.add("groupOfUniqueNames");

		// Assign the name and description to the group
		Attribute cn = new BasicAttribute("cn", name);
		Attribute desc = new BasicAttribute("description", description);

		// Add these to the container
		container.put(objClasses);
		container.put(cn);
		container.put(desc);
		DirContext dirCtx = new InitialDirContext(env);

		// Create the entry
		dirCtx.createSubcontext(getGroupDN(name), container);
	}


	public void assignUser(String username, String groupName) throws NamingException {
		try {
			ModificationItem[] mods = new ModificationItem[1];

			Attribute mod = new BasicAttribute(MEMBER, getUserDN(username));
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
			DirContext dirCtx = new InitialDirContext(env);
			dirCtx.modifyAttributes(getGroupDN(groupName), mods);
		} catch (AttributeInUseException e) {
			// If user is already added, ignore exception
		}
	}


	private String getUserDN(String username) {
		return new StringBuilder().append("uid=").append(username).append(",").append("ou=users,ou=system")
				.toString();
	}


	private String getGroupDN(String name) {
		return new StringBuilder().append("cn=").append(name).append(",").append("ou=groups,ou=system").toString();
	}


	public boolean userInGroup(String username, String groupName) throws NamingException {

		// Set up attributes to search for
		String[] searchAttributes = new String[1];
		searchAttributes[0] = MEMBER;
		DirContext dirCtx = new InitialDirContext(env);
		Attributes attributes = dirCtx.getAttributes(getGroupDN(groupName), searchAttributes);
		if (attributes != null) {
			Attribute memberAtts = attributes.get(MEMBER);
			if (memberAtts != null) {
				for (NamingEnumeration<?> vals = memberAtts.getAll(); vals.hasMoreElements();) {
					if (username.equalsIgnoreCase(getUserUID((String) vals.nextElement()))) {
						return true;
					}
				}
			}
		}

		return false;
	}


	public List<String> getGroups(String username) throws NamingException {
		List<String> groups = new LinkedList<>();

		// Set up criteria to search on
		String filter = new StringBuilder().append("(&").append("(member=").append(getUserDN(username)).append(")")
				.append(")").toString();

		// Set up search constraints
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		DirContext dirCtx = new InitialDirContext(env);
		NamingEnumeration<SearchResult> results = dirCtx.search("ou=groups,ou=system", filter, cons);

		while (results.hasMore()) {
			SearchResult result = results.next();
			groups.add(getGroupCN(result.getName()));
		}

		return groups;
	}


	private String getUserUID(String userDN) {
		int start = userDN.indexOf('=');
		int end = userDN.indexOf(',');

		if (end == -1) {
			end = userDN.length();
		}

		return userDN.substring(start + 1, end);
	}


	private String getGroupCN(String groupDN) {
		LOGGER.debug("groupDN: {}", groupDN);
		int start = groupDN.indexOf('=');
		int end = groupDN.indexOf(',');

		if (end == -1) {
			end = groupDN.length();
		}

		return groupDN.substring(start + 1, end);
	}

}