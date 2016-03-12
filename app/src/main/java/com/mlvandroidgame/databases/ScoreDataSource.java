package com.mlvandroidgame.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zacharie BARBECOT.
 */
public class ScoreDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public ScoreDataSource(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addScore(Score score) {
        database = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_SCORE, score.getScore());

            long insert = database.insert(DBHelper.TABLE_SCORE, null, values);

            return insert;
        } finally {
            database.close();
        }
    }



    @SuppressWarnings("SimpleDateFormat")
    public ArrayList<Score> getScore() {
        ArrayList<Score> shares = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_SCORE;

            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    try {
                        long id = Long.
                                parseLong(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID)));

                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
                        Date date = null;
                        date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                        int score = Integer
                                .parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCORE)));

                        Score share = new Score(id, date, score);
                        shares.add(share);
                        cursor.moveToNext();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            cursor.close();
        } finally {
            database.close();
        }
        return shares;
    }

    @SuppressWarnings("SimpleDateFormat")
    public ArrayList<Score> getTopFiveScore() {
        ArrayList<Score> shares = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_SCORE + " ORDER BY "
                    + DBHelper.TABLE_SCORE + "." + DBHelper.COLUMN_SCORE + " DESC LIMIT 5";

            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    try {
                        long id = Long.
                                parseLong(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID)));

                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                        int score = Integer
                                .parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCORE)));

                        Score share = new Score(id, date, score);
                        shares.add(share);
                        cursor.moveToNext();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            cursor.close();
        } finally {
            database.close();
        }
        return shares;
    }

    public void delete() {
        database = dbHelper.getReadableDatabase();

        try {
            database.delete(DBHelper.TABLE_SCORE, null, null);
        } finally {
            database.close();
        }
    }
}
