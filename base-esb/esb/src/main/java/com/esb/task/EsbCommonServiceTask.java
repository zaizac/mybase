package com.esb.task;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esb.client.EsbServiceClient;
import com.esb.common.UidGenerator;
import com.esb.util.BaseConstants;
import com.esb.util.BaseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Chaithanya
 * @since Oct 12, 2017
 */

public class EsbCommonServiceTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsbCommonServiceTask.class);
	
	private String serviceSource;
	private String serviceTarget;
	private String serviceReturn;
	
	private String serviceFlagSource;
	private String serviceFlagTarget;
	private String serviceFlagReturn;
	
 	public EsbServiceClient esbServiceSource;
 	public EsbServiceClient esbServiceTarget;
	
	public String getServiceSource() {
		return serviceSource;
	}

	public void setServiceSource(String serviceSource) {
	 	this.serviceSource = serviceSource;
	}

	public String getServiceTarget() {
		return serviceTarget;
	}

	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}

	public String getServiceReturn() {
		return serviceReturn;
	}

	public void setServiceReturn(String serviceReturn) {
		this.serviceReturn = serviceReturn;
	}

	public String getServiceFlagSource() {
		return serviceFlagSource;
	}

	public void setServiceFlagSource(String serviceFlagSource) {
		this.serviceFlagSource = serviceFlagSource;
	}

	public String getServiceFlagTarget() {
		return serviceFlagTarget;
	}

	public void setServiceFlagTarget(String serviceFlagTarget) {
		this.serviceFlagTarget = serviceFlagTarget;
	}

	public String getServiceFlagReturn() {
		return serviceFlagReturn;
	}

	public void setServiceFlagReturn(String serviceFlagReturn) {
		this.serviceFlagReturn = serviceFlagReturn;
	}
	
	
	public EsbServiceClient getEsbServiceSource() {
		esbServiceSource.setMessageId(UidGenerator.getMessageId());
		return esbServiceSource;
	}

	public void setEsbServiceSource(EsbServiceClient esbServiceSource) {
		this.esbServiceSource = esbServiceSource;
	}

 	public EsbServiceClient getEsbServiceTarget() {
 		esbServiceTarget.setMessageId(UidGenerator.getMessageId());
		return esbServiceTarget;
	}

	public void setEsbServiceTarget(EsbServiceClient esbServiceTarget) {
		this.esbServiceTarget = esbServiceTarget;
	}

	/**
	 * Service W2M_IN
	 * 
	 * @return Object
	 */
	public Object triggerServiceW2M() {
		LOGGER.info("Processing Source Service S2E_IN URL..-->"+serviceSource);
	 	try {
			return getEsbServiceSource().getForObject(serviceSource);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ERROR - Service S2E_IN:"+serviceSource +".."+ e.getMessage());
		}
		return null;
	}
	

	/**
	 * Service M2W_OUT
	 * 
	 * @param  request body
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public Object handleServiceM2W(String requestBody) throws JsonProcessingException, IOException {
		LOGGER.info("Processing Source Service S2E_OUT.." + requestBody);
		try {
			return  getEsbServiceTarget().postForObject(serviceTarget, requestBody);
	 
		} catch (Exception e) {
			LOGGER.error("ERROR -  Service S2E_OUT : " + e.getMessage());
		} 
		
		return BaseUtil.updateSyncFlagValue(requestBody, "syncFlag", Integer.valueOf(BaseConstants.SYNC_INITIATE));
	}
	
	/**
	 * Service M2W_RTN
	 * 
	 * @param requestBody
	 * @return
	 */
	public Object returnServiceM2W(String requestBody) {
		LOGGER.info("Processing Source Service S2E RTN..." + requestBody);
		try {
			getEsbServiceSource().postForObject(serviceReturn, requestBody);
			return null;
		} catch (Exception e) {
			LOGGER.error("ERROR - Service S2E RTN: " + e.getMessage());
		}
		
		return requestBody;
	}
	
	
	

	/**
	 * Service Target W2M_IN
	 * 
	 * @return Object
	 */
	public Object triggerTargetServiceW2M() {
		LOGGER.info("Processing Target Service S2E_IN URL..-->"+serviceFlagSource);
	 	try {
			return getEsbServiceTarget().getForObject(serviceFlagSource);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ERROR - Service Target S2E_IN:"+serviceSource +".."+ e.getMessage());
		}
		return null;
	}
	

	/**
	 * Service Target M2W_OUT
	 * 
	 * @param  request body
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public Object handleTargetServiceM2W(String requestBody) throws JsonProcessingException, IOException {
		LOGGER.info("Processing Target Service S2E_OUT.." + requestBody);
		try {
			Object result = getEsbServiceSource().postForObject(serviceFlagTarget, requestBody);
			return result;
		} catch (Exception e) {
			LOGGER.error("ERROR -  Service Target S2E_OUT : " + e.getMessage());
		} 
		
		return BaseUtil.updateSyncFlagValue(requestBody, "syncFlag", Integer.valueOf(BaseConstants.SYNC_INITIATE));
	}
	
	/**
	 * Service Target M2W_RTN
	 * 
	 * @param requestBody
	 * @return
	 */
	public Object returnTargetServiceM2W(String requestBody) {
		LOGGER.info("Processing Target Service S2E RTN..." + requestBody);
		try {
			getEsbServiceTarget().postForObject(serviceFlagReturn, requestBody);
			return null;
		} catch (Exception e) {
			LOGGER.error("ERROR - Service Target S2E RTN: " + e.getMessage());
		}
		
		return requestBody;
	}
	
	
	
}
