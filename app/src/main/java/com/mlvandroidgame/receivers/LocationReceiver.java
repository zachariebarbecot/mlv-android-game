package com.mlvandroidgame.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mlvandroidgame.services.ShowLocationService;

/**
 * Created by Zacharie BARBECOT.
 */
public class LocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("LocationReceiver", "Location RECEIVED!");

        if (intent.getAction().equals("android.intent.action.LOCATION")) {
            context.startService(new Intent(context, ShowLocationService.class));
        }
    }
}
