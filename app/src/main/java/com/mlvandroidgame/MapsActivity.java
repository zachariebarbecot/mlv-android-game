package com.mlvandroidgame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mlvandroidgame.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<HashMap<String, String>> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.players = new ArrayList<>();
        new MyAsyncTask().execute();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        LatLng paris = new LatLng(48.866667, 2.333333);
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(paris, 10.0f));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MapsActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Boolean> {

        private String url = getResources().getString(R.string.url2);

        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Loading data...", Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();

            try {
                JSONObject jsonObject = jsonParser.getJSONFromUrl(url);

                Log.d("All Record: ", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("location");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    // Storing each json item in variable
                    String imei = c.getString("imei");
                    String latitude = c.getString("latitude");
                    String longitude = c.getString("longitude");
                    String date_gps = c.getString("date_gps");
                    String score = c.getString("score");
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("imei", imei);
                    map.put("latitude", latitude);
                    map.put("longitude", longitude);
                    map.put("date_gps", date_gps);
                    map.put("score", score);
                    // adding HashList to ArrayList
                    players.add(map);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            for (HashMap<String, String> player : players) {
                double latitude = Double.parseDouble(player.get("latitude"));
                double longitude = Double.parseDouble(player.get("longitude"));
                final LatLng local = new LatLng(latitude,longitude);
                Marker locations = mMap.addMarker(new MarkerOptions()
                        .position(local)
                        .title("Imei: " + player.get("imei"))
                        .snippet("Date: " + player.get("date_gps") + "\nscore: " + player.get("score"))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.videogames)));
            }
        }
    }
}
