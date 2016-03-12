package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.mlvandroidgame.databases.Score;
import com.mlvandroidgame.databases.ScoreDataSource;
import com.mlvandroidgame.utils.ScoreSingleton;

import java.util.Date;
import java.util.Random;

public class GameActivity extends Activity {

    private ScoreDataSource scoreDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        this.scoreDataSource = new ScoreDataSource(this);
    }

    @Override
    protected  void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                int i = r.nextInt(100);
                Score score = new Score(new Date(), i);
                ScoreSingleton.getINSTANCE().setScore(score);
                scoreDataSource.addScore(score);
                Toast toast = Toast.makeText(getApplicationContext(),"Score: " + score.getScore(), Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(GameActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
