package com.mlvandroidgame.databases;

import java.util.Date;

/**
 * Created by Zacharie BARBECOT.
 */
public class Score {

    private long id;
    private Date date;
    private int score;


    public long getId() {
        return id;
    }

    public Score(long id, Date date, int score) {
        this.id = id;
        this.date = date;
        this.score = score;
    }

    public Score(Date date, int score) {
        this.date = date;
        this.score = score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}