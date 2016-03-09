package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Space;

public class MenuActivity extends Activity {

    private Button share, game, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.share = (Button) findViewById(R.id.shareButton);
        this.game = (Button) findViewById(R.id.gameButton);
        this.score = (Button) findViewById(R.id.scoreButton);


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
                Intent intent = new Intent(MenuActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
