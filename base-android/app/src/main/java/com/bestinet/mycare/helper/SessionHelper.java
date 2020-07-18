//package com.bestinet.mycare.helper;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class SessionHelper {
//
//    private SessionHelper() {
//    }
//
//    public static Boolean isAutoSignIn(Context context) {
//
//        AuthRequest loginModel = getIsAutoSignIn(context);
//
//        return !BaseUtil.isObjNull(loginModel) && !BaseUtil.isObjNull(loginModel.getAutoSignIn()) && loginModel.getAutoSignIn();
//
//    }
//
//    public static AuthRequest getIsAutoSignIn(Context context) {
//        String loginModelString = PreferenceHelper.getPreference(context, AppConstant.PREFERENCE_AUTH);
//
//        if (!BaseUtil.isObjNull(loginModelString)) {
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//                AuthRequest loginModel = mapper.readValue(loginModelString, AuthRequest.class);
//
//                if (!BaseUtil.isObjNull(loginModel)) {
//                    return loginModel;
//                }
//            } catch (JsonProcessingException e) {
//                Log.d(ExceptionConstant.CATCH_ERROR, e.getOriginalMessage());
//            }
//        }
//
//        return new AuthRequest();
//    }
//
//    public static void setIsAutoSignIn(Context context, AuthRequest authModel) {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            String loginJsonString = mapper.writeValueAsString(authModel);
//
//            if (!BaseUtil.isObjNull(loginJsonString)) {
//                PreferenceHelper.savePreference(context, AppConstant.PREFERENCE_AUTH, loginJsonString);
//            }
//        } catch (JsonProcessingException e) {
//            Log.d(ExceptionConstant.CATCH_ERROR, e.getOriginalMessage());
//        }
//    }
//
//    public static void setSessionAuthResponse(Context context, AuthResponse authResponse) {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            String loginJsonString = mapper.writeValueAsString(authResponse);
//
//            if (!BaseUtil.isObjNull(loginJsonString)) {
//                PreferenceHelper.savePreference(context, AppConstant.PREFERENCE_AUTH_RESPONSE, loginJsonString);
//            }
//        } catch (JsonProcessingException e) {
//            Log.d(ExceptionConstant.CATCH_ERROR, e.getOriginalMessage());
//        }
//    }
//
//    public static AuthResponse getAuthResponse(Context context) {
//        String authRespString = PreferenceHelper.getPreference(context, AppConstant.PREFERENCE_AUTH_RESPONSE);
//
//        if (!BaseUtil.isObjNull(authRespString)) {
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//                AuthResponse authResponse = mapper.readValue(authRespString, AuthResponse.class);
//
//                if (!BaseUtil.isObjNull(authResponse)) {
//                    return authResponse;
//                }
//            } catch (JsonProcessingException e) {
//                Log.d(ExceptionConstant.CATCH_ERROR, e.getOriginalMessage());
//            }
//        }
//
//        return new AuthResponse();
//    }
//
//
//    public static void clearSession(Context context) {
//        PreferenceHelper.deletePreference(context, AppConstant.PREFERENCE_AUTH);
//        PreferenceHelper.deletePreference(context, AppConstant.PREFERENCE_AUTH_RESPONSE);
//    }
//}
