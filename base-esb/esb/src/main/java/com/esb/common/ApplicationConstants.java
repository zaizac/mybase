/*

 * @ApplicationConstants.java	@Apr 26, 2013
 *
 * Copyright (c) 2013 Bestinet. 
 * All rights reserved. 
 * 
 * No part of this document may be reproduced or transmitted in any form or by 
 * any means, electronic or mechanical, whether now known or later invented, 
 * for any purpose without the prior and express written consent of Bestinet 
 * 
 */
package com.esb.common;

/**
 * <p>
 * Interface which captures application level Constants
 * </p>
 *
 * @author nisha.golani
 * @version 1.0
 *
 */
public interface ApplicationConstants {


	String BATCH_SERVICE_ROOT_URL_KEY = "batch.root.url";
	String INTEGRATION_SERVICE_ROOT_URL_KEY = "integration.root.url";
	String MYIMMS_INCREMENTAL_UPDATE_URL_KEY = "myimms.incremental.update.batch.url";
	String GET_INCREMENTAL_DATA_URL_KEY = "get.incremental.data.url";
	String EMPLOYER_INFORMATION_UPDATE_URL_KEY = "employer.information.update.url";
	String MAID_AGENCY_INFORMATION_UPDATE_URL_KEY = "maid.agency.information.update.url";

	String CDC_SERVER_URL_KEY = "cdc.server.url";
	String MISSING_SERVICE_URL = "Missing Service URL";
	
	String VISA_GENERATE_DOCUMENTS_TRIGGER_KEY = "visa.document.generation.trigger.url";
	String VISA_GENERATE_DOCUMENTS_HANDLER_KEY = "visa.document.generation.handler.url";
	
	String VISA_IN_PROGRESS_TRIGGER_KEY = "visa.inProgress.trigger.url";
	String VISA_IN_PROGRESS_HANDLER_KEY = "visa.inProgress.handler.url";
	
	String VDR_DELETION_PROCESS_KEY = "vdr.deletion.process.trigger.url";
	String VDR_DELETION_HANDLER_KEY = "vdr.deletion.process.handler.url";
	String INCOMPLETE_VDR_TRIGGER_KEY = "incomplete.vdr.trigger.url";
	String INCOMPLETE_VDR_HANDLER_KEY = "incomplete.vdr.handler.url";
	
	String BATCH_TYPE_EMPLOYER = "EMPLOYER_BATCH";
	String BATCH_TYPE_QUOTA = "QUOTA_BATCH";
	String BATCH_TYPE_MAID_AGENCY = "MAID_AGENCY_BATCH";
			
	/*MyIMMs Integration batch process*/
	
	String WORKER_MYIMMS_VALIDATION_TRIGGER_KEY = "reg/getWorkerIdsForValidation";
	String WORKER_MYIMMS_VALIDATION_HANDLER_KEY = "reg/myImmsWorkerValidation";
	String WORKER_VDR_SUBMISSION_VALIDATION_HANDLER_KEY = "worker.vdr.submission.validation.handler.url";
	String EMPLOYER_BL_CHECK_BEFORE_MJ_UPDATE = "perform.employer.bl.before.mj.update";
	
	String QUOTA_INFORMATION_UPDATE_URL_KEY = "quota.information.update.url";
		
	String VDR_MYIMMS_INTEGRATION_TRIGGER_KEY = "visamanager/getVdrIdsWithStatus";
	String VDR_MYIMMS_INTEGRATION_HANDLER_KEY = "payloadManager/generateVdrPayload";
	String VDR_MJ_UPDATE_TO_MYIMMS_TRIGGER_URL = "vdr.updatemj.trigger.url";
	String VDR_MJ_UPDATE_TO_MYIMMS_HANDLER_URL = "vdr.updatemj.handler.url";
	
	String PAYLOAD_BATCH_TRIGGER = "payloadManager/getPayloadIds";
	String PAYLOAD_BATCH_HANDLER = "payloadManager/readPayloadDetails";
	
	String FRANKING_DETAILS_UPDATE_MAIL_NOTIFICATION_KEY = "franking.details.mail.notification.trigger.url";
	String INSURANCE_DETAILS_UPDATE_MAIL_NOTIFICATION_KEY = "insurance.details.mail.notification.trigger.url";
	String IGREFUND_CLAIM_DETAILS_UPDATE_MAIL_NOTIFICATION_KEY = "igrefund.claim.details.mail.notification.trigger.url";
	String ENDORSEMENT_DETAILS_UPDATE_MAIL_NOTIFICATION_KEY = "endorsement.details.mail.notification.trigger.url";
	String WORKER_CANCELLATION_DETAILS_UPDATE_MAIL_NOTIFICATION_KEY = "worker.cancellation.details.mail.notification.trigger.url";
	String VDR_STATUS_UPDATE_KEY = "vdr.status.update.trigger.url";
	String UNLOCK_INSURANCE_REQUEST_DETAILS_KEY = "unlock.insurance.request.details.trigger.url";
	
