/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwUser;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_USER_DTO)
@EnableTransactionManagement
public interface WfwUserDto {

	public WfwUser findUserByUserId(Integer userId);


	public List<WfwUser> findAllWfUser();


	public WfwUser findWfUserByUsernamePassword(String username, String password);


	public WfwUser findWfUserByUsername(String username);


	public boolean addWfUser(WfwUser wfwUser);


	public boolean editWfUser(WfwUser wfwUser);


	public boolean deleteWfUser(WfwUser wfwUser);


	public boolean deleteWfUser(Integer userId);

}
