package com.bestinet.mycare.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DevicePermissionHelper {
    private static final int REQUEST_CODE = 1234;
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED + 1;

    private DevicePermissionHelper() {
    }

    public static void checkDevicePermission(Context context) {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE
        };

        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    permissions,
                    REQUEST_CODE
            );
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    permissions,
                    GRANTED);
        }
    }
}
