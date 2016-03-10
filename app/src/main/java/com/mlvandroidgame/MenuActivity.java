package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {

    private Button share, game, score,licence, map;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.share = (Button) findViewById(R.id.shareButton);
        this.game = (Button) findViewById(R.id.gameButton);
        this.score = (Button) findViewById(R.id.scoreButton);
        this.licence = (Button) findViewById(R.id.licenceButton);
        this.map = (Button) findViewById(R.id.mapButton);


        this.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO partager l'apps
            }
        });

        this.game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LicenceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit)
            MenuActivity.this.finish();
        else {
            Toast.makeText(this, getResources().getText(R.string.Tap_to_exit),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }
}