	String INS_CRT_BTCH_REQ_TRIGGER = "ins.create.batch.request.trigger.url";
	String INS_CRT_BTCH_REQ_HANDLER = "ins.create.batch.request.handler.url";
	
	String INS_PURGE_BTCH_HANDLER = "ins.purge.batch.request.handler.url";
	
	String INS_CRT_PAYLOAD_BTCH_TRIGGER = "ins.create.batch.payload.trigger.url";
	String INS_CRT_PAYLOAD_BTCH_HANDLER = "ins.create.batch.payload.handler.url";

	String INS_STATUS_TRIGGER = "ins.status.request.trigger.url";
	String INS_STATUS_HANDLER = "ins.status.request.handler.url";
	
	String MC_REPRESENTATIVE_BLOCK_URL_KEY = "mc.representative.block.serice.url";
	String MYIMMS_QUEUE_BATCH_COUNT="myimms.queue.batch.count";
	
	String SEND_NOTIFICATION_TRIGGER_KEY = "send.notification.trigger.url";
	String SEND_NOTIFICATION_HANDLER_KEY = "send.notification.handler.url";
	
	String REFRESH_EHCACHE_RESOURCES_URL_KEY = "refresh.ehcache.resources.service.url";
	
	String SEND_WORKER_TRANSFER_NOTIFICATION_TRIGGER_KEY = "send.worker.transfer.notification.trigger.url";
	
	String VISA_REPLACEMENT_STATUS_UPDATE_KEY = "visa.replacement.status.update.trigger.url";
	
	//BioSL keys
	String MYIMMS_SLBL_TRIGGER_KEY = "myimms.slbl.trigger.url";
	String MYIMMS_SLBL_HANDLER_KEY = "myimms.slbl.handler.url";
	String MYIMMS_SLBL_WORKERLIST_COUNT = "myimms.slbl.workerlist.count";

	String GET_WRKRS_WIHTOUT_MC_REPORT_KEY = "get.workers.without.mc.report.trigger.url";
	String GENERATE_MC_REPORT_FOR_WORKERS_KEY = "generate.mc.report.for.workers.handler.url";
	
	String REINITIATE_INPROGRESS_STAGING_DATA_URL_KEY = "reinitiate.inprogress.staging.data.trigger.url";
	String VDR_PRA_DELETION_KEY = "vdr.pra.deletion.trigger.url";
	String VDR_PRA_DELETION_HANDLER_KEY = "vdr.pra.deletion.process.handler.url";
	
	String VDR_PRA_DOCUMENT_GENERATION_TRIGGER_KEY = "vdr.pra.document.generation.trigger.url";
	String VDR_PRA_DOCUMENT_GENERATION_HANDLER_KEY = "vdr.pra.document.generation.handler.url";
	
	String VDR_PRA_IN_PROGRESS_TRIGGER_KEY = "vdr.pra.inProgress.trigger.url";
	String VDR_PRA_IN_PROGRESS_HANDLER_KEY = "vdr.pra.inProgress.handler.url";
	
	String VDR_PRA_INCOMPLETE_TRIGGER_KEY = "vdr.pra.incomplete.notification.trigger.url";
	String VDR_PRA_INCOMPLETE_HANDLER_KEY = "vdr.pra.incomplete.notification.handler.url";
	
	String VDR_PRA_EMPLOYER_UPDATE_PAYLOAD_TRIGGER_KEY = "vdr.pra.employer.update.payload.trigger.url";
	String VDR_PRA_EMPLOYER_UPDATE_PAYLOAD_HANDLER_KEY = "vdr.pra.employer.update.payload.handler.url";
	String VDR_PRA_SUBMISSION_WORKER_VALIDATION_HANDLER_KEY = "vdr.pra.submission.worker.validation.handler.url";

	String UNTAG_SECURITY_WORKER_TRIGGER_KEY = "security.wrkr.untag.trigger.url";
	
	String WORKER_ARRIVAL_DATE_TRIGGER_KEY = "worker.arrival.date.trigger.url";
	String WORKER_ARRIVAL_DATE_HANDLER_KEY = "worker.arrival.date.handler.url";
	
	String INS_PAYMENT_STATUS_TRIGGER_KEY = "ins.payment.status.trigger.url";
	String INS_PAYMENT_STATUS_HANDLER_KEY = "ins.payment.status.handler.url";
	
	String INS_PURGE_BATCH_AUTOSTART = "ins.purge.batch.request.task.autostart";
	String TRUE_CONST = "true";
}
