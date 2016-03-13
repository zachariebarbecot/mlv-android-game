package com.mlvandroidgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.mlvandroidgame.game.GameView;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    private GameView gameView;
    private Timer timer;
    private int second;
    private Handler handler;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        this.second = 3000;
        this.handler = new Handler();

        gameView = new GameView(this);
        setContentView(gameView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }


    public void startTimer() {
        this.timer = new Timer();
        initializeTimerTask();
        this.timer.schedule(timerTask, 1000, second);
    }

    public void initializeTimerTask() {
        this.timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (gameView.getBall().isMoving()) {
                            gameView.createCube();
                        }
                    }
                });
            }
        };

    }
}
