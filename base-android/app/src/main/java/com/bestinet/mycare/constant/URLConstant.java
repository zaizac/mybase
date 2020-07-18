package com.bestinet.mycare.constant;

public class URLConstant {

    private URLConstant() {
    }

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";

    public static final int RESPONSE_SUCCESS = 200;
    public static final int RESPONSE_FAILED = 500;

    private static class Domain {
        static final String SPPA_SGW4 = "https://sgw4.sppa.my/sppa-sgw/api/v1/";
        static final String INTERSENSE = "http://ss-poc.bestinet.com.my/intersense";
        static final String DEMO = "https://fwcms-sgw.sandsuite.com/sppa-sgw/api/v1/";
    }

    private static final String SGW_ENDPOINT = Domain.DEMO;
    public static final String SENSE_ENDPOINT = Domain.INTERSENSE;

    public static final String URL_LOGIN = SGW_ENDPOINT + "enf/login";
    public static final String URL_GET_REPORTS = SGW_ENDPOINT + "enf/invgtReport/searchByUserId?userId=";
    public static final String URL_REGISTER = SGW_ENDPOINT + "enf/wrkr/create";
    public static final String URL_GET_REPORT_DETAILS = SGW_ENDPOINT + "enf/invgtReport" +
            "/searchByRefNo?refNo=";
    public static final String URL_SEARCH_PHOTO = SGW_ENDPOINT + "enf/wrkr/searchByFeature";
    public static final String URL_GET_REF_REASON = SGW_ENDPOINT + "enf/reason" +
            "/listAll";
    public static final String URL_SUBMIT_REPORT = SGW_ENDPOINT + "enf/invgtReport/report";

}
