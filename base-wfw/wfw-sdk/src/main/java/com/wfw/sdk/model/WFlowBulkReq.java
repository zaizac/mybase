package com.wfw.sdk.model;

import java.util.List;

/**
 * @author Raj Kapur Saha
 * @since 04/13/2018
 */

public class WFlowBulkReq {

    private List<String> appRefList;

    private String userType;

    private String userFullName;

    private String userId;

    public List<String> getAppRefList() {
        return appRefList;
    }

    public void setAppRefList(List<String> appRefList) {
        this.appRefList = appRefList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
