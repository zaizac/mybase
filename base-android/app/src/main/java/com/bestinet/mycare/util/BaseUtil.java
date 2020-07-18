package com.bestinet.mycare.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;

import com.bestinet.mycare.constant.BaseConstant;

import java.text.DecimalFormat;
import java.util.List;

public class BaseUtil {

    private BaseUtil() {
    }

    private static String getStr(Object obj) {
        if (obj != null) {
            return obj.toString().trim();
        } else {
            return BaseConstant.EMPTY_STRING;
        }
    }

    private static String getText(TextView obj) {
        if (obj != null) {
            return obj.getText().toString().trim();
        } else {
            return BaseConstant.EMPTY_STRING;
        }
    }

    public static String getText(EditText obj) {
        if (obj != null) {
            return obj.getText().toString().trim();
        } else {
            return BaseConstant.EMPTY_STRING;
        }
    }

    public static String getStrWithNull(Object obj) {
        if (obj != null) {
            return obj.toString().trim();
        } else {
            return null;
        }
    }

    public static String getStrUpper(Object obj) {
        if (obj != null) {
            return obj.toString().trim().toUpperCase();
        } else {
            return BaseConstant.EMPTY_STRING;
        }
    }

    public static String getStrLower(Object obj) {
        if (obj != null) {
            return obj.toString().trim().toLowerCase();
        } else {
            return BaseConstant.EMPTY_STRING;
        }
    }

    public static boolean isEquals(String oriSrc, String compareSrc) {
        boolean isEqual = false;

        if (oriSrc != null && getStr(oriSrc).equals(getStr(compareSrc))) {
            isEqual = true;
        }

        return isEqual;
    }

    public static boolean isEqualsCaseIgnore(String oriSrc, String compareSrc) {
        boolean isEqual = false;

        if (oriSrc != null && getStr(oriSrc).equalsIgnoreCase(getStr(compareSrc))) {
            isEqual = true;
        }

        return isEqual;
    }

    public static boolean isEqualsAny(String oriSrc, String compareSrc) {
        boolean isEqual = false;
        String[] compareSrcArr = compareSrc.split(",");
        for (String s : compareSrcArr) {
            if (oriSrc != null
                    && getStr(oriSrc).equals(getStr(s))) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
    }

    public static boolean isObjNull(Object obj) {
        boolean isNull = true;

        if (obj != null && getStr(obj).length() > 0) {
            isNull = false;
        }

        return isNull;
    }

    public static boolean isTextViewNull(TextView obj) {
        boolean isNull = true;

        if (obj != null && getText(obj).length() > 0) {
            isNull = false;
        }

        return isNull;
    }

    public static Boolean isListNull(List o) {
        boolean isListNull = true;

        if (o != null && getListSize(o) > 0) {
            isListNull = false;
        }

        return isListNull;
    }

    private static Integer getListSize(List o) {
        int listSize = 0;

        if (o != null && !o.isEmpty()) {
            listSize = o.size();
        }

        return listSize;
    }

    public static Double getDouble(Object obj, String pattern) {
        if (obj != null) {
            try {
                double d = Double.parseDouble(obj.toString().trim());

                if (!isObjNull(pattern)) {
                    DecimalFormat df = new DecimalFormat(pattern);
                    return Double.parseDouble(df.format(d));
                } else {
                    return d;
                }
            } catch (Exception e) {
                return (double) 0;
            }
        } else {
            return (double) 0;
        }
    }

    public static void redirectApp(Activity context, String appPackageName) {

        try {
            context.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)), 10001);
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)), 10001);
        }
    }
}
