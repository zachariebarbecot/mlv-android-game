package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
    }

    @Override
    protected  void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
