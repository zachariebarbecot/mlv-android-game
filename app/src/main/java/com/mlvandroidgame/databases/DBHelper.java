package com.mlvandroidgame.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zacharie BARBECOT.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MLVAndroidGame.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SCORE = "score";
    public static final String TABLE_ADMIN = "admin";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMEI = "imei";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SCORE = "score";

    public static final String CREATE_TABLE_ADMIN_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_ADMIN + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IMEI + " TEXT)";
    public static final String CREATE_TABLE_SCORE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DATE + " DATETIME default current_timestamp,"
            + COLUMN_SCORE + " INTEGER)";

    public static final String DROP_TABLE_ADMIN_SQL = "DROP TABLE IF EXISTS " + TABLE_ADMIN;
    public static final String DROP_TABLE_SCORE_SQL = "DROP TABLE IF EXISTS " + TABLE_SCORE;


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADMIN_SQL);
        db.execSQL(CREATE_TABLE_SCORE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ADMIN_SQL);
        db.execSQL(DROP_TABLE_SCORE_SQL);
        onCreate(db);
    }
}
