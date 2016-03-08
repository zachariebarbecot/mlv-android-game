package com.mlvandroidgame;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.mlvandroidgame.databases.AdminDataSource;

public class SplashActivity extends Activity {

    private String imei;
    private AdminDataSource adminDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        /*
         * GET IMEI
         */
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        this.imei=mngr.getDeviceId();
        if(imei == null) {
            this.finish();
        }

        this.adminDataSource = new AdminDataSource(this);

        if (android.os.Build.VERSION.SDK_INT > 14) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if(adminDataSource.getAdminByImei(imei).isEmpty()) {
            splash();
        }
    }

    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!adminDataSource.getAdminByImei(imei).isEmpty()) {
                    intent = new Intent(SplashActivity.this, MenuActivity.class);
                } else {
                    //Admin admin = new Admin(imei);
                    //adminDataSource.addAdmin(admin);
                    intent = new Intent(SplashActivity.this, LicenceActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
