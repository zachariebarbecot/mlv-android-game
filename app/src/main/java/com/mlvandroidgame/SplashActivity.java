package com.mlvandroidgame;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mlvandroidgame.databases.Admin;
import com.mlvandroidgame.databases.AdminDataSource;
import com.mlvandroidgame.services.ShowLocationService;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    private String imei;
    private AdminDataSource adminDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        // GPS service
        startService(new Intent(this, ShowLocationService.class));

        /*
         * GET IMEI
         */
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        this.imei=mngr.getDeviceId();

        this.adminDataSource = new AdminDataSource(this);

        if (android.os.Build.VERSION.SDK_INT > 14) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ArrayList<Admin> admins = this.adminDataSource.getAdminByImei(imei);
        splash(admins);
    }

    @Override
    public void onBackPressed() {
    }

    private void splash(final ArrayList<Admin> admins) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!admins.isEmpty()) {
                    if(!admins.get(0).getImei().equals(imei)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Not Admin: " + imei, Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                    intent = new Intent(SplashActivity.this, MenuActivity.class);
                } else {
                    Admin admin = new Admin(imei);
                    adminDataSource.addAdmin(admin);
                    Toast toast = Toast.makeText(getApplicationContext(), "Add Admin: " + imei, Toast.LENGTH_SHORT);
                    toast.show();
                    intent = new Intent(SplashActivity.this, LicenceActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
