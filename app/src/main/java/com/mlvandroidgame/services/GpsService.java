package com.mlvandroidgame.services;

import android.util.Log;

import com.mlvandroidgame.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Zacharie BARBECOT.
 */
public class GpsService {

    private String url;
    private String numero;
    private String imei;
    private float batteryPct;
    private double latitude;
    private double longitude;
    private Date dateGps;
    private Date dateCell;
    private int score;

    public GpsService(String url, String numero, String imei, float batteryPct,
                      double latitude, double longitude, Date dateGps, Date dateCell, int score) {
        this.url = url;
        this.numero = numero;
        this.imei = imei;
        this.batteryPct = batteryPct;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateGps = dateGps;
        this.dateCell = dateCell;
        this.score = score;
    }

    @SuppressWarnings("SimpleDateFormat")
    public void send() {
        JSONParser jsonParser = new JSONParser();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        nameValuePairs.add(new BasicNameValuePair("numero", numero));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("batteryPct", String.valueOf(batteryPct)));
        nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
        nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
        nameValuePairs.add(new BasicNameValuePair("dateGps", format.format(dateGps)));
        nameValuePairs.add(new BasicNameValuePair("dateCell", format.format(dateCell)));
        nameValuePairs.add(new BasicNameValuePair("score", String.valueOf(score)));

        JSONObject jsonObject = jsonParser.sendJSONToUrl(url, nameValuePairs);
        Log.i("JSON DATA SEND", nameValuePairs.toString());

    }
}
