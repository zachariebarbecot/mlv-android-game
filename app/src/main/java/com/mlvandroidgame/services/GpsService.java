package com.mlvandroidgame.services;

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
    private String country;
    private double latitude;
    private double longitude;
    private Date date;
    private int score;

    public GpsService(String url, String numero, String imei, float batteryPct, String country,
                      double latitude, double longitude, Date date, int score) {
        this.url = url;
        this.numero = numero;
        this.imei = imei;
        this.batteryPct = batteryPct;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.score = score;
    }

    @SuppressWarnings("SimpleDateFormat")
    public void send() {
        JSONParser jsonParser = new JSONParser();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        nameValuePairs.add(new BasicNameValuePair("numero", numero));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("batteryPct", format.format(batteryPct)));
        nameValuePairs.add(new BasicNameValuePair("country", country));
        nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
        nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
        nameValuePairs.add(new BasicNameValuePair("date", date.toString()));
        nameValuePairs.add(new BasicNameValuePair("score", String.valueOf(score)));

        JSONObject jsonObject = jsonParser.getJSONFromUrl(url, nameValuePairs);
    }
}
