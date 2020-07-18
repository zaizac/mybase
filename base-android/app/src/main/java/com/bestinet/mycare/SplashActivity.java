package com.bestinet.mycare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bestinet.mycare.helper.DevicePermissionHelper;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private static final int GRANTED = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        DevicePermissionHelper.checkDevicePermission(SplashActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goNextActivity();
                } else {
                    goNextActivity();
                }
                return;
            }

            case GRANTED: {
                goNextActivity();
            }
            default:
                goNextActivity();
        }
    }


    private void goNextActivity() {
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        };
        handler.postDelayed(runnable, 2000);
    }
}
