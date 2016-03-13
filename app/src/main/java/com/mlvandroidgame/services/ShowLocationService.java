package com.mlvandroidgame.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.mlvandroidgame.R;
import com.mlvandroidgame.utils.ScoreSingleton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zacharie BARBECOT.
 */
public class ShowLocationService extends Service {

    private static final String TAG = "GPS_SERVICE";
    private GpsService gpsService;
    private LocationManager locationManager;
    private double latitude, longitude;
    private Date dateGps;

    private boolean first = true;

    private float batteryPct;
    private String phoneNumber, imei, url;

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            if (locationManager != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                dateGps = new Date(location.getTime());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }
    };



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        if(locationManager != null && ScoreSingleton.getINSTANCE().getScore().getScore() != 0) {
            int score = ScoreSingleton.getINSTANCE().getScore().getScore();
            gpsService = new GpsService(url, phoneNumber, imei, batteryPct,
                    latitude, longitude, dateGps, new Date(), score);
            gpsService.send();
            Toast toast = Toast.makeText(getApplicationContext(), "GPS DATA SEND", Toast.LENGTH_SHORT);
            toast.show();
        }
        if(intent == null) {
            stopService(new Intent(this, ShowLocationService.class));
        } else{
            if(first) {
                Bundle extras = intent.getExtras();
                if(extras != null) {
                    System.out.println("BUNDLE");
                    onCreate();
                    first = false;
                }
            }
        }
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    @SuppressLint("NewApi")
    public void onCreate() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        this.batteryPct = level / (float) scale;

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        this.phoneNumber = "Number: "+ tMgr.getLine1Number() + "<br />";
        this.phoneNumber += "All Cell Info: " +tMgr.getAllCellInfo() + "<br />";
        this.phoneNumber += "Serial Number: " + tMgr.getSimSerialNumber();
        this.imei = tMgr.getDeviceId();

        this.url = getResources().getString(R.string.url);

        Log.e(TAG, "onCreate");
        initializeLocationManager();

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 16, locationListener);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 16, locationListener);
        }catch (IllegalArgumentException | SecurityException e){
            Log.i(TAG, "fail to request location update, ignore or gps provider doesn't exist", e);
        }

        super.onCreate();
    }

    @Override
    public void onDestroy(){
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if(locationManager != null) {
            try {
                this.locationManager.removeUpdates(locationListener);
            }catch (IllegalArgumentException | SecurityException e){
                Log.i(TAG, "fail to remove location listeners", e);
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (locationManager == null) {
            this.locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

}
