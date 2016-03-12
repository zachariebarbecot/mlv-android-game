package com.mlvandroidgame.utils;

import com.mlvandroidgame.databases.Score;

import java.util.Date;

/**
 * Created by Zacharie BARBECOT.
 */
public class ScoreSingleton {

    private Score score;

    private static ScoreSingleton INSTANCE = new ScoreSingleton();

    private ScoreSingleton() {
        score = new Score(new Date(), 0);
    }

    public static ScoreSingleton getINSTANCE() {
        if(ScoreSingleton.INSTANCE == null) {
            ScoreSingleton.INSTANCE = new ScoreSingleton();
        }
        return ScoreSingleton.INSTANCE;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
