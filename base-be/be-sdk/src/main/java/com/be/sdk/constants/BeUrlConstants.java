/**
 * Copyright 2019
 */
package com.be.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public class BeUrlConstants {
	
	private BeUrlConstants() {
		throw new IllegalStateException(BeUrlConstants.class.getName());
	}

	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String REFERENCE = "/references";
	
	public static final String REFERENCE_BE = "/references/be";

	public static final String CONFIG = "/beConfig";

	public static final String REF_REF_COUNTRY = REFERENCE + "/references/getRefCountry";

	public static final String STATUS = REFERENCE + "/status";

	public static final String CITY = REFERENCE + "/cities";
	
	public static final String STATE = REFERENCE + "/states";
	
	public static final String COUNTRY = REFERENCE + "/countries";

	public static final String GET_ALL_RELATIONSHIP = "/getAllRelationship";

	public static final String GET_ALL_STATE = "/getAllState";

	public static final String GET_ALL_COUNTRY = "/getAllCountry";

	public static final String GET_ALL_NATIONALITY = "/getAllNationality";

	public static final String GET_ALL_NATIONALITY_CODE = "/findByNationalityCode";

	public static final String GET_ALL_NATIONALITY_DESC = "/findNationalityByDesc";

	public static final String GET_ALL_STATUS = "/getAllStatus";

	public static final String GET_ALL_MARITAL_STATUS = "/getAllMaritalStatus";

	public static final String GET_ALL_MARITAL_STATUS_CODE = "/findByMaritalStatusCode";

	public static final String GET_ALL_CITY_CODE = "/findByCityCode";

	public static final String QUOTA = "/quota";

	public static final String SEARCH_PAGINATION = "/search/paginated";

	public static final String WRKR_PROFILE = "/wrkrProfile";

	public static final String FIND_WRKR_PROFILE = "/findWrkrProfile";

	public static final String WRKR_PROFILE_CLEARANCE = "/wrkrProfileClearance";

	public static final String SEARCH_CLEARANCE_PAGINATION = "/searchClearance/paginated";

	public static final String CLEARANCE = "/clearance";

	public static final String FIND_CLEARANCE = "/findClearance";

	public static final String SEARCH_CLEARANCE = "/searchClearance";

	public static final String CREATE_CLEARANCE = "/createClearance";

	public static final String UPDATE_CLEARANCE = "/updateClearance";

	public static final String ASSIGNMENT = "/assignment";

	public static final String SEARCH_ASSIGNMENT = "/searchAssignment";

	public static final String CREATE_ASSIGNMENT = "/createAssignment";

	public static final String DMND_LETTER = "/demand";

	public static final String DMND_LETTER_FIND_DOC = DMND_LETTER + "/{quotaRefNo}?isDocReq=true";

	public static final String DMND_LETTER_FIND_CMPNY_SGNTR = DMND_LETTER
			+ "/{quotaRefNo}?isCmpnyReq=true&isSgntrReq=true&isOwnerReq=true"; // GET;

	public static final String DMND_LETTER_FIND_CMPNY = DMND_LETTER + "/{quotaRefNo}?isCmpnyReq=true"; // GET

	public static final String DMND_LETTER_FIND_REMARK = DMND_LETTER + "/{quotaRefNo}?isRemarksReq=true"; // GET;

	public static final String DMND_LETTER_FIND_AGNT = DMND_LETTER + "/{quotaRefNo}?isAgntReq=true"; // GET

	public static final String COMPANY = "/company";

	public static final String COMPANY_ADDRESS = COMPANY + "/address";

	public static final String COMPANY_PROFILE = COMPANY + "/profiles";

	public static final String COMPANY_ADDR_BY_COMPANY_ID = "/findCompanyAddressByCmpnyAddrId";

	public static final String COMPANY_OWNER = COMPANY + "/owners";

	public static final String TRXN_DOCUMENT = "/trxnDocument";

	public static final String API_VERSION = "/api/v1";

	public static final String REGISTRATIONS = API_VERSION + "/registrations";

	public static final String PERMISSION = "/permission";

	public static final String INFO_ADD = "/infoAdd";

	public static final String GET_LIST = "/getList";

	public static final String GET_DETAIL = "/getDtl";

	public static final String GET_EMP_DETAIL = "/getEmpDtl";

	public static final String GET_ALL_COMPANY = "/getAllCompany";

	public static final String RA = "/ra";

	public static final String FIND_RA = "/findRA";

	public static final String REGISTER = "/register";

	public static final String VERIFY_RA = "/verifyRa";

	public static final String GET_ALL_MED_CENTER = "/getAllMedCenter";

	public static final String MC_PROFILE = "/mcProfiles";

	public static final String MC_LOGIN = "/mcLogin";

	public static final String TRAVEL = "/travel";

	public static final String WRKR_PROFILE_TRAVEL = "/wrkrProfileTravel";

	public static final String VISA = "/visa";

	public static final String WRKR_VISA_DTL_SEV = GET_LIST + "/wrkrVisaDtlSev";

	public static final String SEV_DTL_INFO_ADD = "/sevDtl" + INFO_ADD;

	public static final String VISA_DTL_INFO_ADD = "/visaDtl" + INFO_ADD;

	public static final String MEDICAL = "/medical";

	public static final String DRLKUP = "/drLkup";

	public static final String REF_MED_RPT_ITEM = "/refMedRptItem";

	public static final String GET_MED_RPT_ITEM_LIST = "/getMedRptItmList";

	public static final String MESSAGE_RESPONSE = "/msgRsp";

	public static final String CREATE_UPD_MED_RPT = "/createUpdate";

	public static final String GET_AGENT_DTL = "/getAgentDtl";

	public static final String EMP_ASSIGNMENT = "/empAssignment";


	


	public static final String QUOTA_CREATE = "/create";

	public static final String QUOTA_DQ = "/dq";

	public static final String DMND_LETTER_CRE = DMND_LETTER + "/{quotaRefNo}"; // POST

	public static final String DMND_LETTER_EDIT = DMND_LETTER + "/edit/{quotaRefNo}/status/{statusCd}";// POST

	public static final String DMND_LETTER_UPLD_CRE = DMND_LETTER + "/upload/{quotaRefNo}"; // POST

	public static final String DMND_LETTER_SUBMIT = DMND_LETTER + "/submit/{quotaRefNo}/status/{statusCd}";// POST

	public static final String SECTOR = "/sectors";

	public static final String JOB_CATEGORY = "/jobcategories";

	public static final String QUOTA_BY_REFNUM = "/quotaRefNo";

	public static final String QUOTA_BY_RA = "/raProfId";

	public static final String QUOTAALCAT_BY_REFNUM = "/alcatRefNoList/quotaRefNo";

	public static final String QUOTA_BY_ALCATREFNO = "/alcatRefNo";

	public static final String QUOTA_ALLOCATE = "/allocate";

	public static final String RA_FINDALL = "/findAll";

	public static final String ATTEST_FIND = DMND_LETTER + "/attest/{attestRefNo}";

	public static final String ATTEST_FIND_REMARK = DMND_LETTER + "/attest/{attestRefNo}?isRemarksReq=true";

	public static final String ATTEST_FIND_MOE_PERM = DMND_LETTER + "/attest/moeperms/{permsRefNo}?isRemarksReq=true";

	public static final String ATTEST_FIND_DOC = DMND_LETTER + "/attest/{attestRefNo}?isDocReq=true";

	public static final String DOCUMENTS_FIND = "/document/{quotaRefNo}"; // GET

	public static final String PAGE_APP_REG_UPDATET = REGISTRATIONS + "/update";

	public static final String PAYMENT = "/payment";

	public static final String DAILY_SPPA_ONLINE_RPT = PAYMENT + "/findDailySppaOnlineRpt";

	public static final String VIEW_INVOICE_RPT = PAYMENT + "/findViewInvoiceRpt";

	public static final String FIN_SETTLEMENT_RPT = PAYMENT + "/findFinSettlementRpt";

	public static final String FINANCE_TRANS_RPT = PAYMENT + "/findFinanceTransRpt";

	public static final String FIND_QUOTA_REF_NO_PYMT_APP_ID = "/getQuotaRefNoByPymtAppId";

	public static final String FIND_ONLINE_PYMT_INFO_PYMT_APP_ID = "/getOnlinePymtInfoByPymtAppId";

	public static final String FIND_ONLINE_FPX_RESPONSE_CODE = "/getResponseByFpxCode";

	public static final String FIND_REF_BANK_FPX = "/findRefBankFpxByBankId";

	public static final String UPDATE_STATUS_PYMT_APPROVED = "/updpateStatusPaymentApproved";

	public static final String PYMT_APP_QUOTA_REF_NO = "/findPymtAppByQuotaRefNo";

	public static final String PYMT_APP_QUOTA_INVOICE = "/findPymtAppByInvoice";

	public static final String PYMT_INFO_PYMT_APP_ID = "/findPymtInfoByPymtAppId";

	public static final String TRXN_DOCS_QUOTA_REF_DOC_ID = "/findTrxnDocsByQuotaRefAndDocId";

	public static final String FIND_PYMT_APP_QUOTA_REF_NO = "/getPymtAppByQuotaRefNo";

	public static final String FIND_PYMT_INFO_PYMT_APP_ID = "/getPymtInfoByPymtAppId";

	public static final String DMND_LETTER_PYMT_CRE = DMND_LETTER + "/payment/{quotaRefNo}"; // POST

	public static final String RESUB_FOR_PAY_DQ = DMND_LETTER + "/returnApprovePay/{quotaRefNo}/status/{statusCd}";// POST

	public static final String DMND_LETTER_FIND_AGNT_CMPNY_SGNTR_EMB = DMND_LETTER
			+ "/{quotaRefNo}?isAgntReq=true&isCmpnyReq=true&isOwnerReq=true&isEmbassy=true&isOriginSignatureReq={isOriginEmp}"; // GET

	public static final String DL_POA_AL_DOC_MGT_ID = "/getDLPOAALDocMgtId";

	public static final String DOCUMENTS = "/document";

	public static final String MOE_PERMISSION_BRA = DMND_LETTER + "/moeapprove/{permsRefNo}";// POST

	public static final String ATTEST = DMND_LETTER + "/findAttest/{attestRefNo}";

	public static final String UPDATE = "/update";

	public static final String BALANCE = "/balance";

}
