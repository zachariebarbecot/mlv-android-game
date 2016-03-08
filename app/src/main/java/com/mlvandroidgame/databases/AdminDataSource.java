package com.mlvandroidgame.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Zacharie BARBECOT.
 */
public class AdminDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public AdminDataSource(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addAdmin(Admin admin) {
        database = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_IMEI, admin.getImei());

            long insert = database.insert(DBHelper.TABLE_ADMIN, null, values);

            return insert;
        } finally {
            database.close();
        }
    }

    public ArrayList<Admin> getAdminByImei(String adminImei) {
        ArrayList<Admin> admins = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_ADMIN + " WHERE " + DBHelper.TABLE_ADMIN
                    + "." + DBHelper.COLUMN_IMEI + " = " + adminImei;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    long id = Long.
                            parseLong(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID)));

                    String imei = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMEI));

                    Admin admin = new Admin(id, imei);
                    admins.add(admin);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } finally {
            database.close();
        }
        return admins;
    }

    public void delete() {
        database = dbHelper.getReadableDatabase();

        try {
            database.delete(DBHelper.TABLE_ADMIN, null, null);
        }finally {
            database.close();
        }
    }
}
