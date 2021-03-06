package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mlvandroidgame.databases.Score;
import com.mlvandroidgame.databases.ScoreDataSource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScoreActivity extends Activity {

    private TextView dates[];
    private TextView scores[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        this.dates = new TextView[5];
        this.scores = new TextView[5];

        this.dates[0] = (TextView) findViewById(R.id.date1);
        this.dates[1] = (TextView) findViewById(R.id.date2);
        this.dates[2] = (TextView) findViewById(R.id.date3);
        this.dates[3] = (TextView) findViewById(R.id.date4);
        this.dates[4] = (TextView) findViewById(R.id.date5);

        this.scores[0] = (TextView) findViewById(R.id.topscore1);
        this.scores[1] = (TextView) findViewById(R.id.topscore2);
        this.scores[2] = (TextView) findViewById(R.id.topscore3);
        this.scores[3] = (TextView) findViewById(R.id.topscore4);
        this.scores[4] = (TextView) findViewById(R.id.topscore5);

        ScoreDataSource scoreDataSource = new ScoreDataSource(this);
        ArrayList<Score> scoreArrayList = scoreDataSource.getTopFiveScore();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        if(!scoreArrayList.isEmpty()){
            for (int i = 0; i < scoreArrayList.size(); i++) {
                this.dates[i].setText(dateFormat.format(scoreArrayList.get(i).getDate()));
                this.scores[i].setText(scoreArrayList.get(i).getScore() + "");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ScoreActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
