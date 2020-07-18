/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.ldap;


import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Component
public interface IdmUserDao {

	IdmUserLdap searchEntry(final String userName);


	boolean authenticate(IdmUserLdap ldapEntryBean);


	boolean changePassword(IdmUserLdap ldapEntryBean, boolean isDirectReplacePwd);


	boolean updateMail(IdmUserLdap ldapEntryBean);


	boolean createEntry(IdmUserLdap ldapEntryBean);


	boolean destoryEntry(final String userName);


	boolean assignUserToGroup(String username, String groupName);


	List<String> getAssignedGroupsByUser(String userName);
}