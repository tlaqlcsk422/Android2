package com.example.androidproject2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static java.sql.Types.NULL;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteDBTest";

    public DBHelper(@Nullable Context context) {
        super(context, Schedule.DB_NAME, null, Schedule.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,getClass().getName() + ".onCreate() ");
        db.execSQL(Schedule.Schadules.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Schedule.Schadules.DELETE_TABLE);
        onCreate(db);
    }

    public void insertSchadule(int year, int month, int day, String title,
                               int shour, int smin, int ehour, int emin,
                               String place, String memo){
        try{
            String sql = String.format(
                    "INSERT INTO %s(%d %d, %d, %s, %d, %d, %d %d %s %s) VALUES(NULL, '%d','%d','%d','%s','%d','%d','%d','%d','%s','%s')",
                    Schedule.Schadules.TABLE_NAME,
                    Schedule.Schadules.KEY_YEAR,
                    Schedule.Schadules.KEY_MONTH,
                    Schedule.Schadules.KEY_DAY,
                    Schedule.Schadules.KEY_TITLE,
                    Schedule.Schadules.KEY_SHOUR,
                    Schedule.Schadules.KEY_SMIN,
                    Schedule.Schadules.KEY_EHOUR,
                    Schedule.Schadules.KEY_EMIN,
                    Schedule.Schadules.KEY_PLACE,
                    Schedule.Schadules.KEY_MEMO,
                    year, month, day, title, shour, smin, ehour, emin, place, memo);

            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in inserting recodes");
        }
    }

    public void updateSchadule(int year, int month, int day, String title,
                               int shour, int smin, int ehour, int emin,
                               String place, String memo){
        try {
            String sql = String.format(
                    "UPDATE %s SET %s = '%s', %d = '%d', %d = '%d', %d = '%d', %d = '%d', %s = '%s', %s ='%s' WHERE %d = '%d' AND %d = '%d' AND %d = '%d'",
                    Schedule.Schadules.TABLE_NAME,
                    Schedule.Schadules.KEY_TITLE, title,
                    Schedule.Schadules.KEY_SHOUR, shour,
                    Schedule.Schadules.KEY_SMIN, smin,
                    Schedule.Schadules.KEY_EHOUR, ehour,
                    Schedule.Schadules.KEY_EMIN, emin,
                    Schedule.Schadules.KEY_PLACE, place,
                    Schedule.Schadules.KEY_MEMO, memo,
                    Schedule.Schadules.KEY_YEAR, year,
                    Schedule.Schadules.KEY_MONTH, month,
                    Schedule.Schadules.KEY_DAY, day);
            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in updaing recodes");
        }
    }

    public void deleteSchadule(int year, int month, int day){
        try{
            String sql = String.format(
                    "DELETE FROM %s WHERE %s = %s AND %s = %s AND %s = %s",
                    Schedule.Schadules.TABLE_NAME,
                    Schedule.Schadules.KEY_YEAR, year,
                    Schedule.Schadules.KEY_MONTH, month,
                    Schedule.Schadules.KEY_DAY, day);
        }catch (SQLException e){
            Log.e(TAG, "Error in deleting recodes");
        }
    }

    public Cursor getAllSchadules(){
        String sql = "Select * From " + Schedule.Schadules.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }
/*
    public String isSchadule(int year, int month, int day){
            String sql = String.format(
                    "(SELECT EXISTS(SELECT * FROM %s WHERE %d = %d AND % = %d AND %d = %d) AS SUCCESS",
                    Schedule.Schadules.TABLE_NAME,
                    Schedule.Schadules.KEY_YEAR, year,
                    Schedule.Schadules.KEY_MONTH, month,
                    Schedule.Schadules.KEY_DAY, day
            );
        return getReadableDatabase().rawQuery(sql, null);
    }

 */

}

